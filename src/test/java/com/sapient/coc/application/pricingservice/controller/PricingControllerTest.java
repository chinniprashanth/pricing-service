
package com.sapient.coc.application.pricingservice.controller;

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
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.message.PricingEventPublisher;
import com.sapient.coc.application.pricingservice.service.PricingService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWebMvc
public class PricingControllerTest {

	private static Logger logger = LoggerFactory.getLogger(PricingControllerTest.class);

	private OrderResponse orderResponse = null;
	private OrderPriceResp orderPriceResp = null;
	private CartResponse cartResposne;
	private static JSONObject requestParams;
	private List<OrderItem> items;
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

	String token;

	@BeforeEach
	public void setup() throws Exception {
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(pricingController).build();
		token = "Bearer zxczxczczxczxczxczxc";

		orderResponse = new OrderResponse();
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
		orderResponse.setItem(cartResposne);
		
		orderPriceResp =new OrderPriceResp(); 
		orderPriceResp.setOrderItems(items);
		orderPriceResp.setActualTotal(new Money("USD",new Double(10)));
		
		/*
		 * orderResponse = new OrderResponse(); cartResposne = new CartResponse(); items
		 * = new ArrayList<OrderItem>(); OrderItem orderItem = new OrderItem();
		 * items.add(orderItem); cartResposne.setItems(items);
		 * orderResponse.setItem(cartResposne);l
		 */
	}

	@Test
	public void testApplyItemPromotionForGivenItems() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			ObjectMapper objectMapper = new ObjectMapper();
			//when(pricingService.fetchCartDetails(token, "100")).thenReturn(cartResposne);
			when(pricingService.applyPromotions(token, "100")).thenReturn(orderResponse);
			this.mockMvc
					.perform(get("/pricing/items/100").contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", token).content(objectMapper.writeValueAsString(orderResponse)))
					.andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			Assert.fail("Get pricing method failed in Controller");
			logger.error("Get pricing method in Controller", e);
		}
		
	}

	@Test
	public void testApplyItemPromotionForGivenItems_null() throws Exception {
		String abc = null;
		requestParams = new JSONObject();
		requestParams.put("cartId", abc);
		when(pricingService.applyPromotions(token, null)).thenReturn(null);
		this.mockMvc.perform(get("/pricing/items/abc")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testApplyItemPromotionForGivenItems_empty() throws Exception {

		requestParams = new JSONObject();
		requestParams.put("cartId", "");
		when(pricingService.fetchCartDetails(token, null)).thenReturn(null);
		when(pricingService.applyPromotions(token, null)).thenReturn(null);
		this.mockMvc.perform(get("/pricing/items/")).andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testApplyShippingPricing() throws Exception {

		try {
			requestParams = new JSONObject();
			requestParams.put("cartId", "100");
			ObjectMapper objectMapper = new ObjectMapper();
			when(pricingService.calculateShipping(token)).thenReturn(orderPriceResp);
			this.mockMvc
					.perform(get("/pricing/order").contentType(MediaType.APPLICATION_JSON)
							.header("Authorization", token).content(objectMapper.writeValueAsString(orderPriceResp)))
					.andExpect(status().is2xxSuccessful());
		} catch (Exception e) {
			Assert.fail("Get pricing method failed in Controller");
			logger.error("Get pricing method in Controller", e);
		}
		
	}
}
