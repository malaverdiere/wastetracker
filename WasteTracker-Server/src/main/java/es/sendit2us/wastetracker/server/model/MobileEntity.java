package es.sendit2us.wastetracker.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MOBILES")
public class MobileEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue private int id = -1;
	@Column(nullable=false, unique=true) private String imei;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	
}
