package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.util.Persistable;

public class SyncData {
	
	private Persistable[] dataObjects;
	private boolean[] failedObjects;
	private int failedSyncCount;
	
	public Persistable[] getObjects() {
		return dataObjects;
	}
	
	public void setObjects(Persistable[] dataObjects) {
		this.dataObjects = dataObjects;
		failedObjects = new boolean[(dataObjects == null ? 0 : dataObjects.length)];
		failedSyncCount = 0;
	}

	public int getFailedSyncCount() {
		return failedSyncCount;
	}
	
	public boolean[] getFailedObjects() {
		return failedObjects;
	}

	public void syncFailedForIndex(int idx) {
		failedObjects[idx] = true;
		failedSyncCount++;
	}
}
