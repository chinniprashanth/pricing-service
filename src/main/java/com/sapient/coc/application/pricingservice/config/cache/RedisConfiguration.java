package com.sapient.coc.application.pricingservice.config.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

import com.sapient.coc.application.coreframework.config.cache.BaseRedisConfiguration;
import com.sapient.coc.application.pricingservice.bo.vo.Address;

/**
 * This class configures the Redis for the application
 *
 * @author khuchaud
 */
@Configuration
@EnableCaching
public class RedisConfiguration extends BaseRedisConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RedisConfiguration.class);

    /**
     * Configures the redis template
     *
     * @return
     */
    @Bean
    @Override
    public RedisTemplate<String, Object> redisTemplate() {
        logger.debug("Configures the redis template");
        RedisTemplate<String, Object> cacheTemplate = super.redisTemplate();
        cacheTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
        return cacheTemplate;
    }

    /**
     * Creates the JSON serializers
     *
     * @return
     */
    @Bean
    public Jackson2JsonRedisSerializer jacksonJsonRedisJsonSerializer() {
		return new Jackson2JsonRedisSerializer<>(Address.class);
    }

}
