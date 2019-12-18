package com.sapient.coc.application.pricingservice.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;

import com.sapient.coc.application.coreframework.bo.Money;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.OrderItemPrice;
import com.sapient.coc.application.pricingservice.bo.vo.OrderKafkaResponse;

public class PricingEventPublisherTest {


	private static Logger logger = LoggerFactory.getLogger(PricingEventPublisherTest.class);

	@InjectMocks
	private PricingEventPublisher sender;

	@Mock
	private KafkaTemplate kafkaTemplate;

	private static OrderKafkaResponse ordResponse;
	private static List<OrderItemPrice> orderItems;

	private static Money money;
	private static String topic = "pricingTopic";

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void test_SendMessage() {
		orderItems = new ArrayList<OrderItemPrice>();
		money = new Money("USD", new Double(10));
		OrderItemPrice itemPrice = new OrderItemPrice("shirt", "100", "100", "STANDARD", new Double(10), 1, money,
				money);
		orderItems.add(itemPrice);
		ordResponse = new OrderKafkaResponse("Created", "user", orderItems, new Date(), new Date(), money, money, money,
				money, money, money, "100");

		try {
			kafkaTemplate.send(topic, ordResponse);
			sender.sendMessage(topic, ordResponse);
		} catch (CoCSystemException e) {
			Assert.fail("Send message failed in PricingEventPublisher");
			logger.error("Send message failed in PricingEventPublisher", e);
		}
	}

	/*
	 * @Test public void test_SendMessage_error() { orderItems = new
	 * ArrayList<OrderItemPrice>(); money = new Money("USD", new Double(10));
	 * OrderItemPrice itemPrice = new OrderItemPrice("shirt", "100", "100",
	 * "STANDARD", new Double(10), 1, money, money); orderItems.add(itemPrice);
	 * ordResponse = new OrderKafkaResponse("Created", "user", orderItems, new
	 * Date(), new Date(), money, money, money, money, money, money, "100");
	 * 
	 * try { sender.sendMessage(null, null); } catch (Exception e) {
	 * logger.debug("Test case passed for Null scenario send message");
	 * assertTrue(true); } }
	 */
}