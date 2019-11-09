package com.sapient.coc.application.pricingservice.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Logger;
import feign.RequestInterceptor;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@Configuration
public class FeignConfigurationPricing {

	 /**
     * Pre-interceptor for API calls
     *
     * @return
     */
    @Bean
    public RequestInterceptor requestTokenBearerInterceptor() {
		return requestTemplate -> 
            requestTemplate.header("Content-Type", "application/json");
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
	
}