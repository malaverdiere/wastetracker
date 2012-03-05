package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
public class CategoryProduct {
	@Column(nullable=false) private String code;
	@Column(nullable=false) private String description;
	@Embedded private FamilyProduct family;
	@Embedded private Usage usage;
	
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
	public FamilyProduct getFamily() {
		return family;
	}
	public void setFamily(FamilyProduct family) {
		this.family = family;
	}
	
	public Usage getUsage() {
		return usage;
	}
	public void setUsage(Usage usage) {
		this.usage = usage;
	}
}