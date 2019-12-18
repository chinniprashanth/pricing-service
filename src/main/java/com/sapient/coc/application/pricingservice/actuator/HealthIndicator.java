package com.sapient.coc.application.pricingservice.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import com.sapient.coc.application.pricingservice.service.impl.PricingServiceImpl;

/**
 * The CartHealthIndicator is a custom Spring Boot Actuator HealthIndicator
 * implementation. HealthIndicator will be invoked when the Actuator 'health'
 * endpoint is invoked. Each HealthIndicator class assesses some portion of the
 * application's health, returing a Health object which indicates that status
 * and, optionally, additional health attributes.
 *
 * @author pooyadav
 */
@Component("CartHealthIndicator")
public class HealthIndicator implements org.springframework.boot.actuate.health.HealthIndicator {

    /**
     * The CartHealthIndicator business service.
     */
    @Autowired
	private PricingServiceImpl service;

    @Override
    public Health health() {

        Long count = service.count();

        if (count == null || count == 0) {
            return Health.down().withDetail("count", 0).build();
        }

        return Health.up().withDetail("count", count).build();
    }
}