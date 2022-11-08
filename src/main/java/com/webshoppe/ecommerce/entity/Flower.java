package com.webshoppe.ecommerce.entity;

import java.math.BigDecimal;

public class Flower extends Product {
	private String id;
	private String name;
	private String description;
	private BigDecimal price;

	public Flower() {
	}

	public Flower(String id, String name, String description, BigDecimal price) {
		super(id, name, description, price);
	}

//	@Override
//	public int hashCode() {
//		return this.id.hashCode() * 31;
//	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Book)) {
			return false;
		}
		Product otherFlower = (Flower) obj;
		return this.id.equals(otherFlower.getId()) && this.name.equals(otherFlower.getName())
				&& this.description.equals(otherFlower.getDescription()) && this.price.equals(otherFlower.getPrice());
	}

}
