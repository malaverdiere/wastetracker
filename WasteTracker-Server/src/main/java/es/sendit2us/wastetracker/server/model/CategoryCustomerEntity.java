package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Deprecated
@Entity
@Table(name="CAT_CUSTOMERS")
public class CategoryCustomerEntity {
	@Id @GeneratedValue private int id = -1;
	@Column(nullable=false) private String code;
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
