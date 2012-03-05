package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.GaugeField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class GaugeScreen extends PopupScreen {
	
	private static float ITEM_FONT_MULTIPLIER = 0.8f;
	
	private int currentValue;
	private LabelField messageLabel;
	private GaugeField progressBar;

	public GaugeScreen() {
		super(new VerticalFieldManager(), Field.FOCUSABLE);

		messageLabel = new LabelField();
		messageLabel.setFont(Font.getDefault().derive(Font.PLAIN, (int) (Font.getDefault().getHeight() * ITEM_FONT_MULTIPLIER)));
		
		progressBar = new GaugeField(null, 0, 100, 0, GaugeField.PERCENT);
		currentValue = 0;
		
		add(messageLabel);
		add(progressBar);
	}
	
	public void setMessage(String message) {
		messageLabel.setText(message);
	}
	
	public void setValue(int value) {
		if (value < 0) {
			value = 0;
		} else if (value > 100) {
			value = 100;
		}
		
		currentValue = value;
		progressBar.setValue(value);
	}
	
	public int getValue() {
		return currentValue;
	}
	
	public void show() {
		UiApplication.getUiApplication().pushScreen(this);
		UiApplication.getUiApplication().repaint();
	}

	public void hide() {
		UiApplication.getUiApplication().popScreen(this);
	}
}
