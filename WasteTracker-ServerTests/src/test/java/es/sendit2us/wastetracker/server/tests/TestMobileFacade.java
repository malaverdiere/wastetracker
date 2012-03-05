package es.sendit2us.wastetracker.server.tests;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.facade.AssignResponse;
import es.sendit2us.wastetracker.server.facade.PickupDetail;
import es.sendit2us.wastetracker.server.facade.PickupHeader;
import es.sendit2us.wastetracker.server.facade.WasteTrackerMobile;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public class TestMobileFacade extends WasteTrackerTests {
	private WasteTrackerMobile mobileFacade;

	@Override
	protected void init() {
		mobileFacade = (WasteTrackerMobile)lookup("WasteTrackerMobileImplRemote");
	}
	
	public void testAssignRequest() {
		AssignResponse res = mobileFacade.assignRequests("A2", "1");		
		assertEquals(1, res.getAssigned().length);
		assertEquals(1, res.getAssigned()[0].getId());
		assertEquals(3, res.getAssigned()[0].getDetail().size());
		assertNull(res.getMessage());
	}
	
	public void testReasignToMe() {
		AssignResponse res = mobileFacade.assignRequests("A1", "2");
		assertEquals(1, res.getAssigned().length);
		assertEquals(2, res.getAssigned()[0].getId());
		assertNull(res.getMessage(), res.getMessage());
	}
	
	public void testReasign() {
		AssignResponse res = mobileFacade.assignRequests("A3", "2");
		assertEquals(0, res.getAssigned().length);
		assertNotNull(res.getMessage());
	}
	
	public void testRegisterIncidence() throws WasteTrackerException {
		mobileFacade.registerIncidence("IMEI", 1, "texto de la incidencia");		
		IncidenceEntity res = (IncidenceEntity) getJPA().findBy(IncidenceEntity.class, 1);
		assertEquals(1, res.getId());
		assertEquals("IMEI", res.getReporter());
		assertEquals(1, res.getCategory().getId());
		assertNotNull(res.getDate());
	}
	
	public void testCloseSingleRequest() throws WasteTrackerException {
		System.out.println("*********************************************************");
		AssignResponse res = mobileFacade.assignRequests("A2", "1");		
		assertNull(res.getMessage(), res.getMessage());

		String customerAuthCode = "1234";
		mobileFacade.closeRequest(res.getAssigned()[0], customerAuthCode);
		PickupHeader[] assigned = mobileFacade.getAssignedRequests("A2");
		assertEquals(0, assigned.length);		
	}
	
	public void testCloseWithDeletedItem() throws WasteTrackerException {
		AssignResponse res = mobileFacade.assignRequests("A2", "1");		
		String customerAuthCode = "1234";
		assertNull(res.getMessage(), res.getMessage());
		
		PickupDetail item = res.getAssigned()[0].getDetail().get(0);
		item.setState(PickupDetail.DELETED);

		mobileFacade.closeRequest(res.getAssigned()[0], customerAuthCode);
		PickupRequestEntity entity = (PickupRequestEntity) getJPA().findBy(PickupRequestEntity.class, 1);
		for(PickupRequestDetailEntity d: entity.getDetail()) {
			if(d.getId() == item.getId()) {
				assertEquals(PickupDetail.DELETED, (int)d.getState());				
			} else {
				assertEquals(PickupDetail.NORMAL, (int)d.getState());								
			}
		}		
		assertNotNull(entity.getDeliveryDate());
	}

	public void testUpdatedItem() throws WasteTrackerException {
		AssignResponse res = mobileFacade.assignRequests("A2", "1");		
		String customerAuthCode = "1234";
		assertNull(res.getMessage(), res.getMessage());
		PickupDetail item = res.getAssigned()[0].getDetail().get(0);
		item.setState(PickupDetail.UPDATED);
		item.setCategoryCode("code1");
		item.setCategoryDesc("description1");
		
		mobileFacade.closeRequest(res.getAssigned()[0], customerAuthCode);
		PickupRequestEntity entity = (PickupRequestEntity)getJPA().findBy(PickupRequestEntity.class, 1);
		for(PickupRequestDetailEntity d: entity.getDetail()) {
			if(d.getId() == item.getId()) {
				assertEquals(PickupDetail.UPDATED, (int)d.getState());
				assertEquals("code1 description1", d.getObservations());
			} else {
				assertEquals(PickupDetail.NORMAL, (int)d.getState());								
			}
		}		
		assertNotNull(entity.getDeliveryDate());
	}
	
	public void testAddItem() throws WasteTrackerException {
		AssignResponse res = mobileFacade.assignRequests("A2", "1");		
		String customerAuthCode = "1234";
		assertNull(res.getMessage(), res.getMessage());

		PickupDetail item = new PickupDetail();
		item.setCategoryCode("code1");
		item.setCategoryDesc("description1");
		item.setState(PickupDetail.ADDED);
		res.getAssigned()[0].getDetail().add(item);
		
		mobileFacade.closeRequest(res.getAssigned()[0], customerAuthCode);
		PickupRequestEntity entity = (PickupRequestEntity)getJPA().findBy(PickupRequestEntity.class, 1);
		boolean found = false;
		for(PickupRequestDetailEntity d: entity.getDetail()) {
			if(d.getState() == PickupDetail.ADDED) {
				assertEquals(PickupDetail.ADDED, (int)d.getState());
				assertEquals("code1 description1", d.getObservations());
				assertEquals("vacío", d.getCategory().getCode());
				assertEquals("vacío", d.getCategory().getDescription());
				found = true;
			} else {
				assertEquals(PickupDetail.NORMAL, (int)d.getState());								
			}
		}		
		assertTrue("new detail item not added", found);
	}
	
	public void testRegisterIncidenceError() {
		try {
			mobileFacade.registerIncidence("IMEI", -1, "texto de la incidencia");
		} catch (WasteTrackerException e) {
			return;
		}			
		assertTrue("WasteTrackerException not thrown", true);
	}
	
	public void testPing() {
		String res = mobileFacade.ping();
		assertEquals("pong", res);
	}
	
	public void testGetIncidenceCategories() {
		
		CategoryIncidenceEntity[] res = mobileFacade.getIncidenceCategories();
		assertEquals(4, res.length);
	}
}