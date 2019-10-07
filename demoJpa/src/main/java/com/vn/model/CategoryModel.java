package com.vn.model;

public class CategoryModel {

	private long id;
	private String name;
	private String nameParent;

	public CategoryModel() {
		super();
	}

	public CategoryModel(long id, String name, String nameParent) {
		super();
		this.id = id;
		this.name = name;
		this.nameParent = nameParent;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNameParent() {
		return nameParent;
	}

	public void setNameParent(String nameParent) {
		this.nameParent = nameParent;
	}

}
