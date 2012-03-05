package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Usage {
	@Column(nullable=true) private String code;
	@Column(nullable=true) private String description;
	
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