package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

public class RoadmapPersistor {

	protected static PickupHeader[] getStoredRoadmap() {
		PickupHeader[] list = null;

		try {
			PersistentObject po = PersistentStore.getPersistentObject(PickupHeader.serialVersionUID);

			synchronized (po) {
				Object obj = po.getContents();
				list = cloneList((PickupHeader[]) obj);
			}
		} catch (Exception e) {
			System.out.println("Error cargando datos persistentes: " + e.getMessage());
		}

		return list;
	}

	protected static void storeRoadmap(PickupHeader[] list) {
		PickupHeader[] clonedList = cloneList(list);

		try {
			PersistentObject po = PersistentStore.getPersistentObject(PickupHeader.serialVersionUID);

			synchronized (po) {
				po.setContents(clonedList);
				po.commit();
			}
		} catch (Exception e) {
		}
	}
	
	private static PickupHeader[] cloneList(PickupHeader[] list) {
		PickupHeader[] copy = null;
		if (list != null) {
			copy = new PickupHeader[list.length];
			for (int i = 0; i < list.length; i++) {
				copy[i] = list[i].clone();
			}
		}
		return copy;
	}
}
