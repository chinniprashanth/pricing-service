package com.sapient.coc.application.pricingservice.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.AddressVO;
import com.sapient.coc.application.pricingservice.bo.vo.BillingAddress;
import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Data;
import com.sapient.coc.application.pricingservice.bo.vo.Fulfillment;
import com.sapient.coc.application.pricingservice.bo.vo.FulfillmentItem;
import com.sapient.coc.application.pricingservice.bo.vo.Images;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.bo.vo.Tax;
import com.sapient.coc.application.pricingservice.cache.CacheDao;
import com.sapient.coc.application.pricingservice.feign.client.AddressServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.TaxServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.service.PricingService;

import io.restassured.RestAssured;
import io.restassured.response.Response;

class PricingServiceImplTest {

	@InjectMocks
	private PricingService pricingService = new PricingServiceImpl();

	@Mock
	private CartInfoServiceClient cartInfoServiceClients;

	@Mock
	private ProductInfoServiceClient productInfoServiceClients;

	@Mock
	private FulfillmentServiceClient fulfillmentServiceClient;

	@Mock
	private TaxServiceClient taxServiceClient;

	@Mock
	private AddressServiceClient addressServiceClient;

	@Mock
	private PricingEventPublisher pricingEventPublisher;

	@Mock
	private CacheDao redis;

	@Mock
	private BillingAddress addrMock;

	@Mock
	private ResponseEntity<BillingAddress> responseEntityAddressMock;

	@Mock
	private ResponseEntity<Tax> responseEntityTax;

	@Mock
	private Tax taxMock;

	@MockBean
	private CartResponse orderResponse = null;

	@MockBean
	private CartResp cartResp;

	private static CartItem[] data;
	private static CartResponse result;
	private static AddressVO addressVO;
	private List<OrderItem> items;
	private List<Sku> skuList;
	private static String token;
	private List<FulfillmentItem> fulfillmentItems;
	private static Data fulfillmentData;
	private static final String OAUTH_SVC_URL = "http://35.241.4.242/auth-service/oauth/token";
	private static Logger logger = LoggerFactory.getLogger(PricingServiceImpl.class);
	private Fulfillment fulfillmentResp;
	private static String orderId = "22e78960-bdf4-11e9-a34b-6bcec6d1fa67";
	private static final String ADDRESS_KEY = "CoC-Shipping-Addr";

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		addressVO = new AddressVO();
		addressVO.setZipcode("12345");
	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	final void testFetchCartDetails() {
		result = new CartResponse();
		try {
			token = obtainAccessToken();
		} catch (Exception e1) {
			logger.error("Error getting cart", e1);
		}
		cartResp = new CartResp();
		data = new CartItem[2];
		CartItem cartItem = new CartItem();
		cartItem.setSkuId("100");
		cartItem.setQuantity(2);
		data[0] = cartItem;
		data[1] = cartItem;
		cartResp.setData(data);
		skuList = new ArrayList<Sku>();
		Sku sku = new Sku();
		sku.setId("abc");
		sku.setId("100");
		sku.setSaleprice(new Double(10));
		sku.setListprice(new Double(10));
		sku.setId("100");
		sku.setParentproductid("100");
		sku.setDescription("Men's wear");
		sku.setName("prod");
		List<Images> images = new ArrayList<Images>();
		Images image = new Images();
		image.setName("prod");
		image.setUrl("www.google.com");
		images.add(image);
		sku.setImages(images);
		skuList.add(sku);
		items = new ArrayList<OrderItem>();
		boolean priceMsg = true;
		OrderItem item = new OrderItem(sku.getId(), sku.getId(), sku.getParentproductid(), 2,
				new Double(sku.getListprice()), new Double(sku.getSaleprice()), (sku.getListprice() * 2),
				new Double(sku.getListprice()), new Double((sku.getSaleprice() * 2)), priceMsg);

		items.add(item);

		when(cartInfoServiceClients.getOrderDetails(token, "100")).thenReturn(cartResp);
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100,100")).thenReturn(skuList);
		result.setActualTotal(0.0);
		result.setTotal(0.0);
		result.setSubtotal(0.0);

		items.forEach(orderItem -> {
			result.setActualTotal(orderItem.getItemsTotalPrice() + result.getActualTotal());
			result.setSubtotal(orderItem.getItemDiscountedPrice() + result.getSubtotal());
		});
		result.setItems(items);

		try {
			result = pricingService.fetchCartDetails(token, "100", "coc");
			assertNotNull(result);
		} catch (CoCBusinessException | CoCSystemException e) {
			fail("Price not calculated"); // TODO
			e.printStackTrace();
		}

	}

	@Test
	final void testCalculateOrderPrice() {
		try {
			token = obtainAccessToken();
		} catch (Exception e) {
			e.printStackTrace();
		}
		fulfillmentItems = new ArrayList<FulfillmentItem>();
		fulfillmentData = new Data();
		cartResp = new CartResp();
		data = new CartItem[2];
		CartItem cartItem = new CartItem();
		cartItem.setSkuId("100");
		cartItem.setQuantity(2);
		data[0] = cartItem;
		data[1] = cartItem;
		cartResp.setData(data);
		items = new ArrayList<OrderItem>();
		FulfillmentItem fulfillitem = new FulfillmentItem();
		OrderItem item = new OrderItem();
		fulfillitem.setFulfillmentMethod("STANDARD");
		fulfillitem.setPrice(new Double(5));
		fulfillitem.setQuantity(2);
		fulfillitem.setSkuId("100");
		item.setListPrice(20);
		item.setItemDescription("sadad");
		item.setItemsTotalPrice(77);
		item.setQuantity(2);
		item.setSkuId("100");
		items.add(item);
		// result.setItems(items);
		skuList = new ArrayList<Sku>();
		Sku sku = new Sku();
		sku.setId("100");
		sku.setSaleprice(new Double(10));
		sku.setListprice(new Double(10));
		sku.setId("100");
		sku.setParentproductid("100");
		sku.setDescription("Men's wear");
		sku.setName("prod");
		List<Images> images = new ArrayList<Images>();
		Images image = new Images();
		image.setName("prod");
		image.setUrl("www.google.com");
		images.add(image);
		sku.setImages(images);
		skuList.add(sku);
		fulfillmentItems.add(fulfillitem);
		fulfillmentResp = new Fulfillment();
		fulfillmentData.setItems(fulfillmentItems);
		fulfillmentResp.setData(fulfillmentData);
		ResponseEntity<Fulfillment> repEntity = new ResponseEntity<Fulfillment>(fulfillmentResp, HttpStatus.OK);

		when(fulfillmentServiceClient.getOrderFulFillmentDeatils(token)).thenReturn(repEntity);
		when(cartInfoServiceClients.getOrderDetails(token, "100")).thenReturn(cartResp);
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100")).thenReturn(skuList);

		OrderPriceResp result;
		try {
			result = pricingService.calculateOrderPrice(token, null);
			assertNotNull(result);
		} catch (CoCBusinessException e) {
			fail("order details not found");
			e.printStackTrace();
		} catch (CoCSystemException e) {
			fail("product details not found");
			e.printStackTrace();
		}

	}

	@Test
	final void testFetchCartDetails_fail() {
		result = null;
		when(cartInfoServiceClients.getOrderDetails(token, "100")).thenReturn(null);
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100,100")).thenReturn(null);

		try {
			result = pricingService.fetchCartDetails(token, "100", "coc");
			// Assert.assertEquals(0.0, result.getTotal());
		} catch (Exception e) {
			assertTrue(true);
			e.printStackTrace();
		}

	}

	@Test
	final void testCalculateOrderPrice_fail_fulfillment() {
		when(fulfillmentServiceClient.getOrderFulFillmentDeatils(token)).thenReturn(null);
		OrderPriceResp result;
		try {
			result = pricingService.calculateOrderPrice(token, null);
			Assert.assertEquals(null, result);
		} catch (Exception e) {
			assertTrue(true);
			e.printStackTrace();
		}

	}

	@Test
	final void testFetchProductDetails_fail() {
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100")).thenReturn(null);
		List<OrderItem> itemDetails;
		try {
			itemDetails = pricingService.fetchProductDetails("100");
			Assert.assertEquals(null, itemDetails);
		} catch (Exception e) {
			assertTrue(true);
			e.printStackTrace();
		}

	}

	@Test
	final void testGetCartdetails() {
		when(cartInfoServiceClients.getOrderDetails(token, null)).thenReturn(null);
		try {
			CartResponse itemDetails = pricingService.fetchCartDetails(token, null, "coc");
			Assert.assertEquals(null, itemDetails);
		} catch (Exception e) {
			assertTrue(true);
			e.printStackTrace();
		}
	}

	@Test
	final void testGetTaxdetails_if_zipcode_inCache() {
		Map<String, AddressVO> cachedData = new HashMap<String, AddressVO>();
		Tax tax = new Tax();
		tax.setData("45.56");
		cachedData.put(ADDRESS_KEY + orderId, addressVO);
		when(redis.findAddressById(ADDRESS_KEY + orderId)).thenReturn(cachedData);
		when(responseEntityTax.getBody()).thenReturn(taxMock);
		when(taxServiceClient.getTax(addressVO.getZipcode(), "20", token)).thenReturn(responseEntityTax);
	}

	@Test
	final void testGetTaxdetails_if_cache_is_empty() {
		Map<String, AddressVO> cachedData = new HashMap<String, AddressVO>();
		Tax tax = new Tax();
		taxMock.setData("45.56");
		cachedData.put(ADDRESS_KEY + orderId, addressVO);
		addrMock.setAddressVO(addressVO);
		when(responseEntityAddressMock.getBody()).thenReturn(addrMock);
		when(addressServiceClient.getShippingAddress(orderId, token)).thenReturn(responseEntityAddressMock);
		when(responseEntityTax.getBody()).thenReturn(taxMock);
		when(taxServiceClient.getTax(addressVO.getZipcode(), "20", token)).thenReturn(responseEntityTax);
	}

	private static String obtainAccessToken() throws Exception {

		Response oauthResponse = RestAssured.given().auth().basic("web-client", "web-client-secret")
				.formParam("grant_type", "client_credentials").when().post(OAUTH_SVC_URL).andReturn();

		String access_token = oauthResponse.getBody().jsonPath().get("access_token");
		return access_token;
	}
}
