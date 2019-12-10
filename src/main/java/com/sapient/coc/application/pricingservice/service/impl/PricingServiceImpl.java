package com.sapient.coc.application.pricingservice.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.datastax.driver.core.utils.UUIDs;
import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.entity.CartPrice;
import com.sapient.coc.application.pricingservice.bo.entity.ItemPrice;
import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Data;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.FulfillmentItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItemPrice;
import com.sapient.coc.application.pricingservice.bo.vo.OrderKafkaResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.ShippingResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.repository.PriceRepository;
import com.sapient.coc.application.pricingservice.service.PricingService;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = false)
public class PricingServiceImpl implements PricingService {
	

	public static final Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);
	
	private static final String ERROR_PRODUCT_DETAIL_MISSING = "Product detail is missing for the skuId";
	private static final String ERROR_GETTING_CART = "Can't Get current cart from cart service";
	private static final String ERROR_FULFILLMENT_MISSING = "Can't get fulfillment details for the token";
	private static final String ERROR_NO_RESPONSE_FROM_PRICING = "No Resposne for the cart id from pricing repository";
	private static final String NO_SKU = "No sku id present in cart";
	private static final String CART_ID_REQUIRED = "Cart id can not be null";
	private static final String CURRENCY = "USD";


	@Autowired
	ProductInfoServiceClient productInfoServiceClient;

	@Autowired
	CartInfoServiceClient cartInfoServiceClient;

	@Autowired
	FulfillmentServiceClient fulfillmentServiceClient;

	@Autowired
	PricingEventPublisher pricingEventPublisher;
	
	@Autowired
	private PriceRepository priceRepository;

	@Value(value = "${spring.kafka.message.topic.name}")
	private String topicName;

	/**
	 * Fetches the price details of a cart and does re-pricing as well
	 *
	 * @param token, cartId
	 * @return CartResponse Object
	 */
	@Override
	public CartResponse fetchCartDetails(String token, String cartId) throws CoCBusinessException, CoCSystemException {
		logger.debug("Enter fetchCartDetails method");
		CartResp cartResp = null;
		CartResponse cartResponse = new CartResponse();
		Map<String, ItemPrice> cartRepoMap = getCartRepo(cartId);
		if (null == cartId) {
			logger.debug("CartId cannot be null");
			throw new CoCBusinessException(CART_ID_REQUIRED);
		} else {
		try {
			cartResp = cartInfoServiceClient.getOrderDetails(token, cartId);
		} catch (Exception exc) {
			logger.error(ERROR_GETTING_CART);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}
		if (null != cartResp && null != cartResp.getData()) {
			List<CartItem> cartItems = Arrays.asList(cartResp.getData()); //
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<String> skuIds = new ArrayList<String>();
		Map<String, Integer> qtySkuId = new HashMap<String, Integer>();
			Map<String, CartItem> cartMap = new HashMap<String, CartItem>();

		cartItems.forEach(cartItem -> {
			skuIds.add(cartItem.getSkuId());
			qtySkuId.put(cartItem.getSkuId(), cartItem.getQuantity());
				cartMap.put(cartItem.getSkuId(), cartItem);
		});
			Map<String, Double> priceCompare = new HashMap<String, Double>();
			if (null != cartRepoMap) {
				for (Map.Entry<String, ItemPrice> priceMap : cartRepoMap.entrySet()) {
		  String key = priceMap.getKey();
		  CartItem cartItem = cartMap.get(key); 
					if (null != cartItem) {
						priceCompare.put(key, priceMap.getValue().getItemPrice().getAmount());
					}
		}
			}
		String ids = skuIds.stream().collect(Collectors.joining(","));
			if (!ids.isEmpty()) {
			List<Sku> itemDetails = new ArrayList<Sku>();
			List<ItemPrice> listPrice = new ArrayList<ItemPrice>();
		try {
			itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
					itemDetails.forEach(itemDetail -> {
						boolean priceMsg = false;
						//OrderItem orderItem = new OrderItem();
							OrderItem orderItem = new OrderItem(itemDetail.getId(),
								itemDetail.getId(), itemDetail.getParentproductid(), 
								qtySkuId.get(itemDetail.getId()), 
								new Double(itemDetail.getListprice()),
								new Double(itemDetail.getSaleprice()),
								(itemDetail.getListprice() * qtySkuId.get(itemDetail.getId())),
								new Double(itemDetail.getListprice()),
								new Double((itemDetail.getSaleprice() * qtySkuId.get(itemDetail.getId()))), priceMsg);
						if (null != cartMap.get(itemDetail.getId()).getWasPrice().getValue()) {
							orderItem.setWasPrice(
									Double.parseDouble(cartMap.get(itemDetail.getId()).getWasPrice().getValue()));
						} else {
							orderItem.setWasPrice(itemDetail.getListprice());
						}
							if (null != priceCompare.get(orderItem.getSkuId())) {

								if ((priceCompare.get(orderItem.getSkuId())) > itemDetail.getListprice() - 10
										|| priceCompare.get(orderItem.getSkuId()) < itemDetail.getListprice() - 10) {
									orderItem.setPriceChanged(true);
								} else {
									orderItem.setPriceChanged(false);
								}
							} else {
								if (orderItem.getWasPrice() > itemDetail.getListprice() - 10
										|| orderItem.getWasPrice() < itemDetail.getListprice() - 10) {
									orderItem.setPriceChanged(true);
								} else {
									orderItem.setPriceChanged(false);
								}
						}
						orderItems.add(orderItem);
						ItemPrice itemPrice = new ItemPrice(orderItem.getName(), orderItem.getProductId(), 
								orderItem.getSkuId(), orderItem.getQuantity(), 
								new Money(CURRENCY, orderItem.getListPrice()), new Double(0.0));
						listPrice.add(itemPrice);
					});

					if (cartResponse.getSubtotal() == 0) {
						orderItems.forEach(orderItem -> {
							cartResponse.setActualTotal(orderItem.getItemsTotalPrice() + cartResponse.getActualTotal());
							cartResponse.setSubtotal(orderItem.getItemDiscountedPrice() + cartResponse.getSubtotal());
						});
						cartResponse.setItems(orderItems);

						CartPrice cart = new CartPrice(cartId, listPrice, cartResponse.getSubtotal(),
								cartResponse.getTotal(), cartResponse.getActualTotal(), cartResponse.getTotalDiscount(),
								cartResponse.getTax(), 0);
						cart.setId(UUIDs.timeBased().toString());
						if (priceRepository.findByCartId(cartId).isPresent()) {
							Optional<CartPrice> cartPri = priceRepository.findByCartId(cartId);
							priceRepository.deleteCart(cartPri.get().getId());
						}
						cart = priceRepository.save(cart);
						
					}
		} catch (Exception e) {
				logger.error(ERROR_PRODUCT_DETAIL_MISSING);
				throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
		}

			} else {
				logger.error(NO_SKU);
				throw new CoCSystemException(NO_SKU);
			}

		} else {
			logger.error(ERROR_GETTING_CART);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}

		logger.debug("Returning cart price details");
		return cartResponse;
		}
	}

	/**
	 * Fetches the price details of a order item
	 *
	 * @param String token
	 * @return OrderPriceResp Object
	 */
	@Override
	public OrderPriceResp calculateShipping(String token) throws CoCBusinessException, CoCSystemException {
		OrderPriceResp orderResp = new OrderPriceResp();
		OrderKafkaResponse orderKafkaResp = null;
		ResponseEntity<Fulfillment> fulfillmentResp;
		try {
			fulfillmentResp = fulfillmentServiceClient.getOrderFulFillmentDeatils(token);
		} catch (Exception exc) {
			logger.error(ERROR_FULFILLMENT_MISSING);
			throw new CoCSystemException(ERROR_FULFILLMENT_MISSING);
		}
		if (null != fulfillmentResp.getBody() && null != fulfillmentResp.getBody().getData()) {
		Data fulfillmentData = fulfillmentResp.getBody().getData();
		List<FulfillmentItem> itemList = fulfillmentData.getItems();
		Map<String, ShippingResponse> shippingDetailsMap = new HashMap<String, ShippingResponse>();
		Map<String, Double> priceMap = new HashMap<String, Double>();
		Map<String, Integer> skuQuantityMap = new HashMap<String, Integer>();
		List<String> skuIds = new ArrayList<String>();
		itemList.forEach(fulfillmentItem -> {
			ShippingResponse shipResponse = new ShippingResponse();
			shipResponse.setShippingCost(fulfillmentItem.getPrice());
			shipResponse.setShippingMethod(fulfillmentItem.getFulfillmentMethod());
			shippingDetailsMap.put(fulfillmentItem.getSkuId(), shipResponse);
			skuIds.add(fulfillmentItem.getSkuId());
				priceMap.put(fulfillmentItem.getSkuId(), fulfillmentItem.getPrice());
			skuQuantityMap.put(fulfillmentItem.getSkuId(), fulfillmentItem.getQuantity());

		});
		String ids = skuIds.stream().collect(Collectors.joining(","));
		List<OrderItem> orderItems = fetchProductDetails(ids);
			if (null != orderItems && orderItems.size() >= 1) {
		List<OrderItemPrice> orderItemPrice = new ArrayList<OrderItemPrice>();

		double total = 0;
				for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
					total = entry.getValue() + total;
		}
		orderResp.setShipping(new Money("USD", total));
		orderResp.setSubmittedTime(fulfillmentData.getCreatedAt());
		orderResp.setId(fulfillmentData.getId());
		orderItems.forEach(orderItem -> {
			OrderItemPrice itemPrice = null;
			orderItem.setShippingMethod(shippingDetailsMap.get(orderItem.getSkuId()).getShippingMethod());
			orderItem.setShippingPrice(shippingDetailsMap.get(orderItem.getSkuId()).getShippingCost());
			orderItem.setQuantity(skuQuantityMap.get(orderItem.getSkuId()));
			orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQuantity());
			orderItem.setItemDiscountedPrice(orderItem.getSalePrice() * orderItem.getQuantity());
			if (null != orderResp.getActualTotal()) {
				orderResp.setActualTotal(
								new Money(CURRENCY,
										(orderItem.getItemsTotalPrice() + orderResp.getActualTotal().getAmount())));
			} else {
						orderResp.setActualTotal(new Money(CURRENCY, (orderItem.getItemsTotalPrice())));
			}
			if (null != orderResp.getSubtotal()) {
				orderResp.setSubtotal(
								new Money(CURRENCY,
										orderItem.getItemDiscountedPrice() + orderResp.getSubtotal().getAmount()));
			} else {
						orderResp.setSubtotal(new Money(CURRENCY, orderItem.getItemDiscountedPrice()));
			}
			itemPrice = new OrderItemPrice(orderItem.getName(), orderItem.getProductId(), orderItem.getSkuId(),
					orderItem.getShippingMethod(), orderItem.getShippingPrice(), orderItem.getQuantity(),
							new Money(CURRENCY, orderItem.getItemPrice()),
							new Money(CURRENCY, orderItem.getItemsTotalPrice()));
			orderItemPrice.add(itemPrice);

		});
				orderResp.setSubtotal(new Money(CURRENCY, orderResp.getSubtotal().getAmount() + total));
		orderKafkaResp = new OrderKafkaResponse("P", token, orderItemPrice, fulfillmentData.getCreatedAt(), new Date(),
				orderResp.getSubtotal(), orderResp.getTotal(), orderResp.getActualTotal(), orderResp.getShipping(),
						orderResp.getTotalDiscount(), new Money(CURRENCY, 0.0), orderResp.getId());
		orderResp.setOrderItems(orderItems);
		try {
			sendMessage(orderKafkaResp);
		} catch (CoCSystemException e) {
			logger.error("Error publishing Kafka messgae");
			throw new CoCSystemException("Error publishing Kafka messgae");
			}
			} else {
				logger.error(ERROR_PRODUCT_DETAIL_MISSING);
				throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
			}
		} else {
			logger.error(ERROR_FULFILLMENT_MISSING);
			throw new CoCSystemException(ERROR_FULFILLMENT_MISSING);
		}
		return orderResp;
	}

	/**
	 * Fetches the product details of an item
	 *
	 * @param String skuId
	 * @return List<OrderItem>
	 */
	@Override
	public List<OrderItem> fetchProductDetails(String skuId) {
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<Sku> itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(skuId);
		itemDetails.forEach(itemDetail -> {
			OrderItem orderItem = new OrderItem();
			orderItem.setSalePrice(new Double(itemDetail.getSaleprice()));
			orderItem.setListPrice(new Double(itemDetail.getListprice()));
			orderItem.setSkuId(itemDetail.getId());
			orderItem.setProductId(itemDetail.getParentproductid());
			orderItem.setItemDescription(itemDetail.getDescription());
			orderItem.setName(itemDetail.getName());
			orderItem.setImageUrl(itemDetail.getImages().get(0).getUrl());
			orderItem.setItemId(itemDetail.getId());
			orderItems.add(orderItem);
		});
		return orderItems;
	}
	
	/**
	 * Sends Kafka message to order service to update the price
	 *
	 * @param OrderKafkaResponse cart
	 * 
	 */

	public void sendMessage(OrderKafkaResponse cart) throws CoCSystemException {

		logger.debug("Sending order pricing message= {}", cart.getActualTotal());
		pricingEventPublisher.sendMessage(topicName, cart);
	}

	/**
	 * Checks for price change in cart items
	 *
	 * @param String cartId
	 * 
	 */
	public boolean priceChange(String cartId) {
		Optional<CartPrice> cart = priceRepository.findByCartId(cartId);
		List<Sku> itemDetails = null;
		CartPrice cartPrice = cart.get();
		List<ItemPrice> itemPrice = cartPrice.getItemPrice();
		Map<String, Double> cartPriceMap = new HashMap<String, Double>();
		Map<String, Double> productPriceMap = new HashMap<String, Double>();
		itemPrice.stream().forEach(itemPriceObj -> {
			cartPriceMap.put(itemPriceObj.getSkuId(), itemPriceObj.getDiscountedprice());
		});
		String ids = "";
		for (Map.Entry<String, Double> entry : cartPriceMap.entrySet()) {
			ids = ids + entry.getKey() + ",";
		}
		itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
		itemDetails.stream().forEach(itemDetailObj -> {
			productPriceMap.put(itemDetailObj.getId(), new Double(itemDetailObj.getListprice() - 10));
		});
		cartPriceMap.entrySet().stream()
        .filter(x -> {
        	for(Map.Entry entry:productPriceMap.entrySet()){
        	    if (x.getValue().equals(entry.getKey()) && !x.getValue().equals(entry.getValue())) {
                    return true;
                }
        	}
           
            return false;
        });
		return false;
	}

	/**
	 * Method to fetch cart details from price repository
	 *
	 * @param String cartId
	 * 
	 */
	public Map<String, ItemPrice> getCartRepo(String cartId) {
		Map<String, ItemPrice> cartRepoMap = new HashMap<String, ItemPrice>();
		CartPrice cartObj = new CartPrice();
		Optional<CartPrice> cartPrice = null;
		cartPrice = priceRepository.findByCartId(cartId);
		if (cartPrice.isPresent()) {
			cartObj = cartPrice.get();
			List<ItemPrice> itemPrice = cartObj.getItemPrice();
			itemPrice.forEach(itemDetail -> {
				cartRepoMap.put(itemDetail.getSkuId(), itemDetail);
			});
		} else {
			logger.info(ERROR_PRODUCT_DETAIL_MISSING);
			return null;
		}
		return cartRepoMap;

	}
}
