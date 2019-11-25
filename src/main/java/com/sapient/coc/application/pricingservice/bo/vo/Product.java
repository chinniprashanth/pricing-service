package com.sapient.coc.application.pricingservice.bo.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pooyadav
 *
 */
public class Product {

	private String id;
	private String name;
	private String type;
	private String description;
	private List<String> category_ids;
	private List<String> category_names;
	private List<Item> items;
	private Category category;
	private List<Images> images;
	private String numberOfSKUs;
	private List<Price> prices;
	private String brand;

	public String getId() {
		return id;
	}

	public String getNumberOfSKUs() {
		return numberOfSKUs;
	}

	public void setNumberOfSKUs(String numberOfSKUs) {
		this.numberOfSKUs = numberOfSKUs;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public List<String> getCategory_ids() {
		return category_ids;
	}

	public void setCategory_ids(List<String> category_ids) {
		this.category_ids = category_ids;
	}

	public List<String> getCategory_names() {
		return category_names;
	}

	public void setCategory_names(List<String> category_names) {
		this.category_names = category_names;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Item> getItems() {
		if (items == null) {
			items = new ArrayList<Item>();
		}
		return items;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getBrand() {
		return brand;
	}

	public List<Images> getImages() {
		if (images == null) {
			images = new ArrayList<Images>();
		}
		return images;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<Price> getPrices() {
		if (prices == null) {
			prices = new ArrayList<Price>();
		}
		return prices;
	}

	@Override
	public String toString() {
		return "Product{" + "id='" + id + '\'' + ", name='" + name + '\'' + ", type='" + type + '\'' + ", description='"
				+ description + '\'' + ", items=" + items + '}';
	}
}
