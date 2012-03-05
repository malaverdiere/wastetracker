package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.Persistable;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;

public class WasteDescriptionListPersistor implements Persistable {

	final static long PERSISTENCE_GUID = WasteDescriptionListPersistor.class.hashCode();

	private WasteDescriptionEntity[] persistableObjects = null;

	protected static WasteDescriptionEntity[] getStoredDescriptions() {
		return load().persistableObjects;
	}

	protected static void storeDescriptions(WasteDescriptionEntity[] descriptions) {
		WasteDescriptionListPersistor newList = new WasteDescriptionListPersistor();
		newList.persistableObjects = descriptions;
		save(newList);
	}

	private static WasteDescriptionListPersistor load() {
		WasteDescriptionListPersistor list = null;

		try {
			PersistentObject po = PersistentStore.getPersistentObject(WasteDescriptionListPersistor.PERSISTENCE_GUID);

			synchronized (po) {
				Object obj = po.getContents();
				list = ((obj == null) ? new WasteDescriptionListPersistor() : (WasteDescriptionListPersistor) obj);
			}
		} catch (Exception e) {
			list = new WasteDescriptionListPersistor();
		}

		return list;
	}

	private static void save(WasteDescriptionListPersistor list) {
		try {
			PersistentObject po = PersistentStore.getPersistentObject(WasteDescriptionListPersistor.PERSISTENCE_GUID);

			synchronized (po) {
				po.setContents(list);
				po.commit();
			}
		} catch (Exception e) {
		}
	}
}
