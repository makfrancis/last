package com.accenture.bars.domain;

import java.sql.Timestamp;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.ModelAndView;

public class Customer {
	private int customerId;
	private String firstName;
	private String lastName;
	private String address;
	private String status;
	private Timestamp dateCreated;
	private String lastEdited;
	
	public Customer(int customerId, String firstName, String lastName, String address, String status,
			String lastEdited) {
		super();
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.status = status;
		this.lastEdited = lastEdited;
	}
	
	public Customer(String jsonObject) throws JSONException {
		JSONObject customerJson = new JSONObject(jsonObject);
		this.customerId = (int) customerJson.getInt("customerId");
		this.firstName = (String) customerJson.getString("firstName");
		this.lastName = (String) customerJson.getString("lastName");
		this.address = (String) customerJson.getString("address");
		this.status = (String) customerJson.getString("status");
		this.lastEdited = (String) customerJson.getString("lastEdited");
	}
 
	public void addModelAndView(ModelAndView mv) {
		mv.addObject("customerId", this.customerId);
		mv.addObject("firstName", this.firstName);
		mv.addObject("lastName", this.lastName);
		mv.addObject("address", this.address);
		mv.addObject("status", this.status);
		mv.addObject("lastEdited", this.lastEdited);
	}
 
	public String toJson() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("customerId", this.customerId);
		jsonObject.put("firstName", this.firstName);
		jsonObject.put("lastName", this.lastName);
		jsonObject.put("address", this.address);
		jsonObject.put("status", this.status);
		jsonObject.put("lastEdited", this.lastEdited);
		return jsonObject.toString();
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastEdited() {
		return lastEdited;
	}

	public void setLastEdited(String lastEdited) {
		this.lastEdited = lastEdited;
	}
	
	
	
	
	
	
}
