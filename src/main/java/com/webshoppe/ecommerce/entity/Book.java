package com.webshoppe.ecommerce.entity;

import java.math.BigDecimal;

public class Book extends Product{
    private String id;
    private String name;
    private String description;
    private String aid;
    private BigDecimal price;

    public Book() {
    }


	public Book(String id, String name, String description, String aid, BigDecimal price) {
		super(id, name, description, price);
		this.aid = aid;
		// TODO Auto-generated constructor stub
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

//	@Override
//    public int hashCode() {
//        return this.id.hashCode() * 31;
//    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Book)) {
            return false;
        }
        Book otherBook = (Book)obj;
        return this.id.equals(otherBook.getId()) && this.name.equals(otherBook.getName())
               && this.description.equals(otherBook.getDescription()) && this.aid.equals(otherBook.getAid())
               && this.price.equals(otherBook.getPrice());
    }

}
