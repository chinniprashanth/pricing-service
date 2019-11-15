package com.sapient.coc.application.pricingservice.stepdef;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.junit.Assert;

import com.sapient.coc.application.pricingservice.cukes.SpringIntegrationTest;

import cucumber.api.java8.En;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;


public class PricingServiceStepDefs extends SpringIntegrationTest implements En {

	private static final String GET_PRICE = "/pricing/items/37131670-06b0-11ea-8937-9b9c92ca95d0";
	private static final String GET_SHIPPING_PRICE = "/pricing/items/shipping";
	public static RequestSpecification httpRequest;
	public static ResponseSpecification httpResponse;
	public static Response response;
	private static JSONObject requestParams;
	private static String cartId = "37131670-06b0-11ea-8937-9b9c92ca95d0";
	private static final String OAUTH_SVC_URL = "http://34.67.111.77:8080/auth-service/oauth/token";


	public PricingServiceStepDefs() {

		RestAssured.baseURI = "http://localhost:12789";
		httpRequest = RestAssured.given();

		String token;
		String onlyToken;
		try {
			token = "Bearer " + obtainAccessToken();
			onlyToken = obtainAccessToken();
			httpRequest.header("Content-Type", "application/json");
			httpRequest.header("Authorization", token);

			JSONObject requestParams1 = new JSONObject();
			requestParams1.put("cartId", cartId);

			JSONObject requestParams2 = new JSONObject();
			requestParams1.put("cartId", cartId);


		Given("^user cart is not empty and should contain items in cart$", () -> {
				if (null != cartId)
					try {
						requestParams1.put("cartId", cartId);
						requestParams1.put("token", onlyToken);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		});

		When("^user goes to cart page/ views cart$", () -> {

				httpRequest.body(requestParams1.toString()).get(GET_PRICE).andReturn();
		});

		Then("^price should be displayed for each item in cart and the total amount of the cart$", () -> {
				response = httpRequest.get(GET_PRICE);
				response.then().assertThat().statusCode(200);
				Assert.assertTrue(response.time() < 800);
		});
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		When("^user clicks on checkout on shipping page", () -> {

			httpRequest.get(GET_SHIPPING_PRICE).andReturn();
		});

		Then("^User should be able to see the applied prices for shipping$", () -> {
			response = httpRequest.get(GET_PRICE);
			response.then().assertThat().statusCode(200);
			Assert.assertTrue(response.time() < 800);
		});
	}

	private static String obtainAccessToken() throws Exception {

		Response oauthResponse = RestAssured.given().auth().basic("web-client", "web-client-secret")
				.formParam("grant_type", "client_credentials").when().post(OAUTH_SVC_URL).andReturn();

		String access_token = oauthResponse.getBody().jsonPath().get("access_token");
		return access_token;
	}
}
