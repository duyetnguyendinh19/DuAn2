package com.vn.jpa;

import javax.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", nullable = true)
	private Category parent;

	@OneToMany(mappedBy = "parent" , fetch = FetchType.LAZY)
	private List<Category> children = new ArrayList<Category>();

	@Column(name = "isactive" , columnDefinition = "CHAR")
	private String isActive;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date date;

	@Column(name = "isdelete", columnDefinition = "CHAR")
	private String isDelete;

	public Category() {
	}

	public Category(Long id, String name, Category parent, List<Category> children, String isActive, Date date,
			String isDelete) {
		super();
		this.id = id;
		this.name = name;
		this.parent = parent;
		this.children = children;
		this.isActive = isActive;
		this.date = date;
		this.isDelete = isDelete;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		if(isDelete == null) {
			isDelete = "N";
		}
		this.isDelete = isDelete;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	@PrePersist
	 public void prePersist( ) {
		 if(this.date == null )
			 this.date = new Date();
	 }

	public static enum status {
		ACTIVE(0), INACTIVE(1);

		private final int value;

		private status(int value) {
			this.value = value;
		}

		public int value() {
			return this.value;
		}
	}

	public static enum typeId {
		PARENT(0L), CHILDREN(1L);

		private final Long value;

		private typeId(Long value) {
			this.value = value;
		}

		public Long value() {
			return this.value;
		}

	}
}
