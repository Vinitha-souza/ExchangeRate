package com.exchangerate.main.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Currency {
	
	@Id
	@GeneratedValue
	private int id;
	private String name;
	private double spot;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSpot() {
		return spot;
	}
	public void setSpot(double spot) {
		this.spot = spot;
	}
	
}

