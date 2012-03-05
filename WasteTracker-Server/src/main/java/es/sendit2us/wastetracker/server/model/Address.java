package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Address {
	@Column(nullable=false) private String address;
	@Column(nullable=false) private String city;
	@Column(nullable=false) private String zipCode;
	@Column(nullable=false) private String phone1;
	@Column(nullable=true) private String phone2;
	@Column(nullable=true) private String country;
	@Column(nullable=true) private String ccaa;

	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public void setCcaa(String ccaa) {
		this.ccaa = ccaa;
	}
	public String getCcaa() {
		return ccaa;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountry() {
		return country;
	}
}
