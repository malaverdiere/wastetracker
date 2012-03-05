package es.sendit2us.wastetracker.server.facade;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

public interface DataManager {

	int addRequest(String code, String customerCode, String name, String company, String address, String zipCode, String city, String ccaa, String country, String phone1, String phone2, String customerAuthCode, PickupDetail[] detail) throws WasteTrackerException;
	public CategoryIncidenceEntity[] getIncidenceCategories();
	void addIncidenceCategory(String code, String description);
	public void addWasteDescription(String code, String description);
	public WasteDescriptionEntity[] getWasteDescriptionList();
	void addImei(String string) throws WasteTrackerException;
	Boolean imeiExists(String imei);
	void removeImei(String imei) throws WasteTrackerException;
	void removeIncidenceCategory(String code) throws WasteTrackerException;
	void removeWasteDescription(String code) throws WasteTrackerException;
	PickupHeader[] getDeliveredRequests();
	void removeDeliveredRequest(String string) throws WasteTrackerException;
	public void removeIncidence(int id);
	public IncidenceEntity[] getIncidences();
}
