package com.sapient.coc.application.pricingservice.cukes;

import java.io.File;

import org.junit.AfterClass;
import org.junit.runner.RunWith;

import com.cucumber.listener.Reporter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "com.sapient.coc.application.pricingservice.stepdef" }, features = {
		"src/test/java/feature" }, strict = false, plugin = {
				"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html" })
public class CucumberIntegrationTest extends SpringIntegrationTest {
	@AfterClass
	public static void writeExtentReport() {
		Reporter.loadXMLConfig(new File("src/test/configs/extent-config.xml"));
	}
}
