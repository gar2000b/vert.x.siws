package com.onlineinteract.model;

import org.bson.types.ObjectId;

//@Entity
//@XmlRootElement
public class Customer {

//    @Id
    private ObjectId id;
	
//    @XmlElement
	private String forname;
//    @XmlElement
	private String surname;
//    @XmlElement
	private String sin;
//    @XmlElement
	private String customerId;
	
	private String csuuid;
	
	// Note: important - needs empty constructor.
	public Customer(){}
	
	public Customer(String customerId, String forname, String surname, String sin, String csuuid) {
		this.customerId = customerId;
		this.forname = forname;
		this.surname = surname;
		this.sin = sin;
		this.csuuid = csuuid;
	}
	public String getForname() {
		return forname;
	}
	public void setForname(String forname) {
		this.forname = forname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getSin() {
		return sin;
	}
	public void setSin(String sin) {
		this.sin = sin;
	}
	
	@Override
	public String toString() {
		return "blah: blah";
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCsuuid() {
		return csuuid;
	}

	public void setCsuuid(String csuuid) {
		this.csuuid = csuuid;
	}
}
