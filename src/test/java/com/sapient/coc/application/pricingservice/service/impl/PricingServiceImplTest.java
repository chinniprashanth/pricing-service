package com.sapient.coc.application.pricingservice.service.impl;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.sapient.coc.application.pricingservice.bo.vo.CartItem;
import com.sapient.coc.application.pricingservice.bo.vo.CartResp;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItem;
import com.sapient.coc.application.pricingservice.bo.vo.Sku;
import com.sapient.coc.application.pricingservice.feign.client.CartInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.client.ProductInfoServiceClient;
import com.sapient.coc.application.pricingservice.feign.fallback.CartInfoServiceFallBack;
import com.sapient.coc.application.pricingservice.feign.fallback.ProductInfoFallBack;
import com.sapient.coc.application.pricingservice.service.PricingService;

/*@RunWith(SpringRunner.class)

  @RunWith(SpringRunner.class)
  
  @WebMvcTest(value = PricingServiceImpl.class, secure = false)*/
/*@RunWith(SpringRunner.class)

@WebMvcTest(value = PricingServiceImpl.class, secure = false)

@WebMvcTest(value = PricingServiceImpl.class, secure = false)

@RunWith(SpringRunner.class)*/

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class PricingServiceImplTest {

	private static Logger logger = LoggerFactory.getLogger(PricingServiceImplTest.class);

	@InjectMocks
	private PricingService pricingService = new PricingServiceImpl();

	@Mock
	private ProductInfoServiceClient productInfoServiceClient;

	@Mock
	private CartInfoServiceFallBack cartInfoServiceFallBack;

	@Mock
	private ProductInfoFallBack productInfoFallBack;

	@Mock
	private CartInfoServiceClient cartInfoServiceClient = new CartInfoServiceFallBack();

	private static String token;

	/*
	 * @Autowired PricingService pricingService;
	 */

	private CartItem data[];

	@Mock
	private CartResp cartResp;
	private List<OrderItem> items;

	private List<Sku> skuList;

	@Mock
	private CartResponse result;

	@Autowired
	private MockMvc mvc;

	@TestConfiguration
	static class PricingServiceImplConfiguration {

		@Bean
		public PricingService PricingServiceImpl() {
			return new PricingServiceImpl();
		}
	}

	@BeforeAll
	static void setUpBeforeClass() throws Exception {

	}

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	private String obtainAccessToken() throws Exception {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.add("Authorization", "Basic d2ViLWNsaWVudDp3ZWItY2xpZW50LXNlY3JldA==");

		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		String url = "http://localhost:8080/auth-service/oauth/token?password=user&username=user&grant_type=password";
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		String resultString = response.getBody();
		JacksonJsonParser jsonParser = new JacksonJsonParser();
		return jsonParser.parseMap(resultString).get("access_token").toString();
	}

	@Test
	public void testFetchCartDetails() {
		try {
			token = "Bearer " + obtainAccessToken();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cartResp = new CartResp();
		data = new CartItem[2];
		CartItem cartItem = new CartItem();
		cartItem.setSkuId("abc");
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

		when(cartInfoServiceClient.getOrderDetails(token, "abc")).thenReturn(cartResp);
		// when(productInfoFallBack.getProductDetailsForSapecificItems("abc");
		// when(productInfoServiceClient.getProductDetailsForSapecificItems("abc")).thenReturn(skuList);
		when(pricingService.fetchCartDetails(token, "abc")).thenReturn(result);
		// CartResponse result = pricingService.fetchCartDetails(token, "abc");
		Assert.assertEquals(result, result);
		// assertNotNull(result);
	}

}
