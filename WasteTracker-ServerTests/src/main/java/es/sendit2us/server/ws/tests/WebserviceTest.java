package es.sendit2us.server.ws.tests;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import es.sendit2us.wastetracker.server.facade.PickupHeader;
import es.sendit2us.wastetracker.server.facade.WasteTrackerMobile;

@SuppressWarnings("restriction")
public class WebserviceTest {

	public static void main(String[] args) {
		String endpointURI = "http://192.168.20.29:8080/WasteTracker-WasteTrackerServer/WasteTrackerMobileImpl?wsdl";

		try {
			WasteTrackerMobile p = getPort(endpointURI);
			PickupHeader[] r = p.getUnassignedRequests();
			System.out.println(r[0].getName());
		} catch (MalformedURLException e) {			
			e.printStackTrace();
		}
	}

	private static WasteTrackerMobile getPort(String endpointURI) throws MalformedURLException {
		QName serviceName = new QName(
				"http://facade.server.wastetracker.sendit2us.es/WT",
				"WasteTrackerMobileImplService");
		URL wsdlURL = new URL(endpointURI);

		Service service = Service.create(wsdlURL, serviceName);
		return service.getPort(WasteTrackerMobile.class);
	}
}
