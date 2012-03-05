package es.sendit2us.wastetracker.client.blackberry.concurrency;

import java.rmi.RemoteException;

import net.rim.device.api.util.Persistable;
import es.sendit2us.wastetracker.client.blackberry.Helper;
import es.sendit2us.wastetracker.client.blackberry.rest.RestPort;
import es.sendit2us.wastetracker.client.blackberry.storage.GlobalStorage;
import es.sendit2us.wastetracker.client.blackberry.storage.Incidence;
import es.sendit2us.wastetracker.client.blackberry.storage.ClosedPickup;
import es.sendit2us.wastetracker.client.blackberry.storage.SyncData;

public class BackgroundSyncThread extends Thread {

	private static final int SLEEP_TIME = (5 * 1000);
	
	private RestPort stub;
	private boolean terminated;
	
	public BackgroundSyncThread() {
		super("Background synchro thread");

		setPriority(MIN_PRIORITY);
		
		terminated = true;
		stub = new RestPort();
	}
	
	public void stop() {
		terminated = true;
	}
	
	public void run() {
		System.out.println("Background Sync running...");
		terminated = false;
		while (isAlive() && !terminated) {
			System.out.println(" *** Seeing if we have sync data...");
			SyncData syncData = GlobalStorage.getSyncData();
			if (syncData != null) {
				performSync(syncData);
			}
			
			try {
				System.out.println(" *** Going to sleep...");
				sleep(SLEEP_TIME);
			} catch (InterruptedException e) {
				// nada
			}
		}
		System.out.println("Background Sync stopping...");
	}
	
	private void performSync(SyncData syncData) {
		if (canPing()) {
			System.out.println(" *** Sinchronizing...");

			Persistable[] objects = syncData.getObjects();
			for(int i = 0; i < objects.length; i++) {
				if (objects[i] instanceof Incidence) {
					Incidence incidence = (Incidence) objects[i];
					try {
						stub.registerIncidence(Helper.IMEI, incidence.getType(), incidence.getMessage());
					} catch (Exception e) {
						System.out.println(" !!! Incidence sync failed: '" + incidence.getMessage() + "'");
						
						/* Marcarla como no sincronizada, para no quitarla del almacenamiento */
						syncData.syncFailedForIndex(i);
					}
				} else if (objects[i] instanceof ClosedPickup) {
					ClosedPickup pickup = (ClosedPickup) objects[i];
					try {
						stub.closeRequest(pickup);
					} catch (Exception e) {
						System.out.println(" !!! Pickup sync failed: '" + pickup.getJSONData() + "'");
						
						/* Marcarla como no sincronizada, para no quitarla del almacenamiento */
						syncData.syncFailedForIndex(i);
					}
				}
			}
			
			GlobalStorage.mergeSyncData(syncData);

			System.out.println(" *** Sincronización terminada.");
		}
	}
	
	private boolean canPing() {
		try {
			System.out.println(" *** Pinging...");
			String pong = stub.ping();
			System.out.println(" *** Got '" + pong + "'");
			return (Helper.PONG.equals(pong));
		} catch (RemoteException e) {
			System.out.println(" !!! Servidor no disponible.");
			return false;
		}
	}
}
