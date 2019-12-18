package com.sapient.coc.application.pricingservice.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.coc.application.coreframework.controller.BaseController;
import com.sapient.coc.application.coreframework.exception.CoCBusinessException;
import com.sapient.coc.application.coreframework.exception.CoCSystemException;
import com.sapient.coc.application.pricingservice.bo.vo.OrderPriceResp;
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
public class PricingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(PricingController.class);
	private static final String CANT_FETCH_PRICING = "Can't fetch pricing details";

	@Autowired
	private PricingService pricingService;

	/**
	 * API to fetch cart item price and dynamic price as well
	 * 
	 * @param authorization
	 * @param cartId
	 * @return
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	@RequestMapping(value = "/items/{cartId}", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Applied given promotion for given items", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad Request | cart not found") })
	@ApiOperation(value = "${applyItemPricing.ApiOperation.value}", notes = "${applyItemPricing.ApiOperation.notes}", httpMethod = "GET", produces = "application/json", responseContainer = "Map", tags = {
			"Pricing Service" })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "cartId  ", value = "cart id", required = true, dataType = "String") })
	public ResponseEntity<OrderResponse> applyItemPricing(@RequestHeader("Authorization") String authorization,
			@ApiParam(value = "Cart Id for which pricing need to be applied", required = true) @PathVariable String cartId)
			throws CoCBusinessException, CoCSystemException {
		logger.info("Entering the applyCartPricing method in PricingController for cart id {}", 0);
		return Optional.ofNullable(pricingService.applyCartPricing(authorization, cartId))
				.map(result -> ResponseEntity.ok().body(result))
				.orElseThrow(() -> new CoCBusinessException(CANT_FETCH_PRICING));
	}

	/**
	 * API to fetch order pricing details
	 * 
	 * @param authorization
	 * @return
	 * @throws CoCBusinessException
	 * @throws CoCSystemException
	 */
	@RequestMapping(value = "/order", method = RequestMethod.GET, produces = "application/json")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Applied shipping and order pricing ", response = ResponseEntity.class),
			@ApiResponse(code = 400, message = "Bad Request | order not found") })
	@ApiOperation(value = "${applyOrderPricing.ApiOperation.value}", notes = "${applyOrderPricing.ApiOperation.notes}", httpMethod = "GET", produces = "application/json", responseContainer = "Map", tags = {
			"Pricing Service" })
	public ResponseEntity<OrderPriceResp> applyOrderPricing(@RequestHeader("Authorization") String authorization)
			throws CoCBusinessException, CoCSystemException {
		logger.info("Entering the applyOrderPricing method in PricingController for cart id {}", 0);
		return Optional.ofNullable(pricingService.calculateOrderPrice(authorization))
				.map(result -> ResponseEntity.ok().body(result))
				.orElseThrow(() -> new CoCBusinessException(CANT_FETCH_PRICING));
	}

}
