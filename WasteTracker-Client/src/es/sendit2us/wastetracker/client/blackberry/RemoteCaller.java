package es.sendit2us.wastetracker.client.blackberry;

import java.rmi.RemoteException;

import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.component.Dialog;
import es.sendit2us.wastetracker.client.blackberry.concurrency.GaugeTask;
import es.sendit2us.wastetracker.client.blackberry.concurrency.RemoteCallerThread;
import es.sendit2us.wastetracker.client.blackberry.rest.AssignResponse;
import es.sendit2us.wastetracker.client.blackberry.rest.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.client.blackberry.rest.CommunicationException;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;
import es.sendit2us.wastetracker.client.blackberry.rest.RestPort;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;

public final class RemoteCaller {

	private GaugeTask gaugeTask;
	private RestPort stub;
	
	protected RemoteCaller() {
		gaugeTask = new GaugeTask();
		stub = new RestPort();		
	}
	
	public CategoryIncidenceEntity[] getIncidenceCategories() throws CommunicationException {
		System.out.println(" *** Antes de pedir categorías");
		CategoryIncidenceEntity[] retval = (CategoryIncidenceEntity[]) gaugeTask.startThread("Sincronizando categorías maestras...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					// TODO si no hay conexión, cargar del almacenamiento
					setResult(stub.getIncidenceCategories());
				} catch (Exception e) {
					System.out.println("Excepción: " + e);
				}
			}
		});
		
		if (retval == null) {
			throw new CommunicationException("No se ha podido sincronizar las categorías maestras.");
		}
		
		return retval;
	}

	public WasteDescriptionEntity[] getWasteDescriptions() throws CommunicationException {
		System.out.println(" *** Antes de pedir descripciones de deshechos");
		WasteDescriptionEntity[] retval = (WasteDescriptionEntity[]) gaugeTask.startThread("Sincronizando descripciones maestras...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					// TODO si no hay conexión, cargar del almacenamiento
					System.out.println(" ***  Dentro del thread");
					setResult(stub.getWasteDescriptionList());
					System.out.println(" ***  Dentro del thread finalizado" );
				} catch (Exception e) {
					System.out.println("Excepción: " + e);
				}
			}
		});		

		if (retval == null) {
			throw new CommunicationException("No se ha podido sincronizar las descripciones maestras.");
		}
		
		return retval;
	}

	public PickupHeader[] getAllAvailablePickups() throws CommunicationException {
		
		PickupHeader[] retval = (PickupHeader[]) gaugeTask.startThread("Sincronizando destinos disponibles...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					setResult(stub.setupPickupRequests(Helper.IMEI));
				} catch (Exception e) {
					System.out.println("Excepción: " + e);
				}
			}
		});
		
		if (retval == null) {
			throw new CommunicationException("No se ha podido obtener la lista de puntos de recogida disponibles. Por favor, inténtelo de nuevo más tarde.");
		}
		
		return retval;
	}

	public PickupHeader[] getAssignedPickups() throws CommunicationException {
		
		PickupHeader[] retval = (PickupHeader[]) gaugeTask.startThread("Sincronizando hoja de ruta...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					setResult(stub.getAssignedRequests(Helper.IMEI));
				} catch (Exception e) {
					System.out.println("Excepción: " + e);
				}
			}
		});
		
		if (retval == null) {
			throw new CommunicationException("No se ha podido obtener la hoja de ruta. Por favor, inténtelo de nuevo más tarde.");
		}
		
		return retval;
	}
	
	public AssignResponse assignRequests(final int[] arg1) throws CommunicationException {
		AssignResponse retval = (AssignResponse) gaugeTask.startThread("Asignando destinos...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					AssignResponse resp = stub.assignRequests(Helper.IMEI, joinIds(arg1)); 
					setResult(resp);
				} catch (RemoteException e) {
					System.out.println("Excepción: " + e);
				}
			}
		});

		if (retval == null) {
			throw new CommunicationException("No se ha podido reservar los puntos de recogida seleccionados. Por favor, inténtelo de nuevo más tarde.");
		} else if (retval.getMessage() != null) {
			throw new CommunicationException(retval.getMessage());
		}
		
		return retval;
	}

	public void unassignRequest(final int arg1) {
		String retval = (String)gaugeTask.startThread("Descartando el destino...", new RemoteCallerThread(stub) {
			public void doRun() {
				try {
					stub.unassignRequests(Helper.IMEI, new Integer(arg1));
					setResult(null);
				} catch (RemoteException e) {
					setResult(e.getMessage());
					System.out.println("Excepción: " + e);
				}
			}
		});

		if (retval != null) {
			Dialog.alert("No se ha podido liberar el punto de recogida seleccionado. Por favor, inténtelo de nuevo más tarde.");
		}
	}

	/**
	 * Concatenador de identificadores en un único String, para evitar la serialización de los arrays en la BlackBerry.
	 */
	private String joinIds(final int[] ids) {
		if (ids == null || ids.length == 0) {
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(ids[0]);
		for (int i = 1; i < ids.length; i++) {
			buffer.append(',');
			buffer.append(ids[i]);
		}
		return buffer.toString();
	}
}
