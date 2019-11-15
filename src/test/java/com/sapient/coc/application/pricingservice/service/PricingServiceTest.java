package com.sapient.coc.application.pricingservice.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.service.impl.PricingServiceImpl;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PricingServiceImpl.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PricingServiceTest {

	private static Logger logger = LoggerFactory.getLogger(PricingServiceTest.class);

	@InjectMocks
	private PricingService pricingService = new PricingServiceImpl();

	@MockBean
	private CartInfoServiceClient cartInfoServiceClients;

	@MockBean
	private ProductInfoServiceClient productInfoServiceClients;

	private CartResponse orderResponse = null;

	@MockBean
	private CartResp cartResp;

	private List<OrderItem> items;
	private static String token;
	private CartItem data[];
	private List<Sku> skuList;

	@Mock
	private CartResponse result;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

	}


	@Test
	public void testApplyPromotions() throws Exception {

		token = "Bearer asKJSSLKajslASasjlAS";
		cartResp = new CartResp();
		data = new CartItem[2];
		CartItem cartItem = new CartItem();
		cartItem.setSkuId("100");
		cartItem.setQuantity(2);
		data[0] = cartItem;
		data[1] = cartItem;
		cartResp.setData(data);
		items = new ArrayList<OrderItem>();
		OrderItem item = new OrderItem();
		item.setListPrice(20);
		item.setItemDescription("sadad");
		item.setItemsTotalPrice(77);
		item.setQty(2);
		item.setSkuId("adasdsad");
		items.add(item);
		// result.setItems(items);
		skuList = new ArrayList<Sku>();
		Sku sku = new Sku();
		sku.setId("abc");
		skuList.add(sku);

		when(cartInfoServiceClients.getOrderDetails(token, "100")).thenReturn(cartResp);
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100")).thenReturn(skuList);

		CartResponse result = pricingService.fetchCartDetails(token, "100");
		assertNotNull(result);
	}


}
