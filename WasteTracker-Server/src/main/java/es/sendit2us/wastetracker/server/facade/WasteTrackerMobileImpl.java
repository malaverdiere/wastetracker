package es.sendit2us.wastetracker.server.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebParam;

import es.sendit2us.wastetracker.server.controller.IncidenceController;
import es.sendit2us.wastetracker.server.controller.MasterDataController;
import es.sendit2us.wastetracker.server.controller.PickupRequestController;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.lib.StringUtils;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

@Stateless
@Remote(WasteTrackerMobile.class)
public class WasteTrackerMobileImpl implements WasteTrackerMobile {
	@EJB private PickupRequestController requestController;
	@EJB private MasterDataController dataController;
	@EJB private IncidenceController incidenceController;
	@EJB private MasterDataController masterDataController;
		
	public PickupHeader[] getUnassignedRequests() {
		List<PickupRequestEntity> requests = requestController.getUnasignedRequests();
		PickupHeader[] res = VOFactory.pickupEntityToHeader(requests);
		return res;
	}
	
	public PickupHeader[] getAssignedRequests(String device) {
		List<PickupRequestEntity> requests = requestController.getAssignedToDevice(device);
		PickupHeader[] res = VOFactory.pickupEntityToHeader(requests);
		return res;		
	}

	public AssignResponse assignRequests(@WebParam(name="deviceCode") String deviceCode, @WebParam(name="pickups") String pStr) {
		requestController.unassignAll(deviceCode);
		
		AssignResponse response = new AssignResponse();
		Integer[] pickups = StringUtils.stringToArray(pStr);
		List<PickupHeader> res = new ArrayList<PickupHeader>();
		for(int i = 0; i < pickups.length; i++) {
			Integer id = pickups[i];
			try {
				PickupRequestEntity pickup = requestController.assignRequest(deviceCode, id);
				PickupHeader vo = VOFactory.newPickupHeader(pickup);
				List<PickupDetail> det = VOFactory.retrieveDetail(pickup);
				vo.setDetail(det);
				res.add(vo);
			} catch (WasteTrackerException e) {
				if(response.getMessage() == null) {
					response.addMessage("Las siguientes solicitudes no han podido ser asignadas");				
				}
				response.addMessage(e.getMessage());
			}			
		}
		
		response.setAssigned(res.toArray(new PickupHeader[] {}));
		return response;
	}

	public void unassignRequests(String deviceCode, Integer p) throws WasteTrackerException {
		Integer[] pickups = new Integer[] {p};
		for(int i: pickups) {
			requestController.unassignRequest(deviceCode, i);			
		}
	}

	public PickupHeader[] setupPickupRequests(String device) {
	
		List<PickupRequestEntity> requests = requestController.setupRequests(device);
		PickupHeader[] res = VOFactory.pickupEntityToHeader(requests);
		return res;

	}
	
	public String ping() {
		return "pong&lec";
	}

	public void registerIncidence(String imei, int category, String obs) throws WasteTrackerException {
		incidenceController.saveIncidence(imei, category, obs);
	}

	public CategoryIncidenceEntity[] getIncidenceCategories() {
		List<CategoryIncidenceEntity> res = dataController.getIndicenceCategories();
		return res.toArray(new CategoryIncidenceEntity[] {});
	}
	
	public WasteDescriptionEntity[] getWasteDescriptionList() {

		List<WasteDescriptionEntity> res = masterDataController.getWasteDescriptionList();
		return res.toArray(new WasteDescriptionEntity[] {});
	}

	@Override
	public void closeRequest(PickupHeader pickupHeader, String customerAuthCode) throws WasteTrackerException {
		
		for(PickupDetail d: pickupHeader.getDetail()) {
			switch(d.getState()) {
				case PickupDetail.NORMAL:
					break;
				case PickupDetail.DELETED:
					requestController.deleteDetailItem(d.getId());
					break;
				case PickupDetail.UPDATED:
					requestController.updateDetailItem(d.getId(), d.getCategoryCode(), d.getCategoryDesc());
					break;
				case PickupDetail.ADDED:
					requestController.addDetailItem(pickupHeader.getId(), d.getCategoryCode(), d.getCategoryDesc());
					break;
			}
		}
		requestController.closeRequest(pickupHeader.getId(), customerAuthCode);
	}
}
