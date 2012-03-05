package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CategoryContainer {
	@Column(nullable=false) private String code;
	@Column(nullable=false) private String description;
	
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
