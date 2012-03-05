package es.sendit2us.wastetracker.client.blackberry.concurrency;

import es.sendit2us.wastetracker.client.blackberry.rest.RestPort;


public abstract class RemoteCallerThread extends Thread {

	private RestPort stub;
	private Object result;
	
	protected RemoteCallerThread(RestPort stub) {
		super("Remote caller");

		this.stub = stub;
	}
	
	public abstract void doRun();
	
	protected void setResult(Object result) {
		this.result = result;
	}
	
	public Object getResult() {
		return result;
	}
	
	public void run() {
		result = null;
		doRun();
	}
}
