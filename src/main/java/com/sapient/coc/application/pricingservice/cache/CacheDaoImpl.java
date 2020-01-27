package com.sapient.coc.application.pricingservice.cache;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import com.sapient.coc.application.pricingservice.bo.vo.AddressVO;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

@Component
/**
 * Concrete Implementation that encapsulates all the cache requests
 *
 * @author pooyadav
 */
@Repository
public class CacheDaoImpl implements CacheDao {
	/**
	 * The Logger for this class.
	 */
	private static Logger logger = LoggerFactory.getLogger(CacheDaoImpl.class);

	private RedisTemplate<String, Object> redisTemplate;
	private HashOperations hashOperations;

	@Autowired
	public CacheDaoImpl(RedisTemplate<String, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	private void init() {
		hashOperations = redisTemplate.opsForHash();
	}

	/**
	 * Finds address by key
	 * 
	 * @param key
	 * @return Map<String, AddressVO>
	 */
	public Map<String, AddressVO> findAddressById(String key) {
		logger.debug("Finding Available address zipcode in Cache {} ", key);
		Map<String, AddressVO> entries = hashOperations.entries(key);

		if (ObjectUtils.isEmpty(entries)) {
			logger.debug("entry not found in redis {}");
		} else {
			logger.warn("found entry in redis for {}");
		}
		return entries;
	}

	/**
	 * Saves address details in cache
	 * 
	 * @param String, AddressVO
	 * @return boolean
	 */
	@Override
	public boolean save(String key, AddressVO value) {
		logger.debug("Saving Available zipcode in Cache {} ", key);
		if (null != value && null != value.getZipcode() && !value.getZipcode().isEmpty()) {
			hashOperations.put(key, "methods", value);
			return true;
		} else {
			logger.warn("Address did not return zipcode");
			return false;
		}
	}

	/**
	 * Check if a zipcode already exists in cache
	 *
	 * @param key
	 * @return
	 */
	@Override
	public boolean existsById(String key) {
		return hashOperations.entries(key).size() > 0;
	}

	/**
	 * deletes the key from redis
	 *
	 * @param key
	 */
	@Override
	public boolean remove(String key) {
		return (redisTemplate.delete(key)).booleanValue();
	}

}
