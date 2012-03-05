package es.sendit2us.client.wstest;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.BindingProvider;

import es.sendit2us.client.wstest.stubs.DataManagerImpl;
import es.sendit2us.client.wstest.stubs.DataManagerImplService;
import es.sendit2us.client.wstest.stubs.PickupDetail;
import es.sendit2us.client.wstest.stubs.WasteTrackerException_Exception;

public class ApplicationMain {

	public ApplicationMain() {
		DataManagerImplService service = new DataManagerImplService();
		DataManagerImpl port = service.getDataManagerImplPort();
		BindingProvider bp = (BindingProvider)port;
		bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "wastetracker");
		bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "wastetracker");
		
		PickupDetail d = new PickupDetail();
		d.setCategoryCode("catcode");
		d.setCategoryDesc("catdescr");
		d.setContainerCode("contcode");
		d.setContainerDescr("contdescr");
		d.setFamilyCode("famcode");
		d.setFamilyDesc("famdescr");
		d.setUsageCode("usagecode");
		d.setUsageDesc("usagedescr");
		List<PickupDetail> detail = new ArrayList<PickupDetail>();
		detail.add(d);
		try {
			int code = port.addRequest("code", "customerCode", "name", "company", "address", "zipCode", "city", "ccaa", "country", "phone1", "phone2", "customerAuthCode", detail);
			System.out.println("request added: " + code);
		} catch (WasteTrackerException_Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {

		new ApplicationMain();
	}
}
