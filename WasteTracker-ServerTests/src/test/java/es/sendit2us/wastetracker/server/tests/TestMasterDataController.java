package es.sendit2us.wastetracker.server.tests;

import java.util.HashSet;

import es.sendit2us.wastetracker.server.controller.MasterDataController;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.Address;
import es.sendit2us.wastetracker.server.model.CategoryContainer;
import es.sendit2us.wastetracker.server.model.CategoryProduct;
import es.sendit2us.wastetracker.server.model.Customer;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;

public class TestMasterDataController extends WasteTrackerTests {
	private MasterDataController dataController;

	@Override
	protected void init() {
		dataController = (MasterDataController)lookup("MasterDataControllerImplLocal");
	}
	
	public void testAddRequest() throws WasteTrackerException {
		PickupRequestEntity request = new PickupRequestEntity();
	
		createRequest(request);
		
		addDetail(request);
		dataController.addPickupRequest(request);
		assertTrue(request.getId() > 0);
	}

	private void addDetail(PickupRequestEntity request) {
		PickupRequestDetailEntity det1 = new PickupRequestDetailEntity();
		CategoryProduct category = new CategoryProduct();
		category.setCode("c1");
		category.setDescription("descr1");
		det1.setCategory(category);
		CategoryContainer container = new CategoryContainer();
		container.setCode("cn1");
		container.setDescription("contadescr1");
		det1.setContainer(container);
		request.setDetail(new HashSet<PickupRequestDetailEntity>());
		request.getDetail().add(det1);
	}

	private void createRequest(PickupRequestEntity request) {
		request.setCode("1");
		Customer customer = new Customer();
		Address address = new Address();
		address.setAddress("address");
		address.setCcaa("ccaa");
		address.setCity("city");
		address.setCountry("country");
		address.setPhone1("phone1");
		address.setPhone2("phone2");
		address.setZipCode("zipCode");
		customer.setAddress(address);
		customer.setCnum("1231J");
		customer.setCompany("company name");
		customer.setCustomerCode("13");
		customer.setName("customer name");
		request.setCustomer(customer);
		request.setCustomerAuthCode("authcode");
	}
	
	public void testAddRequestEmptyDetail() {
		PickupRequestEntity request = new PickupRequestEntity();
		
		createRequest(request);
		
		try {
			dataController.addPickupRequest(request);
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("WasteTrackerException expected", true);
	}
	
	public void testAddRequestEmptyCustomer() throws WasteTrackerException {
		PickupRequestEntity request = new PickupRequestEntity();
		
		createRequest(request);
		request.setCustomer(null);
		addDetail(request);

		try {
			dataController.addPickupRequest(request);					
		} catch(javax.ejb.EJBException e) {
			return;
		}
		assertTrue("EJBException expected", true);
	}
	
	public void testAddDuplicateRequest() throws WasteTrackerException {
		PickupRequestEntity request = new PickupRequestEntity();
		
		createRequest(request);
		addDetail(request);

		PickupRequestEntity request2 = new PickupRequestEntity();
		
		createRequest(request2);
		addDetail(request2);

		dataController.addPickupRequest(request);
		try {
			dataController.addPickupRequest(request2);								
		} catch(javax.ejb.EJBException e) {
			return;
		}
		assertTrue("EJBException expected", true);
	}
	
	public void testAddAssignedRequest() {
		PickupRequestEntity request = new PickupRequestEntity();
		
		createRequest(request);
		request.setDeviceCode("dev");
		addDetail(request);

		try {
			dataController.addPickupRequest(request);
		} catch (WasteTrackerException e) {
			return;
		}
		assertTrue("WasteTrackerException expected", true);
	}
}