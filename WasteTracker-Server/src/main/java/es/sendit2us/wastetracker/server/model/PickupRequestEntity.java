package es.sendit2us.wastetracker.server.model;

import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name="PICKUPREQUESTS")
public class PickupRequestEntity {
	@Id @GeneratedValue private int id = -1;
	@Column(nullable=false, unique=true) private String code;
	@Column(nullable=false) private Timestamp pickupDate;
	@Column(nullable=true) private Timestamp deliveryDate;
	@Column(nullable=false) private String customerAuthCode;
	@Embedded private Customer customer;
	@Column(nullable=true) private String deviceCode;
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="request")	
	private Set<PickupRequestDetailEntity> detail;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Timestamp getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Timestamp pickupDate) {
		this.pickupDate = pickupDate;
	}
	public Timestamp getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(Timestamp deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getCustomerAuthCode() {
		return customerAuthCode;
	}
	public void setCustomerAuthCode(String customerAuthCode) {
		this.customerAuthCode = customerAuthCode;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public Set<PickupRequestDetailEntity> getDetail() {
		return detail;
	}
	public void setDetail(Set<PickupRequestDetailEntity> detail) {
		this.detail = detail;
	}	
	public boolean isAssigned() {
		return StringUtils.isNotBlank(getDeviceCode());
	}
}
