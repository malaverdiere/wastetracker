package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.system.Characters;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import es.sendit2us.wastetracker.client.blackberry.CategoryChoiceField;
import es.sendit2us.wastetracker.client.blackberry.rest.CategoryIncidenceEntity;
import es.sendit2us.wastetracker.client.blackberry.storage.Incidence;

public class IncidencePromptScreen extends PopupScreen {

	private Incidence _response;
	
	private ObjectChoiceField type;
	private TextField message;

	public IncidencePromptScreen(CategoryIncidenceEntity[] types) {
		super(new VerticalFieldManager(), Field.FOCUSABLE);
		
		_response = new Incidence();
		LabelField caption = new LabelField("Nueva incidencia");
		type = new CategoryChoiceField("Tipo de incidencia:", types, 0, Field.FOCUSABLE | Field.USE_ALL_WIDTH);
		message = new TextField(Field.FOCUSABLE | Field.EDITABLE | Field.USE_ALL_WIDTH);
		add(caption);
		add(new SeparatorField());
		add(type);
		add(message);
		add(new SeparatorField());

		ButtonField acceptField = new ButtonField("Registrar", ButtonField.CONSUME_CLICK);
		acceptField.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				accept();
			}
		});
		add(acceptField);
	}

	public void accept() {
		CategoryIncidenceEntity theType = (CategoryIncidenceEntity) type.getChoice(type.getSelectedIndex());
		if (theType.getId() == -1) {
			Dialog.alert("Debe escoger un tipo de incidencia.");
			return;
		}

		String txt = message.getText();
		if (txt != null) {
			txt = txt.trim();
		}
		if ("".equals(txt)) {
			Dialog.alert("Debe escribir el texto de la incidencia.");
			return;
		}
		
		_response.setType(theType.getId());
		_response.setMessage(txt);
		close();
	}

	public void close() {
		UiApplication.getUiApplication().popScreen(this);
	}

	public Incidence getResponse() {
		return _response;
	}

	public boolean keyChar(char key, int status, int time) {
		// intercept the ESC key - exit the app on its receipt
		boolean retval = false;
		switch (key) {
		case Characters.ENTER:
			accept();
			retval = true;
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
