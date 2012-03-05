package es.sendit2us.wastetracker.server.facade;

import java.io.Serializable;
import java.util.List;

public class PickupHeader implements Serializable {
	private int id = -1;
	private String code;
	private String name;
	private String address;
	private String customerAuthCode;	
	private long pickupDate;
	private long deliveryDate;
	private String deviceCode;
	private String msgError;
	private List<PickupDetail> detail;
	
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCustomerAuthCode() {
		return customerAuthCode;
	}
	public void setCustomerAuthCode(String customerCode) {
		this.customerAuthCode = customerCode;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(long pickupDate) {
		this.pickupDate = pickupDate;
	}
	public long getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(long deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	public String getDeviceCode() {
		return deviceCode;
	}
	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}
	public void setMsgError(String msgError) {
		this.msgError = msgError;
	}
	public String getMsgError() {
		return msgError;
	}
	public List<PickupDetail> getDetail() {
		return detail;
	}
	public void setDetail(List<PickupDetail> detail) {
		this.detail = detail;
	}
}
