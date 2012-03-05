package es.sendit2us.wastetracker.client.blackberry.storage;

import net.rim.device.api.util.Persistable;

public class Incidence implements Persistable {
	
	public static final long serialVersionUID = 8015867165347514931L;
	
	private int type;
	private String message;

	public Incidence() {
	}
	
	public Incidence(int type, String message) {
		this();
		this.type = type;
		this.message = message;
	}
	
	public int getType() {
		return type;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		
		if (!(obj instanceof Incidence)) {
			return false;
		}
		
		Incidence incidence = (Incidence) obj;
		if (message == null) {
			return (type == incidence.type && message == incidence.message);
		} else {
			return (type == incidence.type && message.equals(incidence.message));
		}
	}
	
	public static final Incidence clone(Incidence incidence) {
		Incidence clonedIncidence = null;
		if (incidence != null) {
			clonedIncidence = new Incidence(incidence.type, incidence.message);
		}
		return clonedIncidence;
	}
}
