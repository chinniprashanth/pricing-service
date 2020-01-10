package com.sapient.coc.application.pricingservice.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;

import com.sapient.coc.application.pricingservice.bo.vo.ProfileResponse;

import feign.Headers;
import feign.Param;
import feign.RequestLine;


/*******************************************************
 * Copyright (c) 2019 CommerceOnCloud, PublicisSapient
 *
 * This file is part of CommerceOnCloud project.
 *
 * CommerceOnCloud can not be copied and/or distributed without the express
 * permission of PublicisSapient
 *******************************************************/

/**
 * Feign for Profile  service calls
 *
 * @author ashabrol
 */
@FeignClient(name = "profile-detail", url = "${application.profile.client.url}")
public interface ProfileServiceClient {


	@RequestLine("GET /v1/user")
	@Headers({ "Authorization: bearer {token}", "Accept: application/json", "Content-Type: application/json" })
	ResponseEntity<ProfileResponse> getProfileInfo(@Param(value = "token") String token);


}