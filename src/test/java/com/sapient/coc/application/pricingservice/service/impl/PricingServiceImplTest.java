package com.sapient.coc.application.pricingservice.service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.sapient.coc.application.PricingServiceApplication;
import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.service.PricingService;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PricingServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@EnableWebMvc
class PricingServiceImplTest {

	private static Logger logger = LoggerFactory.getLogger(PricingServiceImplTest.class);

	@MockBean
	ProductInfoServiceClient productInfoServiceClient;

	@MockBean
	CartInfoServiceClient cartInfoServiceClient;

	@Autowired
	PricingService pricingService;

	@MockBean
	CartResp cartRespo;

	private CartItem data[];
	private CartResp cartResp;
	private List<Sku> items;

	/*
	 * @TestConfiguration static class PricingServiceImplConfiguration {
	 * 
	 * @Bean public PricingService PricingServiceImpl() { return new
	 * PricingServiceImpl(); } }
	 */

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		cartResp = new CartResp();
		data = new CartItem[2];
		CartItem cartItem = new CartItem();
		cartItem.setSkuId("abc");
		cartItem.setQuantity(2);
		data[0] = cartItem;
		data[1] = cartItem;
		cartResp.setData(data);
		items = new ArrayList<Sku>();
		Sku item = new Sku();
		items.add(item);
	}

	@Test
	final void testFetchCartDetails() {
		when(productInfoServiceClient.getProductDetailsForSapecificItems("abc")).thenReturn(items);
		when(cartInfoServiceClient.getOrderDetails("abc", "abc")).thenReturn(cartResp);
		CartResponse result = pricingService.fetchCartDetails("abc", "abc");
		assertNotNull(result);
	}

}
