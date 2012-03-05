package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.container.FullScreen;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;
import es.sendit2us.wastetracker.client.blackberry.screens.ApplicationMainScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupCertificationScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupEditScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupSelectionScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.RoadmapScreen;

public class Helper {

	public static final PickupHeader[] EMPTY_PICKUP_LIST = new PickupHeader[0];
	
	//public static final String IMEI = IDENInfo.imeiToString(IDENInfo.getIMEI());
	public static final String IMEI = String.valueOf(DeviceInfo.getDeviceId());
	
	public static final String PONG = "pong";

	/**
	 * Estado de las líneas de detalle
	 */
	public static final int DETAIL_NORMAL = 0;
	public static final int DETAIL_DELETED = 1;
	public static final int DETAIL_ADDED = 2;
	public static final int DETAIL_UPDATED = 3;

	/**
	 * Pantallas accesibles desde el controlador
	 */
	public static final int SCREEN_MAIN = 0;
	public static final int SCREEN_ROADMAP = 1;
	public static final int SCREEN_PICKUPSELECTION = 2;
	public static final int SCREEN_PICKUPEDIT = 3;
	public static final int SCREEN_PICKUPCERTIFICATION = 4;
	
	/**
	 * Cache de pantallas
	 */
	private static FullScreen screenCache[] = new FullScreen[5];
	
	/**
	 * Devuelve una pantalla del caché. Si no existe, la crea.
	 */
	public static FullScreen getScr(Controller controller, int scrId) {
		if (screenCache[scrId] != null) {
			return screenCache[scrId];
		}
		
		FullScreen scr = null;
		switch(scrId) {
		case SCREEN_MAIN:
			scr = new ApplicationMainScreen(controller);
			break;
		case SCREEN_ROADMAP:
			scr = new RoadmapScreen(controller);
			break;
		case SCREEN_PICKUPSELECTION:
			scr = new PickupSelectionScreen(controller);
			break;
		case SCREEN_PICKUPEDIT:
			scr = new PickupEditScreen(controller);
			break;
		case SCREEN_PICKUPCERTIFICATION:
			scr = new PickupCertificationScreen(controller);
			break;
		}
		screenCache[scrId] = scr;
		return scr;
	}
	
	/**
	 * Obtiene la pantalla cacheada, que puede ser NULL si no se ha creado todavía.
	 */
	public static FullScreen getCachedScr(int scrId) {
		return screenCache[scrId];
	}

	/**
	 * Localiza un destino dentro de un array de destinos, por su campo ID.
	 */
	public static int indexOf(PickupHeader[] pickupList, int pickupId) {
		if (pickupList != null) {
			for(int i = 0; i < pickupList.length; i++) {
				if (pickupList[i].getId() == pickupId) {
					return i;
				}
			}
		}
		
		return -1;
	}	
	public static String replace(String _text, String _searchStr, String _replacementStr) {
		// String buffer to store str
		StringBuffer sb = new StringBuffer();
		// Search for search
		int searchStringPos = _text.indexOf(_searchStr);
		int startPos = 0;
		int searchStringLength = _searchStr.length(); 
		// Iterate to add string 
		while (searchStringPos != -1) { 
			sb.append(_text.substring(startPos, searchStringPos)).append(_replacementStr); 
			startPos = searchStringPos + searchStringLength; 
			searchStringPos = _text.indexOf(_searchStr, startPos); 
		} 
		// Create string 
		sb.append(_text.substring(startPos,_text.length())); 
		return sb.toString();
	}
}