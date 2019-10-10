package com.vn.model;

import com.vn.jpa.Product;

import javax.persistence.ManyToOne;

public class Cart {

	private Product product;
	private int quantity;

	public Cart() {
	}

	public Cart(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
}
