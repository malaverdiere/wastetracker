package es.sendit2us.wastetracker.data;

import java.io.Serializable;
import java.util.Date;
import java.util.Vector;

public class PickupHeader implements Serializable {
	private static final long serialVersionUID = 7941564184918434405L;
	private long id;
	private String code;
	private String name;
	private String address;
	private String phone;
	private Date date;
	private Vector items;
	
	public PickupHeader() {
		this.items = new Vector();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Vector getItems() {
		return items;
	}
	
	public void setItems(Vector newItems) {
		items.removeAllElements();
		if (newItems != null) {
			for (int i = 0; i < newItems.size(); i++) {
				items.addElement(newItems.elementAt(i));
			}
		}
	}
}
