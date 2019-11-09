package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;

public class CartResp implements Serializable {

	private static final long serialVersionUID = 2335688676823292750L;

	private CartItem data[];

	private String code;

	private String status;

	private String traceId;

	private String spanId;



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTraceId() {
		return traceId;
	}

	public void setTraceId(String traceId) {
		this.traceId = traceId;
	}

	public String getSpanId() {
		return spanId;
	}

	public void setSpanId(String spanId) {
		this.spanId = spanId;
	}

	public CartItem[] getData() {
		return data;
	}

	public void setData(CartItem[] data) {
		this.data = data;
	}

}