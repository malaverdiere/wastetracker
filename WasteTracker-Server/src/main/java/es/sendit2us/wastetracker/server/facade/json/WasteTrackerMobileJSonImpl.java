package es.sendit2us.wastetracker.server.facade.json;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebParam;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.facade.PickupHeader;
import es.sendit2us.wastetracker.server.facade.WasteTrackerMobile;
import es.sendit2us.wastetracker.server.facade.WasteTrackerMobileJSon;
import es.sendit2us.wastetracker.server.lib.JSon;

@Stateless
@Local(WasteTrackerMobileJSon.class)
public class WasteTrackerMobileJSonImpl implements WasteTrackerMobileJSon {
	@EJB private WasteTrackerMobile mobile;
	
	@Override 
	public String getUnassignedRequests() {
		return JSon.serialize(mobile.getUnassignedRequests());
	}
	
	@Override 
	public String getAssignedRequests(String device) {
		return JSon.serialize(mobile.getAssignedRequests(device));		
	}

	@Override 
	public String assignRequests(@WebParam(name="deviceCode") String deviceCode, @WebParam(name="pickups") String pStr) {
		return JSon.serialize(mobile.assignRequests(deviceCode, pStr));
	}

	@Override 
	public void unassignRequests(String deviceCode, Integer p) throws WasteTrackerException {
		mobile.unassignRequests(deviceCode, p);
	}

	@Override 
	public String setupPickupRequests(String device) {
		return JSon.serialize(mobile.setupPickupRequests(device));
	}
	
	@Override 
	public String ping() {
		return mobile.ping();
	}

	@Override 
	public void registerIncidence(String imei, int category, String obs) throws WasteTrackerException {
		mobile.registerIncidence(imei, category, obs);
	}

	@Override 
	public String getIncidenceCategories() {
		return JSon.serialize(mobile.getIncidenceCategories());
	}
	
	@Override 
	public String getWasteDescriptionList() {
		return JSon.serialize(mobile.getWasteDescriptionList());
	}

	@Override 
	public void closeRequest(String pickupHeader) throws WasteTrackerException {
		PickupHeader ph = (PickupHeader)JSon.unserialize(pickupHeader, PickupHeader.class);
		mobile.closeRequest(ph, ph.getCustomerAuthCode());
	}

	@Override
	public void addIncidence(String imei, int incidenceCode, String description) throws WasteTrackerException {
		mobile.registerIncidence(imei, incidenceCode, description);
	}
}
