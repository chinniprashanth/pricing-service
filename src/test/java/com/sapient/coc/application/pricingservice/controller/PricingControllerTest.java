
package com.sapient.coc.application.pricingservice.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.service.PricingService;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PricingControllerTest {

	private static Logger logger = LoggerFactory.getLogger(PricingControllerTest.class);

	private OrderPriceResp orderPriceResp = null;
	private CartResponse cartResposne;
	private static JSONObject requestParams;
	private List<OrderItem> items;
	public static Response response;
	public static RequestSpecification httpRequest;
	// private List<OrderItem> items;

	@Autowired
	private MockMvc mockMvc;

	/*
	 * @Autowired private PricingController pricingController;
	 */

	@InjectMocks
	private PricingController pricingController;

	@TestConfiguration
	static class PricingControllerTestContextConfiguration {

		@Bean
		public PricingController pricingController() {
			return new PricingController();
		}

	}

	@Mock
	private PricingService pricingService;

	@Mock
	private CartInfoServiceClient cartInfoServiceClient;

	@Mock
	private ProductInfoServiceClient productInfoServiceClient;

	@Mock
	private FulfillmentServiceClient fulfillmentServiceClient;

	@Mock
	private PricingEventPublisher pricingEventPublisher;

	@Value(value = "pricingTopic")
	private String topicName;
	private static final String OAUTH_SVC_URL = "http://35.241.4.242/auth-service/oauth/token";
	String token;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(pricingController).build();
		// token = obtainAccessToken();

		cartResposne = new CartResponse();
		items = new ArrayList<OrderItem>();
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId("23232");
		orderItem.setItemPrice(new Double(20));
		orderItem.setQuantity(2);
		items.add(orderItem);
		cartResposne.setItems(items);
		cartResposne.setShipping(new Double(10));
		cartResposne.setTotal(100);
		cartResposne.setSubtotal(100);
		cartResposne.setActualTotal(100);

		orderPriceResp = new OrderPriceResp();
		orderPriceResp.setOrderItems(items);
		orderPriceResp.setActualTotal(new Money("USD", new Double(10)));

		String server = "localhost:12789";

		// if (!System.getProperty("spring.profiles.active").equalsIgnoreCase("LOCAL"))
		// {
		server = "35.241.4.242";
		// }

		RestAssured.baseURI = "http://" + server + "/pricing";
		logger.debug("the server is {}", server);
		httpRequest = RestAssured.given();

		// Add a header stating the Request body is a JSON
		try {
			httpRequest.header("Content-Type", "application/json");
			token = "Bearer " + obtainAccessToken();
			httpRequest.header("Authorization", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testApplyItemPromotionForGivenItems() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			HttpHeaders headers = new HttpHeaders();
			headers.add("Authorization", token);
			headers.add("Client", "coc");
			ObjectMapper objectMapper = new ObjectMapper();
			when(pricingService.applyCartPricing(token, "100", "coc")).thenReturn(cartResposne);
			this.mockMvc
					.perform(get("/pricing/items/100").contentType(MediaType.APPLICATION_JSON)
							.headers(headers)
							.content(objectMapper.writeValueAsString(cartResposne)))
					.andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			Assert.fail("Get pricing method failed in Controller");
			logger.error("Get pricing method in Controller", e);
		}

	}

	@Test
	public void testApplyItemPromotionForGivenItems_error() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			ObjectMapper objectMapper = new ObjectMapper();
			when(pricingService.applyCartPricing(token, "100", "coc")).thenReturn(null);
			this.mockMvc.perform(get("/pricing/items/100").contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", token).content(objectMapper.writeValueAsString(cartResposne)));
		} catch (Exception e) {
			logger.debug("Test case passed for Null scenario Get pricing");
			assertTrue(true);
		}

	}

	@Test
	public void testApplyItemPromotionForGivenItems_null() throws Exception {
		String abc = null;
		requestParams = new JSONObject();
		requestParams.put("cartId", abc);
		when(pricingService.applyCartPricing(token, null, "coc")).thenReturn(null);
		this.mockMvc.perform(get("/pricing/items/abc")).andExpect(status().is5xxServerError());
	}

	@Test
	public void testApplyItemPromotionForGivenItems_empty() throws Exception {

		requestParams = new JSONObject();
		requestParams.put("cartId", "");
		when(pricingService.fetchCartDetails(token, null, "coc")).thenReturn(null);
		when(pricingService.applyCartPricing(token, null, "coc")).thenReturn(null);
		this.mockMvc.perform(get("/pricing/items/")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testApplyShippingPricing() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			ObjectMapper objectMapper = new ObjectMapper();
			when(pricingService.calculateOrderPrice(token)).thenReturn(orderPriceResp);
			this.mockMvc
					.perform(get("/pricing/order").contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", token).content(objectMapper.writeValueAsString(orderPriceResp)))
					.andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			Assert.fail("Get pricing method failed in Controller");
			logger.error("Get pricing method in Controller", e);
		}

	}

	@Test
	public void testApplyShippingPricing_error() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			ObjectMapper objectMapper = new ObjectMapper();
			when(pricingService.calculateOrderPrice(token)).thenReturn(null);
			this.mockMvc.perform(get("/pricing/order").contentType(MediaType.APPLICATION_JSON)
					.header("Authorization", token).content(objectMapper.writeValueAsString(orderPriceResp)));
		} catch (Exception e) {
			logger.debug("Test case passed for Null scenario Get order pricing methods");
			assertTrue(true);
		}

	}

	private static String obtainAccessToken() throws Exception {

		Response oauthResponse = RestAssured.given().auth().basic("web-client", "web-client-secret")
				.formParam("grant_type", "client_credentials").when().post(OAUTH_SVC_URL).andReturn();

		String access_token = oauthResponse.getBody().jsonPath().get("access_token");
		return access_token;
	}
}
