package es.sendit2us.wastetracker.server.dao;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import es.sendit2us.wastetracker.server.HibernateProxy;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

@Stateless
@Local(PickupRequestDAO.class)
public class PickupRequestDAOImpl implements PickupRequestDAO {
	@EJB private HibernateProxy jpa;

	public PickupRequestEntity findById(int id) {
		return (PickupRequestEntity)jpa.findBy(PickupRequestEntity.class, (Object)id);
	}

	@Override
	public PickupRequestEntity findByCode(String code) {
	
		Query q = jpa.createQuery("from PickupRequestEntity where code=:code");
		q.setParameter("code", code);
		return (PickupRequestEntity)q.getSingleResult();		
	}
	
	@SuppressWarnings("unchecked")
	public List<PickupRequestEntity> getUnasignedRequests() {
	
		Query q = jpa.createQuery("from PickupRequestEntity p where p.deliveryDate is null and p.deviceCode = '' or p.deviceCode is null");
		return q.getResultList();
	}
	
	@Override
	public List<PickupRequestEntity> getDeliveredRequests() {
		Query q = jpa.createQuery("from PickupRequestEntity p where p.deliveryDate is not null");
		return q.getResultList();		
	}
	
	public List<PickupRequestEntity> findByDeviceId(String device) {
		Query q = jpa.createQuery("from PickupRequestEntity p where p.deliveryDate is null and p.deviceCode = :device");
		q.setParameter("device", device);
		return q.getResultList();		
	}
	
	@Override
	public PickupRequestDetailEntity findDetailItemById(int id) {
	
		return (PickupRequestDetailEntity)jpa.findBy(PickupRequestDetailEntity.class, id);
		
	}
	
	public void save(PickupRequestEntity entity) {
		jpa.persist(entity);
	}

	@Override
	public void save(PickupRequestDetailEntity item) {
		jpa.persist(item);
	}

	@Override
	public void remove(PickupRequestEntity r) {
		jpa.remove(r);
	}

	@Override
	public void unassignAll(String deviceCode) {
		Query q = jpa.createQuery("update PickupRequestEntity p set p.deviceCode = null where p.deviceCode = :code");
		q.setParameter("code", deviceCode);
		q.executeUpdate();
		
	}
}
