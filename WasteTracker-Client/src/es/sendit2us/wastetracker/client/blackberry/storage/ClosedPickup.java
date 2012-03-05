package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.util.Persistable;

public class ClosedPickup implements Persistable {
	
	public static final long serialVersionUID = 576629898256072497L;
	
	private String jsonData;
	
	public ClosedPickup(String jsonData) {
		this.jsonData = jsonData;
	}
	
	public String getJSONData() {
		return jsonData;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof ClosedPickup)) {
			return false;
		}
		
		ClosedPickup pickup = (ClosedPickup) obj;
		return (jsonData == pickup.jsonData);
	}
	
	public static final ClosedPickup clone(ClosedPickup closedPickup) {
		ClosedPickup clonedClosedPickup = null;
		if (closedPickup != null) {
			clonedClosedPickup = new ClosedPickup(closedPickup.jsonData);
		}
		return clonedClosedPickup;
	}
}
