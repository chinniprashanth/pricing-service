package com.sapient.coc.application.pricingservice.bo.vo;

import java.io.Serializable;

/**
 * @author pooyadav
 *
 */
public class OrderResponse implements Serializable {

	private static final long serialVersionUID = 1280671591829236813L;
	private CartResponse item;

	// private ShippingResponse shipping;
	// private PaymentResponse payment;
	public CartResponse getItem() {
		return item;
	}
	public void setItem(CartResponse item) {
		this.item = item;
	}
	/*
	 * public ShippingResponse getShipping() { return shipping; } public void
	 * setShipping(ShippingResponse shipping) { this.shipping = shipping; } public
	 * PaymentResponse getPayment() { return payment; } public void
	 * setPayment(PaymentResponse payment) { this.payment = payment; }
	 */
	
	@Override
	public String toString() {
		if(item!=null) {
			/*
			 * if (shipping!= null && payment !=null) { return item.toString() + "-"
			 * +shipping.toString() + "-" + payment.toString(); } else if (shipping!= null)
			 * return item.toString() + "-" +shipping.toString(); else if (payment !=null)
			 * return item.toString() + "-" + payment.toString(); else
			 */
			return item.toString();
		}
		return "Empty Order";
	}
	
}