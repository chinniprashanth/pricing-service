package com.sapient.coc.application.pricingservice.cache;

import java.util.Map;

import com.sapient.coc.application.pricingservice.bo.vo.AddressVO;

/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Encapsulates all the cache requests
 *
 * @author pooyadav
 */
public interface CacheDao {

	/**
	 * Saves address details in Redis
	 * 
	 * @param key
	 * @param value
	 * @return boolean
	 */
	boolean save(String key, AddressVO value);

	/**
	 * Finds address details by key
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, AddressVO> findAddressById(String key);

	boolean existsById(String key);

	boolean remove(String key);

}
