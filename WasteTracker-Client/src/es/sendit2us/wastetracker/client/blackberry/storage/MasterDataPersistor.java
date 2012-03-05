package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import es.sendit2us.wastetracker.client.blackberry.rest.MasterDataEntity;

public class MasterDataPersistor {

	protected static MasterDataEntity[] getStoredMasterDataFor(long guid) {
		MasterDataEntity[] list = null;

		try {
			PersistentObject po = PersistentStore.getPersistentObject(guid);

			synchronized (po) {
				Object obj = po.getContents();
				list = cloneList((MasterDataEntity[]) obj);
			}
		} catch (Exception e) {
			System.out.println("Error cargando datos persistentes: " + e.getMessage());
		}

		return list;
	}

	protected static void storeMasterDataFor(long guid, MasterDataEntity[] list) {
		MasterDataEntity[] clonedList = cloneList(list);

		try {
			PersistentObject po = PersistentStore.getPersistentObject(guid);

			synchronized (po) {
				po.setContents(clonedList);
				po.commit();
			}
		} catch (Exception e) {
		}
	}
	
	private static MasterDataEntity[] cloneList(MasterDataEntity[] list) {
		MasterDataEntity[] copy = null;
		if (list != null) {
			copy = list[0].getArray(list.length);
			for (int i = 0; i < list.length; i++) {
				copy[i] = list[i].clone();
			}
		}
		return copy;
	}
}
