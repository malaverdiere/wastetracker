package es.sendit2us.wastetracker.server.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import es.sendit2us.wastetracker.server.HibernateProxy;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;

@Stateless
@Local(IncidenceController.class)
public class IncidenceControllerImpl implements IncidenceController {
	@EJB private HibernateProxy jpa;
	
	public void saveIncidence(String imei, int catId, String obs) throws WasteTrackerException {
		CategoryIncidenceEntity category = (CategoryIncidenceEntity)jpa.findBy(CategoryIncidenceEntity.class, catId);
		if(category == null) {
			throw new WasteTrackerException("Category " + catId + " does not exists");
		}
		
		IncidenceEntity incidence = new IncidenceEntity();
		incidence.setCategory(category);
		incidence.setDate(new Timestamp(new Date().getTime()));
		incidence.setObs(obs);
		incidence.setReporter(imei);
		
		jpa.persist(incidence);
	}
}
