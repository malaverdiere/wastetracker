package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.ui.TransitionContext;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngineInstance;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.FullScreen;
import net.rim.device.api.util.Arrays;

import org.json.JSONException;
import org.json.JSONObject;

import es.sendit2us.wastetracker.client.blackberry.concurrency.BackgroundSyncThread;
import es.sendit2us.wastetracker.client.blackberry.json.WasteTrackerMobileJSONStub;
import es.sendit2us.wastetracker.client.blackberry.rest.AssignResponse;
import es.sendit2us.wastetracker.client.blackberry.rest.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.client.blackberry.rest.CommunicationException;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupDetail;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;
import es.sendit2us.wastetracker.client.blackberry.screens.CodePromptScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.IncidencePromptScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupCertificationScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupEditScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.PickupSelectionScreen;
import es.sendit2us.wastetracker.client.blackberry.screens.RoadmapScreen;
import es.sendit2us.wastetracker.client.blackberry.storage.GlobalStorage;
import es.sendit2us.wastetracker.client.blackberry.storage.ClosedPickup;

public class WasteTracker extends UiApplication implements Controller {

	private RemoteCaller remoting;
	
	private PickupHeader[] roadmap;
	
	public static void main(String[] args) {
		System.out.println("Empezando...");
		BackgroundSyncThread syncThread = new BackgroundSyncThread();
		syncThread.start();
		UiApplication app = new WasteTracker();
		app.enterEventDispatcher();
		System.out.println("Parando...");
		syncThread.stop();
		try {
			syncThread.join();
		} catch (InterruptedException e) {
			System.out.println("INTERRUMPIDO!!!");
			e.printStackTrace();
		}
		System.out.println("Terminando");
	}

	public WasteTracker() {
		super();

		pushScreen(Helper.getScr(this, Helper.SCREEN_MAIN));

		TransitionContext transitionContextIn;
		TransitionContext transitionContextOut;
		UiEngineInstance engine = Ui.getUiEngineInstance();
		transitionContextIn = new TransitionContext(TransitionContext.TRANSITION_ZOOM);
		transitionContextIn.setIntAttribute(TransitionContext.ATTR_DURATION, 150);
		transitionContextIn.setIntAttribute(TransitionContext.ATTR_KIND, TransitionContext.KIND_IN);

		transitionContextOut = new TransitionContext(TransitionContext.TRANSITION_ZOOM);
		transitionContextOut.setIntAttribute(TransitionContext.ATTR_DURATION, 150);
		transitionContextOut.setIntAttribute(TransitionContext.ATTR_KIND, TransitionContext.KIND_OUT);

		engine.setTransition(null, null, UiEngineInstance.TRIGGER_PUSH, transitionContextIn);
		engine.setTransition(null, null, UiEngineInstance.TRIGGER_POP, transitionContextOut);		
		
		remoting = new RemoteCaller();
		MasterData.isSetupNeeded = true;
	}
	
	public PickupHeader[] getRoadmap() {
		return roadmap;
	}

	public void cancel(FullScreen screen) {
		FullScreen targetScreen = null;
		
		if (screen == Helper.getCachedScr(Helper.SCREEN_PICKUPEDIT) || 
				(screen == Helper.getCachedScr(Helper.SCREEN_PICKUPSELECTION) && hasRoadmap())) {
			targetScreen = Helper.getCachedScr(Helper.SCREEN_ROADMAP);
		}
		switchScreen(screen, targetScreen);
	}
	
	public void reportIncidence(FullScreen screen) {
		IncidencePromptScreen incidencePrompt = new IncidencePromptScreen(MasterData.incidenceTypes);
		UiApplication.getUiApplication().pushModalScreen(incidencePrompt);
		GlobalStorage.addObject(incidencePrompt.getResponse());
	}
	
	/**
	 * Hasta nueva orden, el único menú implementado es el principal.
	 */
	public void menuItemSelected(FullScreen screen, int idx) {
		FullScreen newScreen = null;
		
		try {
			switch (idx) {
			case 0:
				newScreen = getRoadmapScreen();
				break;
			case 1:
				updateMasterData();
				break;
			default:
				Dialog.inform("Opción por defecto " + idx);
			}
			
			if (newScreen != null) {
				pushScreen(newScreen);
			}
		} catch(CommunicationException e) {
			error(e.getMessage());
		}
	}

	public void lockPickups(FullScreen screen, int[] selectedPickups) {
		try {
			AssignResponse response = remoting.assignRequests(selectedPickups);
			roadmap = response.getAssigned();
			GlobalStorage.storeRoadmap(roadmap);
			if (hasRoadmap()) {
				switchScreen(screen, getRoadmapScreen());
			} else {
				switchScreen(screen, null);
			}
		} catch (CommunicationException e) {
			error(e.getMessage());
		}
	}

	public void unlockPickup(FullScreen screen, PickupHeader pickup) {
		remoting.unassignRequest(pickup.getId());
		Arrays.remove(roadmap, pickup);
		GlobalStorage.storeRoadmap(roadmap);
	}

	public void reselectPickups(FullScreen screen, PickupHeader[] currentlySelectedPickups) {
		try {
			switchScreen(screen, getPickupSelectionScreen());
		} catch (CommunicationException e) {
			error(e.getMessage());
		}
	}

	public void openPickup(FullScreen screen, PickupHeader pickup) {
		switchScreen(screen, getPickupEditScreen(pickup));
	}
	
	public void reviewPickup(FullScreen screen, PickupHeader pickup) {
		GlobalStorage.storeRoadmap(roadmap);
		switchScreen(screen, getPickupCertificationScreen(pickup));
	}
	
	public void closePickup(FullScreen screen, PickupHeader pickup) {
		CodePromptScreen codePrompt = new CodePromptScreen(pickup.getCustomerAuthCode());
		pushModalScreen(codePrompt);
		if (!pickup.getCustomerAuthCode().equals(codePrompt.getResponse())) {
			return;
		}

		/* Cerrar el destino */
		PickupDetail[] detail = pickup.getDetail();
		for (int i = 0; i < detail.length; i++) {
			detail[i].setBarcode(pickup.getId() + "-" + detail[i].getId());
		}
		Arrays.remove(roadmap, pickup);
		GlobalStorage.storeRoadmap(roadmap);
		
		/* Pasarlo a pendiente de sincronizar */
		try {
			JSONObject jsonPickup = WasteTrackerMobileJSONStub.encodePickupHeader(pickup);
			GlobalStorage.addObject(new ClosedPickup(jsonPickup.toString()));
			switchScreen(screen, getRoadmapScreen());
		} catch (JSONException e) {
			error("No se ha podido cerrar el destino: JSON error");
		} catch (CommunicationException e) {
			error(e.getMessage());
		}
	}

	public void error(String message) {
		Dialog.alert(message);
	}

	protected void switchScreen(FullScreen fromScreen, FullScreen toScreen) {
		try {
			popScreen(fromScreen);
			if (toScreen != null) {
				pushScreen(toScreen);
			}
		} catch (RuntimeException e) {
			error("Excepción en cambio de pantalla: " + e);
		}
	}

	protected FullScreen getRoadmapScreen() throws CommunicationException {
		if (MasterData.isSetupNeeded) {
			updateMasterData();
		}
		updateRoadmap();
		
		if (!hasRoadmap()) {
			Dialog.inform("La hoja de ruta está vacía y se va a crear una nueva. " +
					"Por favor, añada de la lista siguiente los destinos que desee visitar para " +
					"que sean añadidos a su nueva hoja de ruta.");
			return getPickupSelectionScreen();
		}

		RoadmapScreen scr = (RoadmapScreen) Helper.getScr(this, Helper.SCREEN_ROADMAP);
		scr.refreshPickups();
		return scr;
	}

	protected PickupSelectionScreen getPickupSelectionScreen() throws CommunicationException {
		PickupSelectionScreen scr = (PickupSelectionScreen) Helper.getScr(this, Helper.SCREEN_PICKUPSELECTION);
		PickupHeader[] everything = remoting.getAllAvailablePickups();
		scr.setAvailablePickups(everything);
		return scr;
	}

	protected PickupEditScreen getPickupEditScreen(PickupHeader pickup) {
		PickupEditScreen scr = (PickupEditScreen) Helper.getScr(this, Helper.SCREEN_PICKUPEDIT);
		scr.setPickupHeader(pickup);
		return scr;
	}
	
	protected PickupCertificationScreen getPickupCertificationScreen(PickupHeader pickup) {
		PickupCertificationScreen scr = (PickupCertificationScreen) Helper.getScr(this, Helper.SCREEN_PICKUPCERTIFICATION);
		scr.setPickupHeader(pickup);
		return scr;
	}
	
	private boolean hasRoadmap() {
		return (roadmap != null && roadmap.length > 0);
	}
	
	private void updateMasterData() {
		try {
			MasterData.incidenceTypes = remoting.getIncidenceCategories();
			GlobalStorage.storeCategories(MasterData.incidenceTypes);
		} catch(CommunicationException e) {
			MasterData.incidenceTypes = GlobalStorage.getStoredCategories();
			if (MasterData.incidenceTypes == null) {
				MasterData.incidenceTypes = new CategoryIncidenceEntity[] {};
				error("No hay lista de categorías maestras. Por favor, intente refrescar los datos maestros cuando tenga mejor conectividad.");
			} else {
				Dialog.inform("No se ha podido obtener la lista de categorías maestras del servidor. Se está usando la última lista obtenida.");
			}
		}
		Arrays.insertAt(MasterData.incidenceTypes, new CategoryIncidenceEntity("---", "Escoja un tipo...", -1), 0);
		
		try {
			MasterData.wasteDescriptions = remoting.getWasteDescriptions();
			GlobalStorage.storeDescriptions(MasterData.wasteDescriptions);
		} catch(CommunicationException e) {
			MasterData.wasteDescriptions = GlobalStorage.getStoredDescriptions();
			if (MasterData.wasteDescriptions == null) {
				MasterData.wasteDescriptions = new WasteDescriptionEntity[] {};
				error("No hay lista de descripciones maestras. Por favor, intente refrescar los datos maestros cuando tenga mejor conectividad.");
			} else {
				Dialog.inform("No se ha podido obtener la lista de descripciones maestras del servidor. Se está usando la última lista obtenida.");
			}
		}
		Arrays.insertAt(MasterData.wasteDescriptions, new WasteDescriptionEntity("---", "Escoja una descripción...", -1), 0);
		
		MasterData.isSetupNeeded = false;
	}
	
	private void updateRoadmap() {
		if (!hasRoadmap()) {
			try {
				roadmap = remoting.getAssignedPickups();
				GlobalStorage.storeRoadmap(roadmap);
			} catch(CommunicationException e) {
				roadmap = GlobalStorage.getStoredRoadmap();
				if (hasRoadmap()) {
					error("No se ha podido sincronizar la hoja de ruta con el servidor. Se está usando la última hoja de ruta seleccionada.");
				}
			}
		}
	}
}