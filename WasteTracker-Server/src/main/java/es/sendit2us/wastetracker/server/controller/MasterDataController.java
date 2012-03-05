package es.sendit2us.wastetracker.server.controller;

import java.util.List;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

public interface MasterDataController {

	public void addPickupRequest(PickupRequestEntity request) throws WasteTrackerException;

	public List<CategoryIncidenceEntity> getIndicenceCategories();
	public CategoryIncidenceEntity saveCategoryIncidence(CategoryIncidenceEntity catIncidence);
	public CategoryIncidenceEntity getIncidenceCategory(String code);
	public List<WasteDescriptionEntity> getWasteDescriptionList();
	public WasteDescriptionEntity saveWasteDescription(WasteDescriptionEntity wasteDescription);

	void addImei(String imei) throws WasteTrackerException;

	boolean imeiExists(String imei);

	void removeImei(String imei) throws WasteTrackerException;

	void removeIncidenceCategory(String code) throws WasteTrackerException;

	void removeWasteDescription(String code) throws WasteTrackerException;

	public List<IncidenceEntity> getIncidences();

	public void removeIncidence(int id);
}