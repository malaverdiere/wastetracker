package es.sendit2us.wastetracker.server.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.Query;

import es.sendit2us.wastetracker.server.HibernateProxy;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.MobileEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

@Stateless
@Local(MasterDataController.class)
public class MasterDataControllerImpl implements MasterDataController {
	@EJB private HibernateProxy jpa;
	
	public void addPickupRequest(PickupRequestEntity request) throws WasteTrackerException {
		
		if(!checkRequestForInsert(request)) {
			throw new WasteTrackerException("Ha ocurrido un error al insertar la solicitud. Revise que el detalle no está vacío y que no está asignada a un transportista");
		}
		request.setPickupDate(new Timestamp(new Date().getTime()));
		jpa.persist(request);
	}

	private boolean checkRequestForInsert(PickupRequestEntity request) {
		return request.getDetail() != null && request.getDetail().size() > 0 &&
				!request.isAssigned() 
		;
	}

	@Override
	public List<CategoryIncidenceEntity> getIndicenceCategories() {
		Query res = jpa.createQuery("from CategoryIncidenceEntity");
		
		return res.getResultList();
	}

	public CategoryIncidenceEntity getIncidenceCategory(String code) {
		Query res = jpa.createQuery("from CategoryIncidenceEntity where code=:code");
		res.setParameter("code", code);
		List l = res.getResultList();
		if(l.size() > 0) {
			return (CategoryIncidenceEntity)l.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void removeIncidenceCategory(String code) throws WasteTrackerException {
	
		CategoryIncidenceEntity entity = getIncidenceCategory(code);
		if(entity == null) {
			throw new WasteTrackerException("Incidence cateogry doesnt exists " + code);
		}
		jpa.remove(entity);
	}
	
	public CategoryIncidenceEntity saveCategoryIncidence(CategoryIncidenceEntity catIncidence) {
		
		CategoryIncidenceEntity i = getIncidenceCategory(catIncidence.getCode());
		if(i != null) {
			i.setDescription(catIncidence.getDescription());
			jpa.persist(i);
			return i;
		}
		
		catIncidence = (CategoryIncidenceEntity)jpa.merge(catIncidence);			
		
		return catIncidence;
	}

	public List<WasteDescriptionEntity> getWasteDescriptionList() {		
		Query res = jpa.createQuery("from WasteDescriptionEntity");
		
		return res.getResultList();
	}
	
	public WasteDescriptionEntity getWasteDescription(String code) {
		Query res = jpa.createQuery("from WasteDescriptionEntity where code=:code");
		res.setParameter("code", code);
		List l = res.getResultList();
		if(l.size() > 0) {
			return (WasteDescriptionEntity)l.get(0);
		} else {
			return null;
		}		
	}
	
	public WasteDescriptionEntity saveWasteDescription(WasteDescriptionEntity wasteDescription) {	
		WasteDescriptionEntity i = getWasteDescription(wasteDescription.getCode());
		if(i != null) {
			i.setDescription(wasteDescription.getDescription());
			jpa.persist(i);
			return i;
		}
		
		wasteDescription = (WasteDescriptionEntity)jpa.merge(wasteDescription);			
		
		return wasteDescription;
	}
	
	@Override
	public void removeWasteDescription(String code) throws WasteTrackerException {
		WasteDescriptionEntity entity = getWasteDescription(code);
		if(entity == null) {
			throw new WasteTrackerException("WasteDescription not found " + code);
		}
		
		jpa.remove(entity);
	}
	
	@Override
	public boolean imeiExists(String imei) {

		return loadImei(imei) != null;
	}

	private MobileEntity loadImei(String imei) {
		Query q = jpa.createQuery("from MobileEntity where imei=:imei");
		q.setParameter("imei", imei);
		List l = q.getResultList();
		if(l.size() > 0) {
			return (MobileEntity)l.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public void addImei(String imei) throws WasteTrackerException {

		if(imeiExists(imei)) {
			throw new WasteTrackerException("Imei already exists " + imei); 
		}
		MobileEntity mobile = new MobileEntity();
		mobile.setImei(imei);
		
		jpa.merge(mobile);
	}

	@Override
	public void removeImei(String imei) throws WasteTrackerException {

		MobileEntity imeiEntity = loadImei(imei);
		if(imeiEntity == null) {
			throw new WasteTrackerException("Imei doesnt exists " + imei); 			
		}
		
		jpa.remove(imeiEntity);		
	}

	@Override
	public List<IncidenceEntity> getIncidences() {
		Query q = jpa.createQuery("from IncidenceEntity");
		
		return q.getResultList();
	}

	@Override
	public void removeIncidence(int id) {
		IncidenceEntity inc = (IncidenceEntity)jpa.findBy(IncidenceEntity.class, id);
		jpa.remove(inc);
		
	}
}