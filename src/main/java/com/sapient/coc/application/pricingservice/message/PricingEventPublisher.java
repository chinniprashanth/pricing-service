package com.sapient.coc.application.pricingservice.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.OrderKafkaResponse;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Implements the sender for messages for asynch flows
 *
 * @author pooyadav
 */
@Component
public class PricingEventPublisher {
	
	/**
	 * The Logger for this class.
	 */

	private static Logger logger = LoggerFactory.getLogger(PricingEventPublisher.class);

	@Autowired
	private KafkaTemplate kafkaTemplate;

	public void sendMessage(String topic, OrderKafkaResponse ordResponse) throws CoCSystemException {

		logger.debug("sending price {} to topic {}", ordResponse.getActualTotal(), topic);

		try {
			Message<OrderKafkaResponse> message = MessageBuilder.withPayload(ordResponse)
					.setHeader(KafkaHeaders.TOPIC, topic).build();
			kafkaTemplate.send(topic, ordResponse);

		} catch (Exception e) {
			logger.error("Error occurred while trying to send message", e);
			throw new CoCSystemException("Exception occurred while trying to send message");

		}

	}
}