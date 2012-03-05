package es.sendit2us.wastetracker.server.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name="CAT_INCIDENCES")
public class CategoryIncidenceEntity implements Serializable {
	private static final long serialVersionUID = 8110238725999813439L;
	@Id @GeneratedValue private int id = -1;
	@Column(nullable=false, unique=true) private String code;
	@Column(nullable=false) private String description;
	
	public CategoryIncidenceEntity(int i) {
		this.id = i;		
	}
	
	public CategoryIncidenceEntity() {}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCode() {
		return StringUtils.trimToNull(code);
	}
	public void setCode(String code) {		
		this.code = code;
	}
	public String getDescription() {
		return StringUtils.trimToNull(description);
	}
	public void setDescription(String description) {
		this.description = description;
	}
}