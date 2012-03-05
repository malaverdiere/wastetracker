package es.sendit2us.wastetracker.server.tests;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.facade.DataManager;
import es.sendit2us.wastetracker.server.facade.PickupDetail;
import es.sendit2us.wastetracker.server.facade.PickupHeader;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

public class TestDataManagerImpl extends WasteTrackerTests {
	private DataManager dataManager;

	@Override
	protected void init() {
		dataManager = (DataManager)lookup("DataManagerImplRemote");
	}
	
	public void testAddRequest() throws WasteTrackerException {
		PickupDetail d = new PickupDetail();
		d.setCategoryCode("catcode");
		d.setCategoryDesc("catdescr");
		d.setContainerCode("contcode");
		d.setContainerDescr("contdescr");
		d.setFamilyCode("famcode");
		d.setFamilyDesc("famdescr");
		d.setUsageCode("usagecode");
		d.setUsageDesc("usagedescr");
		PickupDetail[] detail = new PickupDetail[] {d};
		int code = dataManager.addRequest("code", "customerCode", "name", "company", "address", "zipCode", "city", "ccaa", "country", "phone1", "phone2", "customerAuthCode", detail);
		assertTrue(code > 0);
		
		PickupRequestEntity res = (PickupRequestEntity)getJPA().findBy(PickupRequestEntity.class, code);
		assertEquals("code", res.getCode());
		assertEquals("customerCode", res.getCustomer().getCustomerCode());
		assertEquals("name", res.getCustomer().getName());
		assertEquals("company", res.getCustomer().getCompany());
		assertEquals("address", res.getCustomer().getAddress().getAddress());
		assertEquals("zipCode", res.getCustomer().getAddress().getZipCode());
		assertEquals("city", res.getCustomer().getAddress().getCity());
		assertEquals("ccaa", res.getCustomer().getAddress().getCcaa());
		assertEquals("country", res.getCustomer().getAddress().getCountry());
		assertEquals("phone1", res.getCustomer().getAddress().getPhone1());
		assertEquals("phone2", res.getCustomer().getAddress().getPhone2());
		assertEquals("customerAuthCode", res.getCustomerAuthCode());
		assertEquals(1, res.getDetail().size());
		PickupRequestDetailEntity det = res.getDetail().iterator().next();
		assertEquals("catcode", det.getCategory().getCode());
		assertEquals("catdescr", det.getCategory().getDescription());
		assertEquals("contcode", det.getContainer().getCode());
		assertEquals("contdescr", det.getContainer().getDescription());
		assertEquals("famcode", det.getCategory().getFamily().getCode());
		assertEquals("famdescr", det.getCategory().getFamily().getDescription());
		assertEquals("usagecode", det.getCategory().getUsage().getCode());
		assertEquals("usagedescr", det.getCategory().getUsage().getDescription());
	}
	
	public void testAddCategoryIncidence() {
		CategoryIncidenceEntity[] cat = dataManager.getIncidenceCategories();		
		int size = cat.length;
		
		dataManager.addIncidenceCategory("code1", "description1");

		cat = dataManager.getIncidenceCategories();
		
		CategoryIncidenceEntity res = cat[size];
		
		assertEquals("code1", res.getCode());
		assertEquals("description1", res.getDescription());
	}
	
	public void testUpdateCategoryIncidence() {
		dataManager.addIncidenceCategory("4", "update");
		
		CategoryIncidenceEntity[] cat = dataManager.getIncidenceCategories();
		CategoryIncidenceEntity res = cat[3];
		
		assertEquals("4", res.getCode());
		assertEquals("update", res.getDescription());
	}
	
	public void testAddCategoryContainer() {
		int size = dataManager.getWasteDescriptionList().length;
		
		dataManager.addWasteDescription("code5", "description5");
		
		WasteDescriptionEntity[] w = dataManager.getWasteDescriptionList();
		
		WasteDescriptionEntity res = w[size];
		assertEquals("code5", res.getCode());
		assertEquals("description5", res.getDescription());
	}
	
	public void testUpdateCategoryContainer() {
		int size = dataManager.getWasteDescriptionList().length;

		dataManager.addWasteDescription("code1", "description2");

		WasteDescriptionEntity[] w = dataManager.getWasteDescriptionList();
		WasteDescriptionEntity updated = null;
		for(WasteDescriptionEntity wd: w) {
			if(StringUtils.equals(wd.getCode(), "code1")) {
				updated = wd;
			}
		}
		assertEquals("description2", updated.getDescription());
	}
	
	public void testAddImei() throws WasteTrackerException {
		
		dataManager.addImei("IMEI1");
		
		assertTrue(dataManager.imeiExists("IMEI1"));
	}
	
	@Test(expected=WasteTrackerException.class)
	public void testAddDuplicateImei() throws Exception {
		
		dataManager.addImei("IMEI1");
		try {
			dataManager.addImei("IMEI1");					
		} catch(WasteTrackerException e) {
			return;
		}
		assertTrue("Expected exception", false);
	}
	
	public void testRemoveImei() throws WasteTrackerException {

		dataManager.addImei("IMEI1");
		assertTrue(dataManager.imeiExists("IMEI1"));
		
		dataManager.removeImei("IMEI1");
		
		assertFalse(dataManager.imeiExists("IMEI1"));
	}
	
	@Test(expected=WasteTrackerException.class)
	public void testRemoveUnexistent() {
		
		try {
			dataManager.removeImei("IMEI1");
		} catch (WasteTrackerException e) {
			return;
		}		
		assertTrue("Expected exception", false);
	}
	
	public void testRemoveIncidenceCategory() throws WasteTrackerException {
		dataManager.addIncidenceCategory("CODE1", "DESCR1");
		
		dataManager.removeIncidenceCategory("CODE1");
	}
	
	public void testRemoveUnexistentIncidenceCategory() {

		try {
			dataManager.removeIncidenceCategory("CODE1");
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("Expected exception", false);		
	}
	
	public void testRemoveWasteDescription() throws WasteTrackerException {
		
		dataManager.removeWasteDescription("CODE1");
	}
	
	public void testRemoveUnexistentWasteDescription() {
		try {
			dataManager.removeWasteDescription("CODEx");
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("Expected exception", false);				
	}
	
	public void testGetDeilveredRequests() {
		
		PickupHeader[] headers = dataManager.getDeliveredRequests();
		
		assertEquals(1, headers.length);
		assertEquals("324", headers[0].getCode());
	}
	
	public void testRemoveDeliveredRequests() throws WasteTrackerException {
		
		dataManager.removeDeliveredRequest("324");
		PickupHeader[] headers = dataManager.getDeliveredRequests();
		
		assertEquals(0, headers.length);		
	}
	
	public void testRemoveUndeliveredRequest() {

		try {
			dataManager.removeDeliveredRequest("123");
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("Expected exception", false);				
	}
	
	public void testUnexistentRequest() {
		try {
			dataManager.removeDeliveredRequest("xxx");
		} catch (WasteTrackerException e) {
			return;
		}		
		assertTrue("Expected exception", false);				
	}
	
	public void testGetIncidences() {

		IncidenceEntity inc = new IncidenceEntity();
		inc.setCategory(new CategoryIncidenceEntity(1));
		inc.setDate(new Timestamp(new Date().getTime()));
		inc.setObs("incidence...");
		inc.setReporter("123");
		getJPA().persist(inc);
		
		IncidenceEntity[] res = dataManager.getIncidences();
		assertEquals(1, res.length);
		assertEquals("incidence...", res[0].getObs());
		assertEquals("123", res[0].getReporter());
	}
	
	public void testRemoveIncidence() {

		IncidenceEntity inc = new IncidenceEntity();
		inc.setCategory(new CategoryIncidenceEntity(1));
		inc.setDate(new Timestamp(new Date().getTime()));
		inc.setObs("incidence...");
		inc.setReporter("123");
		getJPA().persist(inc);

		dataManager.removeIncidence(1);
		
		IncidenceEntity[] res = dataManager.getIncidences();
		assertEquals(1, res.length);
		
	}
}