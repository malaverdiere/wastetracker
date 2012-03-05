package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.system.Characters;
import net.rim.device.api.system.KeyListener;
import net.rim.device.api.system.TrackwheelListener;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class CodePromptScreen extends PopupScreen {

	private String expectedResponse;
	private String _response;
	private TextField answer;

	public CodePromptScreen(String expectedResponse) {
		super(new VerticalFieldManager(), Field.FOCUSABLE);
		this.expectedResponse = expectedResponse;
		_response = "";
		LabelField question = new LabelField("Introduzca el código de verificación:");
		answer = new TextField(Field.FOCUSABLE | Field.EDITABLE | Field.USE_ALL_WIDTH);
		add(question);
		add(new SeparatorField());
		add(answer);
		add(new SeparatorField());

		ButtonField acceptField = new ButtonField("Certificar", ButtonField.CONSUME_CLICK);
		acceptField.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				accept();
			}
		});
		add(acceptField);
	}

	public void accept() {
		if (expectedResponse.equals(answer.getText())) {
			close();
			_response = answer.getText();
		} else {
			Dialog.alert("¡El código introducido no es correcto!");
		}
	}

	public void close() {
		_response = "";
		UiApplication.getUiApplication().popScreen(this);
	}

	public String getResponse() {
		return _response;
	}

	public boolean keyChar(char key, int status, int time) {
		// intercept the ESC key - exit the app on its receipt
		boolean retval = true;
		switch (key) {
		case Characters.ENTER:
			accept();
			break;
		case Characters.ESCAPE:
			close();
			break;
		default:
			retval = super.keyChar(key, status, time);
		}
		return retval;
	}
}
