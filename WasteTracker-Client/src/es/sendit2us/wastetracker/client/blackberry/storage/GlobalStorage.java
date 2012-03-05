package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.util.Arrays;
import net.rim.device.api.util.Persistable;
import es.sendit2us.wastetracker.client.blackberry.rest.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;

public class GlobalStorage {

	private static final Object lock = new Object();

	/**
	 * Hoja de ruta.
	 */
	
	public static PickupHeader[] getStoredRoadmap() {
		synchronized(lock) {
			return RoadmapPersistor.getStoredRoadmap();
		}
	}
	
	public static void storeRoadmap(PickupHeader[] roadmap) {
		synchronized(lock) {
			RoadmapPersistor.storeRoadmap(roadmap);
		}
	}
	
	/**
	 * Datos maestros.
	 */
	
	public static CategoryIncidenceEntity[] getStoredCategories() {
		synchronized(lock) {
			return (CategoryIncidenceEntity[]) MasterDataPersistor.getStoredMasterDataFor(CategoryIncidenceEntity.serialVersionUID);
		}
	}
	
	public static void storeCategories(CategoryIncidenceEntity[] categories) {
		synchronized(lock) {
			MasterDataPersistor.storeMasterDataFor(CategoryIncidenceEntity.serialVersionUID, categories);
		}
	}

	public static WasteDescriptionEntity[] getStoredDescriptions() {
		synchronized(lock) {
			return (WasteDescriptionEntity[]) MasterDataPersistor.getStoredMasterDataFor(WasteDescriptionEntity.serialVersionUID);
		}
	}
	
	public static void storeDescriptions(WasteDescriptionEntity[] descriptions) {
		synchronized(lock) {
			MasterDataPersistor.storeMasterDataFor(WasteDescriptionEntity.serialVersionUID, descriptions);
		}
	}

	/**
	 * Sincronización de datos que hay que enviar.
	 */
	
	public static SyncData getSyncData() {
		synchronized(lock) {
			SyncData syncData = null;
			Persistable[] objects = SyncObjectPersistor.getStoredList();
			if (objects != null) {
				syncData = new SyncData();
				syncData.setObjects(objects);
			}
			return syncData;
		}
	}
	
	public static void mergeSyncData(SyncData syncData) {
		synchronized(lock) {
			Persistable[] currentObjects = SyncObjectPersistor.getStoredList();
			Persistable[] syncedObjects = syncData.getObjects();
			boolean[] failedObjects = syncData.getFailedObjects();
			int numberToKeep = (currentObjects.length - syncedObjects.length) + syncData.getFailedSyncCount();
			
			SyncObjectPersistor.clear();
			if (numberToKeep > 0) {
				Persistable[] incidencesToKeep = new Persistable[numberToKeep];
				
				for (int i = 0, j = 0; i < currentObjects.length; i++) {
					int syncedIdx = Arrays.getIndex(syncedObjects, currentObjects[i]);
					if (syncedIdx == -1 || failedObjects[syncedIdx]) {
						incidencesToKeep[j] = currentObjects[i];
						j++;
					}
				}
				
				SyncObjectPersistor.storeList(incidencesToKeep);
			}
		}
	}
	
	public static void addObject(Persistable object) {
		synchronized(lock) {
			SyncObjectPersistor.addObject(object);
		}
	}
}
