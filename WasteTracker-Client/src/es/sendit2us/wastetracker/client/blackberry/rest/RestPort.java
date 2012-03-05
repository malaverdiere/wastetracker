package es.sendit2us.wastetracker.client.blackberry.rest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.rmi.RemoteException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.Base64OutputStream;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.sendit2us.wastetracker.client.blackberry.ByteBuffer;
import es.sendit2us.wastetracker.client.blackberry.Helper;
import es.sendit2us.wastetracker.client.blackberry.json.WasteTrackerMobileJSONStub;
import es.sendit2us.wastetracker.client.blackberry.storage.ClosedPickup;

public class RestPort implements WasteTrackerMobile {
	
	private static final String NULLPARAM = "%20";  // Esto es un hack! Utilizar POST en lugar de GET!
	
	private String url;
	private String login = "wastetracker:wastetracker";
	
	public RestPort() {	
		this("http://wastetracker.corecanarias.com:8080/");
	}
	
	private RestPort(String url) {
		if(url.endsWith("/")) {
			this.url = url + "resteasy/wastetracker/";			
		} else {
			this.url = url + "/resteasy/wastetracker/";						
		}
	}
	public AssignResponse assignRequests(String deviceCode, String pickups) throws RemoteException {
		
		try {
			String res = get("assignRequests", new String[] {deviceCode, pickups});
			return WasteTrackerMobileJSONStub.decodeAssignResponse(new JSONObject(res));
		} catch (JSONException e) {
			throw new RemoteException(e.getMessage());
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	public PickupHeader[] getAssignedRequests(String deviceCode) throws RemoteException {
		
		try {
			// TODO comprobar qué sucede cuando el servidor responda algo incorrecto
			String res = get("getAssignedRequests", new String[] {deviceCode});
			return WasteTrackerMobileJSONStub.decodePickupHeaderList(new JSONArray(res));
		} catch (JSONException e) {
			throw new RemoteException(e.getMessage());
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public CategoryIncidenceEntity[] getIncidenceCategories() throws RemoteException {
		try {
			String res = get("getIncidenceCategories", null);
			return WasteTrackerMobileJSONStub.decodeCategoryIncidenceList(new JSONArray(res));
		} catch (JSONException e) {
			throw new RemoteException(e.getMessage());
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public PickupHeader[] getUnassignedRequests() throws RemoteException {
		try {
			String res = get("getUnassignedRequests", null);
			return WasteTrackerMobileJSONStub.decodePickupHeaderList(new JSONArray(res));
		} catch (JSONException e) {
			throw new RemoteException(e.getMessage());
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	public WasteDescriptionEntity[] getWasteDescriptionList() throws RemoteException {
		try {
			String res = get("getWasteDescriptionList", null);
			System.out.println("*********************************++ getWasteDescriptionList");
			System.out.println(res);
			return WasteTrackerMobileJSONStub.decodeWasteDescriptionList(new JSONArray(res));
		} catch (Exception e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public String ping() throws RemoteException {
		try {
			return get("ping", null);
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public void registerIncidence(String deviceCode, int type, String message) throws RemoteException {
		
		try {
			post("registerIncidence", new String[] {deviceCode, String.valueOf(type)}, new String[] {"obs"}, new String[] {message});
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	/**
	 * Obsoleto
	 */
	public PickupHeader[] setupPickupRequests(String arg0) throws RemoteException {
		try {
			String res = get("setupPickupRequests", new String[] {arg0});
			return WasteTrackerMobileJSONStub.decodePickupHeaderList(new JSONArray(res));
		} catch (JSONException e) {
			throw new RemoteException(e.getMessage());
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	public void unassignRequests(String arg0, Integer arg1) throws RemoteException {
		try {
			get("unassignRequests", new String[] {arg0, arg1.toString()});
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}
	
	public void closeRequest(ClosedPickup pickup) throws RemoteException {
		try {
			post("closeRequest", null, new String[] {"pickupHeader"}, new String[] {pickup.getJSONData()});
		} catch (IOException e) {
			throw new RemoteException(e.getMessage());
		}
	}

	protected String get(String function, String[] params) throws IOException {
		String pStr = composeParams(params, "/");
		
		System.out.println("||||||||||||||||||||||||||||||: " + url + function + "/" + pStr + getDeviceSide());
		HttpConnection conn = (HttpConnection)Connector.open(url + function + "/" + pStr + getDeviceSide());
		byte[] encoded = Base64OutputStream.encode(login.getBytes(), 0, login.length(), false, false);
		conn.setRequestProperty("Authorization", "Basic " + new String(encoded));
		conn.setRequestMethod(HttpConnection.GET);
		InputStream _inputStream = conn.openInputStream();
		final ByteBuffer bb = new ByteBuffer(_inputStream);
		return bb.getString();
	}

	protected String post(String function, String[] pathParms, String[] paramNames, String[] params) throws IOException {		
		String paramsStr = composeParams(pathParms, "/");
		
		String postStr = "";
		
		for(int i = 0; i < paramNames.length; i++) {
			postStr = postStr + "&" + paramNames[i] + "=" + normalizeString(params[i]);
		}
		System.out.println("||||||||||||||||||||||||||||||: " + url + function + "/" + paramsStr + getDeviceSide());
		HttpConnection conn = (HttpConnection)Connector.open(url + function + "/" + paramsStr + getDeviceSide());
		byte[] encoded = Base64OutputStream.encode(login.getBytes(), 0, login.length(), false, false);
		conn.setRequestProperty("Authorization", "Basic " + new String(encoded));
		conn.setRequestMethod(HttpConnection.POST);
		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		OutputStream os = conn.openOutputStream();
		os.write(postStr.getBytes());
		
		InputStream _inputStream = conn.openInputStream();
		final ByteBuffer bb = new ByteBuffer(_inputStream);
		return bb.getString();
	}

	private String composeParams(String[] params, String separator) {
		String pStr = "";
		if(params != null) {
			for(int i = 0; i < params.length; i++) {
				String nethParam = normalizeString(params[i]);
				pStr = pStr + nethParam + separator;
			}			
		}
		return pStr;
	}
	
	private String normalizeString(String s) {
		if (s == null || "".equals(s.trim())) {
			s = NULLPARAM;
		} else {
			s = Helper.replace(s, "&", "%26");
			s = Helper.replace(s, " ", "%20");
			s = Helper.replace(s, "/", "%2F");
			s = Helper.replace(s, "=", "%3D");
		}		
		return s;
	}
	
   protected boolean wifiConnectivityDetected() {
      ServiceRecord[] records = ServiceBook.getSB().getRecords();
 
      System.out.println("Detectando conectividad WIFI");
      for(int i = 0; i < records.length; i++) {
          //Search through all service records to find the valid Wi-Fi
          if (records[i].isValid() && !records[i].isDisabled()) {
               String uid = records[i].getUid();
              if (uid != null && uid.length() > 0) {
                if (uid.toLowerCase().indexOf("wifi") != -1) {
                        System.out.println("WIFI detectada en UID " + uid);
                        return true;
                }
              }
          }
      }
      System.out.println("WIFI no detectada!");
      return false;
} 
	private String getDeviceSide() {
		if(wifiConnectivityDetected()) {
			return ";interface=wifi";			
		} else {
			return ";deviceside=true";						
		}
	}
}
