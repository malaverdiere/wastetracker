package es.sendit2us.wastetracker.server.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import org.apache.commons.lang.StringUtils;

import es.sendit2us.wastetracker.server.dao.PickupRequestDAO;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.facade.PickupDetail;
import es.sendit2us.wastetracker.server.model.CategoryContainer;
import es.sendit2us.wastetracker.server.model.CategoryProduct;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

@Stateless
@Local(PickupRequestController.class)
public class PickupRequestControllerImpl implements PickupRequestController {
	@EJB private PickupRequestDAO dao;
	
	public List<PickupRequestEntity> getUnasignedRequests() {
		List<PickupRequestEntity> requests = dao.getUnasignedRequests();
		return requests;
	}	
	
	@Override
	public List<PickupRequestEntity> getDeliveredRequests() {
		List<PickupRequestEntity> res = dao.getDeliveredRequests();
		return res;
	}

	public PickupRequestEntity assignRequest(String deviceCode, Integer id) throws WasteTrackerException {
		PickupRequestEntity pickup = dao.findById(id);
		if(StringUtils.equals(deviceCode, pickup.getDeviceCode())) {
			return pickup;
		}
		if(pickup.isAssigned()) {
			throw new WasteTrackerException("Pickup already assigned: " + id);
		}
		
		pickup.setDeviceCode(deviceCode);
		dao.save(pickup);
		return pickup;
	}

	@Override
	public List<PickupRequestEntity> getAssignedToDevice(String device) {
		return dao.findByDeviceId(device);
	}

	@Override
	public void unassignRequest(String device, int i) throws WasteTrackerException {
		PickupRequestEntity pickup = dao.findById(i);

		if(!StringUtils.equals(device, pickup.getDeviceCode())) {
			throw new WasteTrackerException("Pickup " + i + " is not assigned to " + device);			
		}
		
		pickup.setDeviceCode(null);
		dao.save(pickup);		
	}

	@Override
	public List<PickupRequestEntity> setupRequests(String device) {
		List<PickupRequestEntity> unasigned = dao.getUnasignedRequests();
		List<PickupRequestEntity> assignedToYou = dao.findByDeviceId(device);
		unasigned.addAll(assignedToYou);
		return unasigned;
	}	
	
	public void deleteDetailItem(int id) throws WasteTrackerException {
		PickupRequestDetailEntity item = checkClosedItem(id);
		item.setState(PickupDetail.DELETED);
		dao.save(item);
	}
	
	@Override
	public void closeRequest(int code, String customerAuthCode) throws WasteTrackerException {

		PickupRequestEntity req = dao.findById(code);
		if(req.getDeliveryDate() != null) {
			throw new WasteTrackerException("La solicitud ya ha sido cerrada");
		}
		
		if(!StringUtils.equalsIgnoreCase(customerAuthCode, req.getCustomerAuthCode())) {
			throw new WasteTrackerException("El código de cliente no es correcto ");
		}
		
		
		req.setDeliveryDate(new Timestamp(new Date().getTime()));
		dao.save(req);
	}

	@Override
	public void updateDetailItem(int id, String categoryCode, String categoryDesc) throws WasteTrackerException {
		PickupRequestDetailEntity item = checkClosedItem(id);
		item.setState(PickupDetail.UPDATED);
		
		item.setObservations(categoryCode + " " + categoryDesc);
		dao.save(item);
	}

	private PickupRequestDetailEntity checkClosedItem(int id) throws WasteTrackerException {
		PickupRequestDetailEntity item = dao.findDetailItemById(id);
		if(item.getRequest().getDeliveryDate() != null) {
			throw new WasteTrackerException("La solicitud ya ha sido cerrada");			
		}
		return item;
	}

	@Override
	public void addDetailItem(int id, String categoryCode, String categoryDesc) throws WasteTrackerException {
		PickupRequestEntity req = dao.findById(id);
		if(req.getDeliveryDate() != null) {
			throw new WasteTrackerException("La solicitud ya ha sido cerrada");
		}

		PickupRequestDetailEntity item = new PickupRequestDetailEntity();
		item.setState(PickupDetail.ADDED);
		item.setObservations(categoryCode + " " + categoryDesc);
		item.setCategory(new CategoryProduct());
		item.getCategory().setCode("vacío");
		item.getCategory().setDescription("vacío");
		item.setContainer(new CategoryContainer());
		item.getContainer().setCode("vacío");
		item.getContainer().setDescription("vacío");
		item.setRequest(req);
		req.getDetail().add(item);		
	}

	@Override
	public void removeDeliveredRequest(String code) throws WasteTrackerException {
		PickupRequestEntity r;
		try {
			r = dao.findByCode(code);			
		} catch(Exception e) {
			throw new WasteTrackerException("Request doesn't exists " + code);
		}
		if(r.getDeliveryDate() == null) {
			throw new WasteTrackerException("Request is not delivered. " + code);
		}
		dao.remove(r);
	}

	@Override
	public void unassignAll(String deviceCode) {
		dao.unassignAll(deviceCode);
	}
}