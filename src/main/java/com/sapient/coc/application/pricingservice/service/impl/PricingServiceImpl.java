package com.sapient.coc.application.pricingservice.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.BillingAdd;
import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Data;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.FulfillmentItem;
import com.sapient.coc.application.pricingservice.bo.vo.Order;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItemPrice;
import com.sapient.coc.application.pricingservice.bo.vo.OrderKafkaResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.ShippingResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.bo.vo.Tax;
import com.sapient.coc.application.pricingservice.feign.client.AddressServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.OrderServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.TaxServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.service.PricingService;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project. Handles price calculation for cart and order items
 * 
 * @author pooyadav
 */

@Service
public class PricingServiceImpl implements PricingService {

	public static final Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);

	private static final String ERROR_PRODUCT_DETAIL_MISSING = "Product detail is missing for the skuId";
	private static final String ERROR_GETTING_CART = "Can't Get current cart from cart service";
	private static final String ERROR_FULFILLMENT_MISSING = "Can't get fulfillment details for the token";
	private static final String NO_SKU = "No sku id present in cart";
	private static final String CART_ID_REQUIRED = "Cart id can not be null";
	private static final String ORDER_ID_REQUIRED = "Order id can not be null";
	private static final String ERROR_GETTING_ADDRESS = "Error getting address";
	private static final String ERROR_GETTING_ORDER = "Error while fetching order details";
	private static final String ERROR_GETTING_TAX = "Error getting tax";
	private static final String CURRENCY = "USD";
	private static final String ORDER_ID_NOT_AVAILABLE = "Order Id is not available";

	@Autowired
	ProductInfoServiceClient productInfoServiceClient;

	@Autowired
	CartInfoServiceClient cartInfoServiceClient;

	@Autowired
	FulfillmentServiceClient fulfillmentServiceClient;

	@Autowired
	PricingEventPublisher pricingEventPublisher;

	@Autowired
	AddressServiceClient addressServiceClient;

	@Autowired
	OrderServiceClient orderServiceClient;

	@Autowired
	TaxServiceClient taxServiceClient;

	@Value(value = "${spring.kafka.message.topic.name}")
	private String topicName;

	@Value(value = "${application.taxApply.allowed}")
	private boolean taxEnabled;

	private boolean productDetailNotAvailable = false;

	/**
	 * Fetches the price details of a cart and does re-pricing as well
	 *
	 * @param token, cartId
	 * @return CartResponse Object
	 */
	@Override
	public CartResponse fetchCartDetails(String token, String cartId) throws CoCBusinessException, CoCSystemException {
		logger.debug("Enter fetchCartDetails method in PricingServiceImpl");
		CartResp cartResp = null;
		try {
			cartResp = getCartdetails(cartId, token);
		} catch (Exception exec) {
			logger.error(ERROR_GETTING_CART, exec);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}
		CartResponse cartResponse = new CartResponse();
		if (null != cartResp && null != cartResp.getData()) {
			List<CartItem> cartItems = Arrays.asList(cartResp.getData());
			List<OrderItem> orderItems = new ArrayList<OrderItem>();
			List<String> skuIds = new ArrayList<String>();
			Map<String, Integer> qtySkuId = new HashMap<String, Integer>();
			Map<String, CartItem> cartMap = new HashMap<String, CartItem>();

			cartItems.forEach(cartItem -> {
				skuIds.add(cartItem.getSkuId());
				qtySkuId.put(cartItem.getSkuId(), cartItem.getQuantity());
				cartMap.put(cartItem.getSkuId(), cartItem);
			});

			String ids = skuIds.stream().collect(Collectors.joining(","));
			if (!ids.isEmpty()) {
				List<Sku> itemDetails = new ArrayList<Sku>();
				try {
					itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(ids);
					itemDetails.forEach(itemDetail -> {
						boolean priceMsg = false;
						if (null != itemDetail.getId()) {
						OrderItem orderItem = new OrderItem(itemDetail.getId(), itemDetail.getId(),
								itemDetail.getParentproductid(), qtySkuId.get(itemDetail.getId()),
								new Double(itemDetail.getListprice()), new Double(itemDetail.getSaleprice()),
								(itemDetail.getListprice() * qtySkuId.get(itemDetail.getId())),
								new Double(itemDetail.getListprice()),
								new Double((itemDetail.getSaleprice() * qtySkuId.get(itemDetail.getId()))), priceMsg);
						if (null != cartMap.get(itemDetail.getId()).getWasPrice()
								&& null != cartMap.get(itemDetail.getId()).getWasPrice().getValue()) {
							orderItem.setWasPrice(
										Double.parseDouble(cartMap.get(itemDetail.getId()).getWasPrice().getValue()));
						} else {
								orderItem.setWasPrice(itemDetail.getListprice());
						}

						if (orderItem.getWasPrice() > itemDetail.getSaleprice()
								|| orderItem.getWasPrice() < itemDetail.getSaleprice()) {
							orderItem.setPriceChanged(true);
						} else {
							orderItem.setPriceChanged(false);
						}
						orderItems.add(orderItem);
						} else {
							productDetailNotAvailable = true;

						}
					});
					if (productDetailNotAvailable) {
						logger.error(ERROR_PRODUCT_DETAIL_MISSING);
						throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
					}
					if (cartResponse.getSubtotal() == 0) {
						orderItems.forEach(orderItem -> {
							cartResponse.setActualTotal(orderItem.getItemsTotalPrice() + cartResponse.getActualTotal());
							cartResponse.setSubtotal(orderItem.getItemDiscountedPrice() + cartResponse.getSubtotal());
						});
						cartResponse.setItems(orderItems);
					}
				} catch (Exception exec) {
					logger.error(ERROR_PRODUCT_DETAIL_MISSING, exec);
					throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
				}

			} else {
				logger.error(NO_SKU);
				throw new CoCBusinessException(NO_SKU);
			}
		} else {
			logger.error(ERROR_GETTING_CART);
			throw new CoCSystemException(ERROR_GETTING_CART);
		}
		logger.debug("Returning cart price details");
		return cartResponse;
	}

	/**
	 * Fetches the price details of a order item
	 *
	 * @param String token
	 * @return OrderPriceResp Object
	 */
	@Override
	public OrderPriceResp calculateOrderPrice(String token) throws CoCBusinessException, CoCSystemException {
		logger.debug("Entering calculateOrderPrice method in PricingserviceImpl");

		

		OrderPriceResp orderResp = new OrderPriceResp();
		OrderKafkaResponse orderKafkaResp = null;
		ResponseEntity<Fulfillment> fulfillmentResp;
		productDetailNotAvailable = false;
		try {
			fulfillmentResp = fulfillmentServiceClient.getOrderFulFillmentDeatils(token);
		} catch (Exception exc) {
			logger.error(ERROR_FULFILLMENT_MISSING, exc);
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
			if (productDetailNotAvailable) {
				logger.error(ERROR_PRODUCT_DETAIL_MISSING);
				throw new CoCBusinessException(ERROR_PRODUCT_DETAIL_MISSING);
			}
			if (null != orderItems && orderItems.size() >= 1) {
				List<OrderItemPrice> orderItemPrice = new ArrayList<OrderItemPrice>();

				double total = 0;
				for (Map.Entry<String, Double> entry : priceMap.entrySet()) {
					total = entry.getValue() + total;
				}
				orderResp.setShipping(new Money(CURRENCY, total));
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
						orderResp.setActualTotal(new Money(CURRENCY,
								(orderItem.getItemsTotalPrice() + orderResp.getActualTotal().getAmount())));
					} else {
						orderResp.setActualTotal(new Money(CURRENCY, (orderItem.getItemsTotalPrice())));
					}
					if (null != orderResp.getSubtotal()) {
						orderResp.setSubtotal(new Money(CURRENCY,
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
				orderKafkaResp = new OrderKafkaResponse("Created", token, orderItemPrice,
						fulfillmentData.getCreatedAt(),
						new Date(), orderResp.getSubtotal(), orderResp.getTotal(), orderResp.getActualTotal(),
						orderResp.getShipping(), orderResp.getTotalDiscount(), new Money(CURRENCY, 0.0),
						orderResp.getId());
				orderResp.setOrderItems(orderItems);
				
				try {
					sendMessage(orderKafkaResp);

				} catch (CoCSystemException exc) {
					logger.error("Error publishing Kafka messgae", exc);
					throw new CoCSystemException("Error publishing Kafka messgae");
				}
			} else {
				logger.error(ERROR_PRODUCT_DETAIL_MISSING);
				throw new CoCBusinessException(ERROR_PRODUCT_DETAIL_MISSING);
			}
		} else {
			logger.error(ERROR_FULFILLMENT_MISSING);
			throw new CoCBusinessException(ERROR_FULFILLMENT_MISSING);
		}
		logger.debug("Exit calculateOrderPrice method in PricingserviceImpl");
		return orderResp;
	}

	/**
	 * Fetches the product details of an item
	 *
	 * @param String skuId
	 * @return List<OrderItem>
	 * @throws CoCSystemException
	 */
	@Override
	public List<OrderItem> fetchProductDetails(String skuId) throws CoCSystemException {
		logger.debug("Entering fetchProductDetails method in PricingServiceImpl");
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		List<Sku> itemDetails;
		try {
			itemDetails = productInfoServiceClient.getProductDetailsForSapecificItems(skuId);

			itemDetails.forEach(itemDetail -> {
				if (null != itemDetail.getId()) {
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
				} else {
					productDetailNotAvailable = true;
				}
			});
		} catch (Exception exc) {
			logger.error(ERROR_PRODUCT_DETAIL_MISSING, exc);
			throw new CoCSystemException(ERROR_PRODUCT_DETAIL_MISSING);
		}
		logger.debug("Exit fetchProductDetails method in PricingServiceImpl");
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
	 * This method calls cart service feign client
	 * 
	 * @param cartId
	 * @param token
	 * @return CartResp
	 * @throws CoCSystemException
	 * @throws CoCBusinessException
	 */
	public CartResp getCartdetails(String cartId, String token) throws CoCSystemException, CoCBusinessException {
		logger.debug("Entering getCartdetails method in PricingServiceImpl");
		CartResp cartResp = null;
		if (null == cartId) {
			logger.debug("CartId cannot be null");
			throw new CoCBusinessException(CART_ID_REQUIRED);
		} else {
			try {
				cartResp = cartInfoServiceClient.getOrderDetails(token, cartId);
			} catch (Exception exc) {
				logger.error(ERROR_GETTING_CART, exc);
				throw new CoCSystemException(ERROR_GETTING_CART);
			}
			logger.debug("Entering getCartdetails method in PricingServiceImpl");
			return cartResp;
		}
	}

	/**
	 * This method calls tax service feign client
	 * 
	 * @param cartId
	 * @param token
	 * @return CartResp
	 * @throws CoCSystemException
	 * @throws CoCBusinessException
	 */
	public BigDecimal getTaxdetails(String orderId, String token, Double totalAmount)
			throws CoCSystemException, CoCBusinessException {
		logger.debug("Entering getTaxdetails method in PricingServiceImpl");
		ResponseEntity<BillingAdd> addressResp = null;
		Tax tax = null;
		if (null == orderId) {
			logger.debug(ORDER_ID_REQUIRED);
			throw new CoCBusinessException(ORDER_ID_REQUIRED);
		} else {
			try {
				addressResp = addressServiceClient.getShippingAddress(orderId, token);
				String zipcode = addressResp.getBody().getAddressVO().getZipcode();
				if (null != zipcode) {
					try {
						tax = taxServiceClient.getTax(zipcode, totalAmount.toString()).getBody();
						if (null != tax && null != tax.getTaxDetails() && null != tax.getTaxDetails().getTaxAmount()) {
							logger.debug("Exit getTaxdetails method in PricingServiceImpl");
							return tax.getTaxDetails().getTaxAmount();
						} else {
							logger.error("tax not available");
							return new BigDecimal(0);
						}
					} catch (Exception exce) {
						logger.error(ERROR_GETTING_TAX, exce);
						throw new CoCSystemException(ERROR_GETTING_TAX);
					}
				} else {
					logger.error("zip code not available");
					throw new CoCSystemException("zip code not available");
				}
			} catch (Exception exc) {
				logger.error(ERROR_GETTING_ADDRESS, exc);
				throw new CoCSystemException(ERROR_GETTING_ADDRESS);
			}
		}
	}


}
