package es.sendit2us.wastetracker.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="WASTE_DESCRIPTIONS")
public class WasteDescriptionEntity implements Serializable {
	private static final long serialVersionUID = -842595576280681384L;
	@Id @GeneratedValue private int id = -1;
	@Column(nullable=false, unique=true) private String code;
	@Column(nullable=false) private String description;
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}