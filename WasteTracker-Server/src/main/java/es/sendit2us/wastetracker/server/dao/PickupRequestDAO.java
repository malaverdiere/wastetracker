package es.sendit2us.wastetracker.server.dao;

import java.util.List;

import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public interface PickupRequestDAO {

	public PickupRequestEntity findById(int id);
	public void save(PickupRequestEntity entity);	
	public List<PickupRequestEntity> getUnasignedRequests();
	public List<PickupRequestEntity> findByDeviceId(String device);
	PickupRequestDetailEntity findDetailItemById(int id);
	public void save(PickupRequestDetailEntity item);
	List<PickupRequestEntity> getDeliveredRequests();
	PickupRequestEntity findByCode(String code);
	public void remove(PickupRequestEntity r);
	public void unassignAll(String deviceCode);
}