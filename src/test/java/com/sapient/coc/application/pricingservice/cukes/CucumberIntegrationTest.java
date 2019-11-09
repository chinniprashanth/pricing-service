package com.sapient.coc.application.pricingservice.cukes;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.sapient.coc.application.pricingservice.stepdef" }, features = {
		"src/main/test/feature" }, strict = true)
public class CucumberIntegrationTest extends SpringIntegrationTest {
}