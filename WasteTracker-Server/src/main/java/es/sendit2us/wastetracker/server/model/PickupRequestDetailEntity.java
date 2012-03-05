package es.sendit2us.wastetracker.server.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="PICKUPREQUEST_DET")
public class PickupRequestDetailEntity {
	@Id @GeneratedValue private int id = -1;
	@Embedded private CategoryProduct category;
	@Embedded private CategoryContainer container;
	@ManyToOne private PickupRequestEntity request;
	@Column(nullable = false) private Integer state = 0;
	private String observations;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public CategoryContainer getContainer() {
		return container;
	}
	public void setContainer(CategoryContainer container) {
		this.container = container;
	}	
	public PickupRequestEntity getRequest() {
		return request;
	}
	public void setRequest(PickupRequestEntity request) {
		this.request = request;
	}
	public CategoryProduct getCategory() {
		return category;
	}
	public void setCategory(CategoryProduct category) {
		this.category = category;
	}
	
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getObservations() {
		return observations;
	}
	public void setObservations(String observations) {
		this.observations = observations;
	}
}
