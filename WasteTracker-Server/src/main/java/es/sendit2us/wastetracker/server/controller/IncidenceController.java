package es.sendit2us.wastetracker.server.controller;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;

public interface IncidenceController {

	public void saveIncidence(String imei, int catId, String obs)
			throws WasteTrackerException;

}