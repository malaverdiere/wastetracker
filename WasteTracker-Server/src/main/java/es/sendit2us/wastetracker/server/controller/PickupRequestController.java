package es.sendit2us.wastetracker.server.controller;

import java.util.List;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public interface PickupRequestController {

	public PickupRequestEntity assignRequest(String code, Integer id) throws WasteTrackerException;
	public List<PickupRequestEntity> getUnasignedRequests();
	public List<PickupRequestEntity> getAssignedToDevice(String device);
	public void unassignRequest(String string, int i) throws WasteTrackerException;
	public List<PickupRequestEntity> setupRequests(String device);
	void closeRequest(int code, String customerAuthCode) throws WasteTrackerException;
	public void deleteDetailItem(int id) throws WasteTrackerException;
	public void updateDetailItem(int id, String categoryCode,
			String categoryDesc) throws WasteTrackerException;
	public void addDetailItem(int id, String categoryCode, String categoryDesc) throws WasteTrackerException;
	List<PickupRequestEntity> getDeliveredRequests();
	public void removeDeliveredRequest(String code) throws WasteTrackerException;
	public void unassignAll(String deviceCode);
}