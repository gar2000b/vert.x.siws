package com.onlineinteract.model;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity("SINRequestResponse")
public class SINRequestResponse {

    @Id
    private ObjectId id;
	
    // Request Data
	private String requestNo;
	private String customerId;
	
	// Customer Data
	@Embedded
	private Customer customer;
	
	public SINRequestResponse(String requestNo, String customerId) {
		this.requestNo = requestNo;
		this.customerId = customerId;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
}
