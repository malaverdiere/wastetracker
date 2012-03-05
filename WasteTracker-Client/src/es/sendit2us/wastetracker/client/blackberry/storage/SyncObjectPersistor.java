package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.util.Arrays;
import net.rim.device.api.util.Persistable;

public class SyncObjectPersistor {

	private static final long serialVersionUID = 3842371190456039514L;

	protected static Persistable[] getStoredList() {
		Persistable[] list = null;

		try {
			PersistentObject po = PersistentStore.getPersistentObject(serialVersionUID);

			synchronized (po) {
				Object obj = po.getContents();
				list = cloneList((Persistable[]) obj);
			}
		} catch (Exception e) {
			System.out.println("Error cargando datos persistentes: " + e.getMessage());
		}

		return list;
	}

	protected static void storeList(Persistable[] list) {
		Persistable[] clonedList = cloneList(list);

		try {
			PersistentObject po = PersistentStore.getPersistentObject(serialVersionUID);

			synchronized (po) {
				po.setContents(clonedList);
				po.commit();
			}
		} catch (Exception e) {
		}
	}
	
	protected static void addObject(Persistable object) {
		try {
			PersistentObject po = PersistentStore.getPersistentObject(serialVersionUID);

			synchronized (po) {
				Persistable[] list;
				
				Object obj = po.getContents();
				if (obj != null) {
					list = (Persistable[]) obj;
					Arrays.add(list, object);
				} else {
					list = new Persistable[] { object };
				}
				
				po.setContents(list);
				po.commit();
			}
		} catch (Exception e) {
			System.out.println("Error cargando datos persistentes: " + e.getMessage());
		}
	}

	protected static void clear() {
		try {
			PersistentStore.destroyPersistentObject(serialVersionUID);
		} catch (Exception e) {
			System.out.println("Error borrando datos persistentes: " + e.getMessage());
		}
	}
	
	private static Persistable[] cloneList(Persistable[] list) {
		Persistable[] clonedList = null;
		if (list != null) {
			clonedList = new Persistable[list.length];
			for (int i = 0; i < list.length; i++) {
				if (Incidence.class.isAssignableFrom(list[i].getClass())) {
					clonedList[i] = Incidence.clone((Incidence) list[i]);
				} else if (ClosedPickup.class.isAssignableFrom(list[i].getClass())) {
					clonedList[i] = ClosedPickup.clone((ClosedPickup) list[i]);
				} else {
					throw new RuntimeException("Invalid persistable class " + list[i]);
				}
			}
		}
		return clonedList;
	}
}
