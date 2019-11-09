package com.sapient.coc.application.pricingservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.service.impl.PricingServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PricingControllerTest {

	private static Logger logger = LoggerFactory.getLogger(PricingControllerTest.class);

	@Autowired
	private PricingController pricingController;

	@TestConfiguration
	static class PricingControllerTestContextConfiguration {

		@Bean
		public PricingController pricingController() {
			return new PricingController();
		}

	}

	@MockBean
	private PricingServiceImpl pricingService;

	@MockBean
	private CartInfoServiceClient cartInfoServiceClient;

	@MockBean
	private ProductInfoServiceClient productInfoServiceClient;

	@Autowired
	private MockMvc mvc;

	private OrderResponse orderResponse = null;
	private CartResponse cartResposne;
	private static JSONObject requestParams;
	private List<OrderItem> items;
	private static String token;

	@Before
	public void setUp() throws Exception {
		this.mvc = MockMvcBuilders.standaloneSetup(pricingController).build();
		token = "Bearer " + obtainAccessToken();

		orderResponse = new OrderResponse();
		cartResposne = new CartResponse();
		items = new ArrayList<OrderItem>();
		OrderItem orderItem = new OrderItem();
		items.add(orderItem);
		cartResposne.setItems(items);
		orderResponse.setItem(cartResposne);

	}

	private String obtainAccessToken() throws Exception {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic d2ViLWNsaWVudDp3ZWItY2xpZW50LXNlY3JldA==");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		String url = "http://localhost:8080/auth-service/oauth/token?password=user&username=user&grant_type=password";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String resultString = response.getBody();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

	@Test
	public void testApplyItemPromotionForGivenItems() throws Exception {

		requestParams = new JSONObject();
		requestParams.put("cartId", "100");
		when(pricingService.fetchCartDetails(token, "100")).thenReturn(cartResposne);
		when(pricingService.applyPromotions(token, "100")).thenReturn(orderResponse);
		this.mvc.perform(get("/pricing/items/100").contentType(MediaType.APPLICATION_JSON)
				.header("Authorization", token)
				.content(requestParams.toString())).andExpect(status().is2xxSuccessful());
	}

}
