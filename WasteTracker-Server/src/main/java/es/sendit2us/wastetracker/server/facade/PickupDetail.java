package es.sendit2us.wastetracker.server.facade;

import java.io.Serializable;

public class PickupDetail implements Serializable {
	private int id = -1;
	private String containerCode;
	private String containerDescr;
	private String categoryCode;
	private String categoryDesc;
	private String familyCode;
	private String familyDesc;
	private String usageCode;
	private String usageDesc;
	private int state = NORMAL;
	
	public static final int NORMAL = 0;
	public static final int DELETED = 1;
	public static final int ADDED = 2;
	public static final int UPDATED = 3;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getContainerCode() {
		return containerCode;
	}
	public void setContainerCode(String containerCode) {
		this.containerCode = containerCode;
	}
	public String getContainerDescr() {
		return containerDescr;
	}
	public void setContainerDescr(String containerDescr) {
		this.containerDescr = containerDescr;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public String getFamilyCode() {
		return familyCode;
	}
	public void setFamilyCode(String familyCode) {
		this.familyCode = familyCode;
	}
	public String getFamilyDesc() {
		return familyDesc;
	}
	public void setFamilyDesc(String familyDesc) {
		this.familyDesc = familyDesc;
	}
	public String getUsageCode() {
		return usageCode;
	}
	public void setUsageCode(String usageCode) {
		this.usageCode = usageCode;
	}
	public String getUsageDesc() {
		return usageDesc;
	}
	public void setUsageDesc(String usageDesc) {
		this.usageDesc = usageDesc;
	}	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
}
