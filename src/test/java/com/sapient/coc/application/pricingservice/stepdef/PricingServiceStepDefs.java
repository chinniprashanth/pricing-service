package com.sapient.coc.application.pricingservice.stepdef;

import java.util.Collections;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sapient.coc.application.pricingservice.cukes.SpringIntegrationTest;

import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class PricingServiceStepDefs extends SpringIntegrationTest implements En {

	private static final String GET_CART_PRICE = "pricing/items/";
	private static final String GET_ORDER_PRICE = "pricing/order";
	public static RequestSpecification httpRequest;
	public static RequestSpecification httpPriceRequest;
	public static ResponseSpecification httpResponse;
	public static Response response;
	public static String PRICE_BASE_URI = "http://35.241.4.242/";
	private static String cartId = "34348df-ewe8302fduw283283hj";
	private static String token;
	public static String productId = "12732";
	private static String skuId = "12733";
	private static int quantity = 1;
	private static final String CART_URL = "v1/cart/";
	private static Logger logger = LoggerFactory.getLogger(PricingServiceStepDefs.class);
	private static HttpHeaders headers = new HttpHeaders();

	public PricingServiceStepDefs() {

		RestAssured.baseURI = PRICE_BASE_URI;
		httpRequest = RestAssured.given();
		httpPriceRequest = RestAssured.given();

		try {
			token = "Bearer " + obtainAccessToken("admin");
			logger.debug("token is {}", token);
			// obtainAccessToken();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", token);
			httpPriceRequest.header("Content-Type", "application/json");
			httpPriceRequest.header("Authorization", token);
			httpPriceRequest.header("Client", "coc");
			JSONObject addToCartReq = new JSONObject();
			addToCartReq.put("productId", productId);
			addToCartReq.put("quantity", quantity);
			addToCartReq.put("skuId", skuId);

			Given("^user cart is not empty and should contain items in cart$", () -> {
				if (null != cartId)
					try {
						addToCartReq.put("token", token);
						response = httpRequest.body(addToCartReq.toString()).post(CART_URL).andReturn();
					} catch (JSONException e) {
						e.printStackTrace();
					}
			});

			When("^user goes to cart page/ views cart$", () -> {
				cartId = response.getBody().jsonPath().get("data");
				httpPriceRequest.get(GET_CART_PRICE + cartId).andReturn();
			});

			Then("^price should be displayed for each item in cart and the total amount of the cart$", () -> {
				response = httpPriceRequest.get(GET_CART_PRICE + cartId);
				response.then().assertThat().statusCode(200);
				Assert.assertTrue(response.time() < 2400);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}

		When("^user clicks on checkout on shipping page", () -> {

			httpRequest.get(GET_ORDER_PRICE).andReturn();
			String getCheckoutCart = "v1/cart/checkout/" + cartId;
			response = httpRequest.get(getCheckoutCart).andReturn();
			httpRequest.get(GET_ORDER_PRICE).andReturn();
		});

		Then("^User should be able to see the applied prices for shipping$", () -> {
			response = httpRequest.get(GET_ORDER_PRICE);
			response.then().assertThat().statusCode(200);
			Assert.assertTrue(response.time() < 2800);
		});
	}

	private static String obtainAccessToken(String user) throws Exception {
		user = "admin";
		RestTemplate restTemplate = new RestTemplate();
		headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic d2ViLWNsaWVudDp3ZWItY2xpZW50LXNlY3JldA==");
		// this is the auth code of the cart service. Any change in the auth would
		// require changes in this auth string
		HttpEntity<String> entity = new HttpEntity<>("body", headers);

		String url = "http://35.241.4.242/auth-service/oauth/token?password=" + user + "&username=" + user
				+ "&grant_type=password";

		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String resultString = response.getBody();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}
}
