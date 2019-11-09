package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pooyadav
 *
 */
public class Item {

	private String id;
	private String type;
	private String name;
	private String origin;
	private String description;
	private String fulfillment;
	private List<Price> prices;
	private List<Inventory> inventories;
	private List<SkuAttribute> attributes;
	private List<Images> images;

	public Item() {
	}

	public Item(String id, String type, String name) {
		this.id = id;
		this.type = type;
		this.name = name;
	}

	public String getFulfillment() {
		return fulfillment;
	}

	public void setFulfillment(String fulfillment) {
		this.fulfillment = fulfillment;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Price> getPrices() {
		if (prices == null) {
			prices = new ArrayList<>();
		}
		return prices;
	}

	public List<Inventory> getInventories() {
		if (inventories == null) {
			inventories = new ArrayList<>();
		}
		return inventories;
	}

	public List<SkuAttribute> getAttributes() {

		if (attributes == null) {
			attributes = new ArrayList<>();
		}
		return attributes;
	}

	public List<Images> getImages() {
		if (images == null) {
			images = new ArrayList<>();
		}
		return images;
	}

	@Override
	public String toString() {
		return "Item{" + "id='" + id + '\'' + ", type='" + type + '\'' + ", name='" + name + '\'' + '}';
	}
}
