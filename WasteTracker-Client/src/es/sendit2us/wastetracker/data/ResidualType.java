package es.sendit2us.wastetracker.data;

import java.util.Vector;

public class ResidualType extends CommonType {
	
	private transient Vector residualCategories = new Vector();
	private transient ResidualCategoryType category;
	
	private long categoryId;
	
	public ResidualType() {
		super();
	}
	
	public long getCategoryId() {
		return categoryId;
	}
	
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	public void setCategory(ResidualCategoryType category) {
		setCategoryId(category.getId());
		this.category = category;
	}
	
//	public void addSpecificType(SpecificResidualType type) {
//		if (!residualCategories.contains(type)) {
//			residualCategories.addElement(type);
//			type.setCategory(this);
//		}
//	}	
}
