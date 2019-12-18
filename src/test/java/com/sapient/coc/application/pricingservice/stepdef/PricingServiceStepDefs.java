package com.sapient.coc.application.pricingservice.stepdef;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sapient.coc.application.pricingservice.cukes.SpringIntegrationTest;

import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class PricingServiceStepDefs extends SpringIntegrationTest implements En {

	private static final String GET_CART_PRICE = "/items/a7dab600-f617-11e9-842c-631303ad6c3f";
	private static final String GET_ORDER_PRICE = "/order";
	public static RequestSpecification httpRequest;
	public static ResponseSpecification httpResponse;
	public static Response response;
	public static String PRICE_BASE_URI = "http://35.241.4.242/pricing";
	private static String cartId = "a7dab600-f617-11e9-842c-631303ad6c3f";
	private static final String OAUTH_SVC_URL = "http://35.241.4.242/auth-service/oauth/token";
	private static String token;
	private static Logger logger = LoggerFactory.getLogger(PricingServiceStepDefs.class);

	public PricingServiceStepDefs() {

		RestAssured.baseURI = PRICE_BASE_URI;
		httpRequest = RestAssured.given();

		try {
			JSONObject requestParams = new JSONObject();
			token = "Bearer " + obtainAccessToken();
			logger.debug("token is {}", token);
			// obtainAccessToken();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", token);

		Given("^user cart is not empty and should contain items in cart$", () -> {
				if (null != cartId)
					try {
						requestParams.put("cartId", cartId);
						requestParams.put("token", token);
					} catch (JSONException e) {
						e.printStackTrace();
					}
		});

		When("^user goes to cart page/ views cart$", () -> {

				httpRequest.body(requestParams.toString()).get(GET_CART_PRICE).andReturn();
		});

		Then("^price should be displayed for each item in cart and the total amount of the cart$", () -> {
				response = httpRequest.get(GET_CART_PRICE);
				response.then().assertThat().statusCode(200);
				Assert.assertTrue(response.time() < 2400);
		});

		} catch (Exception e) {
			e.printStackTrace();
		}

		When("^user clicks on checkout on shipping page", () -> {

			httpRequest.get(GET_ORDER_PRICE).andReturn();
		});

		Then("^User should be able to see the applied prices for shipping$", () -> {
			response = httpRequest.get(GET_ORDER_PRICE);
			response.then().assertThat().statusCode(200);
			Assert.assertTrue(response.time() < 2800);
		});
	}

	private static String obtainAccessToken() throws Exception {

		Response oauthResponse = RestAssured.given().auth().basic("web-client", "web-client-secret")
				.formParam("grant_type", "client_credentials").when().post(OAUTH_SVC_URL).andReturn();

		String access_token = oauthResponse.getBody().jsonPath().get("access_token");
		return access_token;
	}
}
