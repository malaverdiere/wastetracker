package es.sendit2us.wastetracker.server.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="INCIDENCES")
public class IncidenceEntity {
	@Id @GeneratedValue private int id = -1;
	@ManyToOne @JoinColumn(name="category")
	private CategoryIncidenceEntity category;
	@Column(nullable=false) private Timestamp date;	
	@Column(nullable=false) private String obs;
	@Column(nullable=false) private String reporter;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CategoryIncidenceEntity getCategory() {
		return category;
	}
	public void setCategory(CategoryIncidenceEntity category) {
		this.category = category;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	public String getReporter() {
		return reporter;
	}
	public void setReporter(String reporter) {
		this.reporter = reporter;
	}
}
