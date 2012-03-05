package es.sendit2us.server.ws.tests;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class TestKSoap2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String serviceUrl = "http://wastetracker.corecanarias.com:8080/WasteTracker-WasteTrackerServer/WasteTrackerMobileImpl";
		String serviceNamespace = "http://facade.server.wastetracker.sendit2us.es/WT";
		String soapAction = "WasteTrackerMobileImplService";
		String methodName = "getUnasignedRequests";

		try {
			SoapObject rpc = new SoapObject(serviceNamespace, methodName);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

			envelope.bodyOut = rpc;
			envelope.encodingStyle = SoapSerializationEnvelope.XSD;

			HttpTransportSE ht = new HttpTransportSE(serviceUrl);
			ht.debug = true;

			ht.call(soapAction, envelope);
			SoapObject resO = (SoapObject)envelope.getResponse();
			SoapObject p = (SoapObject)resO.getProperty(0);
			
			String result = (envelope.getResult()).toString();
			System.out.println(resO.getProperty(4));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
