package es.sendit2us.wastetracker.server.facade;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import es.sendit2us.wastetracker.server.controller.MasterDataController;
import es.sendit2us.wastetracker.server.controller.PickupRequestController;
import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;
import es.sendit2us.wastetracker.server.model.Address;
import es.sendit2us.wastetracker.server.model.CategoryContainer;
import es.sendit2us.wastetracker.server.model.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.server.model.CategoryProduct;
import es.sendit2us.wastetracker.server.model.Customer;
import es.sendit2us.wastetracker.server.model.FamilyProduct;
import es.sendit2us.wastetracker.server.model.IncidenceEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestDetailEntity;
import es.sendit2us.wastetracker.server.model.PickupRequestEntity;
import es.sendit2us.wastetracker.server.model.Usage;
import es.sendit2us.wastetracker.server.model.WasteDescriptionEntity;

@Stateless
@Remote(DataManager.class)
@WebService(name = "DataManagerImpl", targetNamespace = "http://wastetracker.corecanarias.com/facade")
@SOAPBinding(style=SOAPBinding.Style.DOCUMENT)
public class DataManagerImpl implements DataManager {
	@EJB private MasterDataController controller;
	@EJB private PickupRequestController requestController;
	
	@Override @WebMethod
	public int addRequest(@WebParam(name="code") String code, @WebParam(name="customerCode") String customerCode, @WebParam(name="name") String name, 
			@WebParam(name="company") String company, @WebParam(name="address") String address, @WebParam(name="zipCode") String zipCode, @WebParam(name="city") String city, 
			@WebParam(name="ccaa") String ccaa, @WebParam(name="country") String country, @WebParam(name="phone1") String phone1, 
			@WebParam(name="phone2") String phone2, @WebParam(name="customerAuthCode") String customerAuthCode, @WebParam(name="detail") PickupDetail[] detail) throws WasteTrackerException {
		
		PickupRequestEntity request = new PickupRequestEntity();
		request.setCode(code);
		request.setCustomerAuthCode(customerAuthCode);
		Customer customer = new Customer();
		customer.setCompany(company);
		customer.setCustomerCode(customerCode);
		customer.setName(name);
		Address addressComponent = new Address();
		addressComponent.setAddress(address);
		addressComponent.setCcaa(ccaa);
		addressComponent.setCity(city);
		addressComponent.setCountry(country);
		addressComponent.setPhone1(phone1);
		addressComponent.setPhone2(phone2);
		addressComponent.setZipCode(zipCode);
		customer.setAddress(addressComponent);
		request.setCustomer(customer);
		request.setDetail(new HashSet<PickupRequestDetailEntity>());
		for(PickupDetail d: detail) {
			PickupRequestDetailEntity requestDet = new PickupRequestDetailEntity();
			requestDet.setRequest(request);
			CategoryContainer container = new CategoryContainer();
			container.setCode(d.getContainerCode());
			container.setDescription(d.getContainerDescr());
			requestDet.setContainer(container);
			CategoryProduct category = new CategoryProduct();
			category.setCode(d.getCategoryCode());
			category.setDescription(d.getCategoryDesc());
			FamilyProduct family = new FamilyProduct();
			family.setCode(d.getFamilyCode());
			family.setDescription(d.getFamilyDesc());
			category.setFamily(family);
			Usage usage = new Usage();
			usage.setCode(d.getUsageCode());
			usage.setDescription(d.getUsageDesc());
			category.setUsage(usage);
			requestDet.setCategory(category);
			request.getDetail().add(requestDet);
		}
		controller.addPickupRequest(request);
		return request.getId();
	}

	
	@Override @WebMethod
	public void addIncidenceCategory(@WebParam(name="code") String code, @WebParam(name="description") String description) {
		
		CategoryIncidenceEntity categoryIncidence = new CategoryIncidenceEntity();
		categoryIncidence.setCode(code);
		categoryIncidence.setDescription(description);
		
		controller.saveCategoryIncidence(categoryIncidence);
	}
	
	@WebMethod
	public CategoryIncidenceEntity[] getIncidenceCategories() {
		List<CategoryIncidenceEntity> res = controller.getIndicenceCategories();
		return res.toArray(new CategoryIncidenceEntity[] {});
	}
	
	@Override @WebMethod
	public void removeIncidenceCategory(@WebParam(name="code") String code) throws WasteTrackerException {
		controller.removeIncidenceCategory(code);
	}
	
	@WebMethod
	public void addWasteDescription(@WebParam(name="code") String code, @WebParam(name="description") String description) {
		WasteDescriptionEntity w = new WasteDescriptionEntity();
		w.setCode(code);
		w.setDescription(description);
		controller.saveWasteDescription(w);
	}
	
	@WebMethod
	public WasteDescriptionEntity[] getWasteDescriptionList() {

		List<WasteDescriptionEntity> res = controller.getWasteDescriptionList();
		return res.toArray(new WasteDescriptionEntity[] {});
	}

	@Override @WebMethod
	public void removeWasteDescription(@WebParam(name="code") String code) throws WasteTrackerException {
		controller.removeWasteDescription(code);
	}
	
	@Override @WebMethod
	public void addImei(@WebParam(name="imei") String imei) throws WasteTrackerException {
		
		controller.addImei(imei);
	}
	
	@Override @WebMethod
	public Boolean imeiExists(@WebParam(name="imei") String imei) {
	
		return controller.imeiExists(imei);
	}
	
	@Override @WebMethod
	public void removeImei(@WebParam(name="imei") String imei) throws WasteTrackerException {
		
		controller.removeImei(imei);
	}

	@Override @WebMethod
	public PickupHeader[] getDeliveredRequests() {
		List<PickupRequestEntity> res = requestController.getDeliveredRequests();
		ArrayList<PickupHeader> delivered = new ArrayList<PickupHeader>();		
		for(PickupRequestEntity r: res) {
			PickupHeader h = VOFactory.newPickupHeader(r);
			delivered.add(h);
			List<PickupDetail> det = VOFactory.retrieveDetail(r);
			h.setDetail(det);
		}
		
		return delivered.toArray(new PickupHeader[] {});
	}


	@Override
	public void removeDeliveredRequest(String code) throws WasteTrackerException {
		requestController.removeDeliveredRequest(code);
	}
	
	public IncidenceEntity[] getIncidences() {
		List<IncidenceEntity> res = controller.getIncidences();
		
		return res.toArray(new IncidenceEntity[] {});
	}
	
	public void removeIncidence(int id) {

		controller.removeIncidence(id);
	}
}
