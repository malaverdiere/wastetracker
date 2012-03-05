package es.sendit2us.wastetracker.server.tests;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import comes.sendit2us.json.WasteTrackerMobileJSONStub;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.facade.AssignResponse;
import es.sendit2us.wastetracker.server.facade.PickupDetail;
import es.sendit2us.wastetracker.server.facade.PickupHeader;
import es.sendit2us.wastetracker.server.facade.WasteTrackerMobileJSon;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

public class TestMobileFacadeJSon extends WasteTrackerTests {
	private WasteTrackerMobileJSon mobileFacade;

	@Override
	protected void init() {
		mobileFacade = (WasteTrackerMobileJSon)lookup("WasteTrackerMobileJSonImplLocal");
	}

	public void testGetWasteDescriptionList() throws JSONException {
		String s = mobileFacade.getWasteDescriptionList();
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();
		
		JSONArray json = new JSONArray(s);
		WasteDescriptionEntity[] res = stub.decodeWasteDescriptionList(json);
		assertEquals("code1", res[0].getCode());
		assertEquals("description 1", res[0].getDescription());
		assertEquals("code2", res[1].getCode());
		assertEquals("description 2", res[1].getDescription());
		assertEquals("code3", res[2].getCode());
		assertEquals("description 3", res[2].getDescription());
		assertEquals("code4", res[3].getCode());
		assertEquals("description 4", res[3].getDescription());
	}
	
	public void testIncidenceCategories() throws JSONException {
		String s = mobileFacade.getIncidenceCategories();
		JSONArray json = new JSONArray(s);
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();
	
		CategoryIncidenceEntity[] res = stub.decodeCategoryIncidenceList(json);
		assertEquals("1", res[0].getCode());
//		assertEquals("Falta mercancía", res[0].getDescription());
		assertEquals("2", res[1].getCode());
	//	assertEquals("Sobra mercancía", res[1].getDescription());
		assertEquals("3", res[2].getCode());
//		assertEquals("No es la mercancía indicada", res[2].getDescription());
		assertEquals("4", res[3].getCode());
		//assertEquals("Otros", res[3].getDescription());
	}
	
	public void testGetUnassignedRequests() throws JSONException {
		String s = mobileFacade.getUnassignedRequests();
		JSONArray json = new JSONArray(s);
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();

		PickupHeader[] res = stub.decodePickupHeaderList(json);
		for(PickupHeader p: res) {
			System.out.println(p.getCode() + " " + p.getCustomerAuthCode());
		}
	}
	
	public void testAssignRequest() throws JSONException {
		String s = mobileFacade.assignRequests("A2", "1");
		System.out.println(s);
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();

		AssignResponse res = stub.decodeAssignResponse(new JSONObject(s));
		for(PickupDetail d: res.getAssigned()[0].getDetail()) {
			System.out.println(d.getContainerCode() + " " + d.getContainerDescr() + " " + d.getCategoryDesc() + " " + d.getCategoryCode());			
		}
	}

	public void testCloseSingleRequest() throws WasteTrackerException, JSONException {
		System.out.println("*********************************************************");
		String s = mobileFacade.assignRequests("A2", "1");		
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();
		AssignResponse res = stub.decodeAssignResponse(new JSONObject(s));
		assertNull(res.getMessage(), res.getMessage());

		String customerAuthCode = "1234";
		res.getAssigned()[0].setCustomerAuthCode(customerAuthCode);
		JSONObject pickJson = stub.encodePickupHeader(res.getAssigned()[0]);
		String jsonS = pickJson.toString();
		
		mobileFacade.closeRequest(jsonS);
		s = mobileFacade.getAssignedRequests("A2");
		PickupHeader[] assigned = stub.decodePickupHeaderList(new JSONArray(s));
		assertEquals(0, assigned.length);		
	}

	public void testAddItem() throws WasteTrackerException, JSONException {
		WasteTrackerMobileJSONStub stub = new WasteTrackerMobileJSONStub();
		AssignResponse res = stub.decodeAssignResponse(new JSONObject(mobileFacade.assignRequests("A2", "1")));
		String customerAuthCode = "1234";
		assertNull(res.getMessage(), res.getMessage());

		PickupDetail item = new PickupDetail();
		item.setCategoryCode("code1");
		item.setCategoryDesc("description1");
		item.setState(PickupDetail.ADDED);
		res.getAssigned()[0].getDetail().add(item);
		res.getAssigned()[0].setCustomerAuthCode(customerAuthCode);
		
		mobileFacade.closeRequest(stub.encodePickupHeader(res.getAssigned()[0]).toString());
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
	
	public void testAddIncidence() throws WasteTrackerException {
		
		mobileFacade.addIncidence("IMEI", 1, "incidence");
	}
}
