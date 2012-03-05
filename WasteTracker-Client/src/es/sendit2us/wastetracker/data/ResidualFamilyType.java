package es.sendit2us.wastetracker.data;

import java.util.Vector;

public class ResidualFamilyType extends CommonType {
	
	private transient Vector residualCategories = new Vector();
	
	public ResidualFamilyType() {
		super();
	}
	
	public void addCategory(ResidualCategoryType category) {
		if (!residualCategories.contains(category)) {
			residualCategories.addElement(category);
			category.setFamily(this);
		}
	}
}
