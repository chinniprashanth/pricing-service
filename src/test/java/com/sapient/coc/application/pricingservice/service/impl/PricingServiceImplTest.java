package com.sapient.coc.application.pricingservice.service.impl;

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
import com.sapient.coc.application.pricingservice.feign.client.FulfillmentServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.service.PricingService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = PricingServiceImpl.class, secure = false)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PricingServiceImplTest {

	private static Logger logger = LoggerFactory.getLogger(PricingServiceImplTest.class);

	@InjectMocks
	private PricingService pricingService = new PricingServiceImpl();

	@MockBean
	private CartInfoServiceClient cartInfoServiceClients;

	@MockBean
	private ProductInfoServiceClient productInfoServiceClients;

	@MockBean
	private FulfillmentServiceClient fulfillmentServiceClient;

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
	public void testApplyItemPromotionForGivenItems() throws Exception {

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
		item.setQuantity(2);
		item.setSkuId("adasdsad");
		items.add(item);
		// result.setItems(items);
		skuList = new ArrayList<Sku>();
		Sku itemDetail = new Sku();
		itemDetail.setSaleprice(20);
		itemDetail.setListprice(20);
		itemDetail.setParentproductid("12345");
		itemDetail.setDescription("Full sleeves shirt");
		itemDetail.setName("Shirt");
		skuList.add(itemDetail);
		List<OrderItem> orderItems = null;
		when(cartInfoServiceClients.getOrderDetails(token, "100")).thenReturn(cartResp);
		when(productInfoServiceClients.getProductDetailsForSapecificItems("100")).thenReturn(skuList);
		/*
		 * skuList.forEach(skuDetail -> { OrderItem orderItem = new OrderItem();
		 * orderItem.setItemId(skuDetail.getId()); orderItem.setQty(2);
		 * orderItem.setSalePrice(new Double(skuDetail.getSaleprice()));
		 * orderItem.setListPrice(new Double(skuDetail.getListprice()));
		 * orderItem.setSkuId(skuDetail.getId());
		 * orderItem.setItemsTotalPrice(orderItem.getListPrice() * orderItem.getQty());
		 * orderItem.setItemDiscountedPrice(orderItem.getSalePrice() *
		 * orderItem.getQty()); orderItem.setProductId(skuDetail.getParentproductid());
		 * orderItem.setItemDescription(skuDetail.getDescription());
		 * orderItem.setItemName(skuDetail.getName()); orderItems.add(orderItem); });
		 */
		CartResponse result = pricingService.fetchCartDetails(token, "100");
		assertNotNull(result);
	}



}
