package com.sapient.coc.application.pricingservice.message.config;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.sapient.coc.application.coreframework.message.config.BaseKafkaConsumerConfig;
import com.sapient.coc.application.coreframework.message.config.KafkaJsonDeserializer;
import com.sapient.coc.application.pricingservice.bo.vo.CartResponse;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Configures the Kafka Consumer
 *
 * @author pooyadav
 */
@Configuration
@EnableKafka
public class PricingEventConfig extends BaseKafkaConsumerConfig {
    @Value(value = "${spring.kafka.groupId}")
    private String groupId;

    @Autowired
    private LocalValidatorFactoryBean validator;

    /**
	 * Creates a Consumer Factory for a price kind of a message. Consumer Factory is
	 * made available as a bean named consumerFactory in Application Context
	 *
	 * @return ConsumerFactory
	 */
    @Bean
	public ConsumerFactory<String, CartResponse> getconsumerFactory() {
        Map<String, Object> configs = configs();
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        return new DefaultKafkaConsumerFactory<>(
                configs, new StringDeserializer(), new KafkaJsonDeserializer<>(Map.class));
    }

    /**
     * Creates a Kafka Listener Container Factory for Map type messages. Listener
     * Factory is made available as a bean named kafkaListenerContainerFactory in
     * Application Context. Uses consumerFactory() for the creating the Map listener
     * factory.
     *
     * @return Kafka Map Listener Container Factory
     */
    @Bean
	public ConcurrentKafkaListenerContainerFactory<String, CartResponse> pricingConsumerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, CartResponse> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(getconsumerFactory());

        return factory;
    }
}
