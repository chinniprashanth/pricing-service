package com.sapient.coc.application.pricingservice.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.sapient.coc.application.pricingservice.bo.entity.CartPrice;


/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * This encapsulates all the calls to the datastore for pricing
 *
 * @author pooyadav
 */
@Repository
public interface PriceRepository extends CrudRepository<CartPrice, String> {
	@Query("SELECT * FROM carttotal WHERE cartId=?0")
	Optional<CartPrice> findByCartId(String id);

	@Query("delete from carttotal where id=?0")
	void deleteCart(final String id);

}
