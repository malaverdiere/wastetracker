package es.sendit2us.wastetracker.client.blackberry.concurrency;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.ui.UiApplication;
import es.sendit2us.wastetracker.client.blackberry.screens.GaugeScreen;

public final class GaugeTask {

	private GaugeScreen gaugeScreen;
	private Timer gaugeTimer;
	private boolean timeout;
	
	public GaugeTask() {
		gaugeScreen = new GaugeScreen();
	}
	
	public Object startThread(String message, RemoteCallerThread thread) {
		startGaugeTimer(message);

		try {
			thread.start();
			try {
				while (true) {
					if (!timeout && thread.isAlive()) {
						try {
							UiApplication.getUiApplication().repaint();
						} catch(Exception e) {
							System.out.println("Excepción: " + e);
						}
						
						try {
							Thread.sleep(250);
						} catch (InterruptedException e) {
							System.out.println("Excepción: " + e);
						}
					} else {
						break;
					}
				}
				
				if (timeout) {
					thread.interrupt();
					return null;
				} else {
					return thread.getResult();
				}
			} finally {
				thread = null;
			}
		} finally {
			cancelGaugeTimer();
		}
	}
		
	private void startGaugeTimer(String message) {
		gaugeScreen.setMessage(message);
		gaugeScreen.setValue(0);
		gaugeScreen.show();
		
		timeout = false;
		gaugeTimer = new Timer();
		gaugeTimer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				int val = gaugeScreen.getValue();
				if (val == 100) {
					timeout = true;
				} else {
					gaugeScreen.setValue(val + 10);
				}
			}
		}, 10, 1000);
	}
	
	private void cancelGaugeTimer() {
		gaugeTimer.cancel();
		gaugeTimer = null;
		gaugeScreen.hide();
	}
}
