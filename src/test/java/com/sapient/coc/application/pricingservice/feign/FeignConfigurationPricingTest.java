package com.sapient.coc.application.pricingservice.feign;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sapient.coc.application.PricingServiceApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PricingServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWebMvc
class FeignConfigurationPricingTest {

	@TestConfiguration
	static class FeignConfigurationTestContextConfiguration {

		@Bean
		public FeignConfigurationPricing feignConfigurationPricing() {
			return new FeignConfigurationPricing();
		}

	}

	@Autowired
	FeignConfigurationPricing feignConfigurationPricing;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	public void testRequestTokenBearerInterceptor() {
		// TODO
	}

	@Test
	public void testFeignLoggerLevel() {
		// TODO
	}

}
