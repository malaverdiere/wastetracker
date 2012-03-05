package es.sendit2us.wastetracker.server.facade;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

public class AssignResponse implements Serializable {
	private PickupHeader[] assigned;
	private String message = null;
	
	public PickupHeader[] getAssigned() {
		return assigned;
	}
	public void setAssigned(PickupHeader[] assigned) {
		this.assigned = assigned;
	}
	public String getMessage() {
		return StringUtils.trimToNull(message);
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void addMessage(String msg) {
		if(message == null) {
			this.message = msg + "\n";			
		} else {
			this.message += msg + "\n";			
		}
	}
}
