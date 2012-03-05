package es.sendit2us.wastetracker.server.tests;

import java.sql.SQLException;
import java.util.List;

import es.sendit2us.wastetracker.server.controller.PickupRequestController;
import es.sendit2us.wastetracker.server.dao.PickupRequestDAO;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public class TestPickupRequestController extends WasteTrackerTests {
	private PickupRequestController pickupRequest;
	
	@Override
	protected void init() {
		pickupRequest = (PickupRequestController)lookup("PickupRequestControllerImplLocal");
	}
	
	public void testAssign() throws SQLException, WasteTrackerException {

		pickupRequest.assignRequest("A1", 1);
		
		PickupRequestDAO dao = (PickupRequestDAO)lookup("PickupRequestDAOImplLocal");
		PickupRequestEntity p = dao.findById(1);
		assertEquals(1, p.getId());
		assertEquals("A1", p.getDeviceCode());
	}

	public void testReassign() throws SQLException, WasteTrackerException {

		try {
			pickupRequest.assignRequest("A3", 2);			
		} catch(WasteTrackerException e) {
			return;
		}
		assertTrue("WasteTrackerException not thrown", false);
	}

	public void testReassignToMe() throws SQLException, WasteTrackerException {

		PickupRequestEntity res = pickupRequest.assignRequest("A1", 2);
		assertEquals(res.getId(), 2);
		assertEquals(res.getDeviceCode(), "A1");
	}

	public void testUnasign() throws WasteTrackerException {
	
		pickupRequest.unassignRequest("A1", 2);
		PickupRequestDAO dao = (PickupRequestDAO)lookup("PickupRequestDAOImplLocal");
		PickupRequestEntity p = dao.findById(2);
		assertEquals(2, p.getId());
		assertNull(p.getDeviceCode());
	}
	
	public void testUnasignedError() {
		
		try {
			pickupRequest.unassignRequest("A1", 1);
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("WasteTrackerException not thrown", false);
	}
	
	public void testGetUnasigned() {
		
		List<PickupRequestEntity> res = pickupRequest.getUnasignedRequests();
		for(PickupRequestEntity p: res) {
			assertTrue(p.getId() + " is already asigned", !p.isAssigned());
		}
	}

	public void testGetAssignedToDevice() {
		List<PickupRequestEntity> res = pickupRequest.getAssignedToDevice("A1");
		assertEquals(1, res.size());
		assertEquals("A1", res.get(0).getDeviceCode());
	}
	
	public void testSetupPickupRequests() {
		List<PickupRequestEntity> res = pickupRequest.setupRequests("A1");
		assertEquals(2, res.size());
	}	
}