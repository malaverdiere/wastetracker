package es.sendit2us.wastetracker.server.facade;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

public interface WasteTrackerMobile {

	public PickupHeader[] getUnassignedRequests();
	public PickupHeader[] getAssignedRequests(String device);
	public PickupHeader[] setupPickupRequests(String device);
	public AssignResponse assignRequests(String deviceCode, String pStr);
	public void unassignRequests(String deviceCode, Integer pickups) throws WasteTrackerException;
	public void registerIncidence(String imei, int category, String obs) throws WasteTrackerException;
	public String ping();
	public CategoryIncidenceEntity[] getIncidenceCategories();
	WasteDescriptionEntity[] getWasteDescriptionList();
	public void closeRequest(PickupHeader pickupHeader, String customerAuthCode) throws WasteTrackerException;
}