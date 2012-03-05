package es.sendit2us.wastetracker.data;

import java.util.Vector;

public class ResidualCategoryType extends CommonType {
	
	private transient Vector residualTypes = new Vector();
	private transient ResidualFamilyType family;
	
	private long familyId;
	
	public ResidualCategoryType() {
		super();
	}
	
	public long getFamilyId() {
		return familyId;
	}
	
	public void setFamilyId(long familyId) {
		this.familyId = familyId;
	}
	
	public void setFamily(ResidualFamilyType family) {
		setFamilyId(family.getId());
		this.family = family;
	}
	
	public void addType(ResidualType type) {
		if (!residualTypes.contains(type)) {
			residualTypes.addElement(type);
			type.setCategory(this);
		}
	}	
}
