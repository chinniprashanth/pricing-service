package com.sapient.coc.application.pricingservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.coc.application.pricingservice.bo.vo.OrderResponse;
import com.sapient.coc.application.pricingservice.service.PricingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient This file is part of
 * CommerceOnCloud project.
 * 
 * @author pooyadav
 */
@RestController
@RequestMapping("/pricing")
@Api(value = "Pricing Service", tags = { "Pricing Service" })
public class PricingController {

	private static final Logger logger = LoggerFactory.getLogger(PricingController.class);

	@Autowired
	private PricingService pricingService;


	@RequestMapping(value = "/items/{cartId}", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Applied given promotion for given items", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad Request | cart not found") })
	@ApiOperation(value = "${applyItemPricing.ApiOperation.value}", notes = "${applyItemPricing.ApiOperation.notes}", httpMethod = "GET", produces = "application/json", responseContainer = "Map", tags = {
			"Pricing Service" })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cartId  ", value = "cart id", required = true, dataType = "String") })
	public ResponseEntity<OrderResponse> applyItemPricing(
			@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Cart Id for which pricing need to be applied", required = true) @PathVariable String cartId) {
		logger.info("Entering the applyItemPricing method in PricingController for cart id {}", 0);
		if (cartId == null || cartId.isEmpty())
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		OrderResponse price = pricingService.applyPromotions(authorization, cartId);
		logger.info("End the applyItemPricing method in PricingController {}", 0);
		return new ResponseEntity<>(price, HttpStatus.OK);
	}

	@RequestMapping(value = "/items/shipping", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Applied given promotion for given items", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad Request | order not found") })
	@ApiOperation(value = "${applyShippingPricing.ApiOperation.value}", notes = "${applyShippingPricing.ApiOperation.notes}", httpMethod = "GET", produces = "application/json", responseContainer = "Map", tags = {
			"Pricing Service" })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cartId  ", value = "cart id", required = true, dataType = "String") })
	public ResponseEntity<OrderResponse> applyShippingPricing(@RequestHeader("Authorization") String authorization) {
		logger.info("Entering the applyItemPricing method in PricingController for cart id {}", 0);
		OrderResponse price = pricingService.calculateShipping(authorization);
		logger.info("End the applyItemPricing method in PricingController {}", 0);
		return new ResponseEntity<>(price, HttpStatus.OK);
	}

}
