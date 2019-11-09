package com.sapient.coc.application.pricingservice.stepdef;

import com.sapient.coc.application.pricingservice.cukes.SpringIntegrationTest;

import cucumber.api.PendingException;
import cucumber.api.java8.En;

public class PricingServiceStepDefs extends SpringIntegrationTest implements En {

	private static final String GET_PRICE = "/pricing/items/";

	public PricingServiceStepDefs() {

		Given("^user cart is not empty and should contain items in cart$", () -> {
			System.out.println("it is in Given");
			throw new PendingException();
		});

		When("^user goes to cart page/ views cart$", () -> {
			System.out.println("it is in When");
			throw new PendingException();
		});

		Then("^price should be displayed for each item in cart and the total amount of the cart$", () -> {
			System.out.println("it is in Then");
			throw new PendingException();
		});

	}
}
