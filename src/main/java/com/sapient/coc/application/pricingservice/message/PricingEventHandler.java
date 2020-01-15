package com.sapient.coc.application.pricingservice.message;

import javax.validation.Valid;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sapient.coc.application.pricingservice.bo.vo.AddressVO;
import com.sapient.coc.application.pricingservice.cache.CacheDao;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Implements the listener for the changes to the order status
 *
 * @author ashabrol
 */
@Component
public class PricingEventHandler {

	/**
	 * The Logger for this class.
	 */
	private static Logger logger = LoggerFactory.getLogger(PricingEventHandler.class);

	@Autowired
	private CacheDao redis;

	private static final String ADDRESS_KEY = "CoC-Shipping-Addr";



	@KafkaListener(groupId = "${spring.kafka.addressGroupId}", topics = "${spring.kafka.address.topic.name}", containerFactory = "${spring.kafka.addressConsumerFactory}")
	public AddressVO receiveMessage(@Payload @Valid ConsumerRecord object) {
		logger.debug("received data='{}'", object);

		try {
			final AddressVO address = new ObjectMapper().convertValue(object.value(), AddressVO.class);

			redis.findAddressById(ADDRESS_KEY + address.getOrderId());
			redis.remove(ADDRESS_KEY + address.getOrderId());
			redis.save(ADDRESS_KEY + address.getOrderId(), address);
			return address;
		} catch (Exception e) {
			logger.error("Exception in updating address", e);
		}
		return null;
	}
}
