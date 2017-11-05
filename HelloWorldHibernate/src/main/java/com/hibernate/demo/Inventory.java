package com.hibernate.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "inventory") // maps fields to mysql table columns
public class Inventory {
	private long id;
	private String name;
	private int quantity;
	
	public Inventory() {
	}
	
	public Inventory(long id, String name, int quantity) {
		super();
		this.id = id;
		this.name = name;
		this.quantity = quantity;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // specifies that id is a primary key
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

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString() {		
		String id = "Id: " + this.getId();
		String name = "Name: " + this.getName();
		String quantity = "Quantity: " + this.getQuantity();
		return id + "\n" + name + "\n" + quantity;
	}
}
