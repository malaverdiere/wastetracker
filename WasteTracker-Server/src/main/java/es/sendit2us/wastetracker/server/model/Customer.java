package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

@Embeddable
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@MappedSuperclass 
public class Customer {
	@Column(nullable=false) private String customerCode;
	@Column(nullable=false) private String name;
	@Column(nullable=true) private String cnum;
	@Column(nullable=false) private String company;
	@Embedded private Address address;
	
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String code) {
		this.customerCode = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCnum() {
		return cnum;
	}
	public void setCnum(String cnum) {
		this.cnum = cnum;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
