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
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.PopupScreen;
import net.rim.device.api.ui.container.VerticalFieldManager;
import es.sendit2us.wastetracker.client.blackberry.DescriptionChoiceField;
import es.sendit2us.wastetracker.client.blackberry.Helper;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupDetail;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;

public class DetailEditScreen extends PopupScreen {

	private PickupDetail _response;
	
	private ObjectChoiceField type;

	public DetailEditScreen(WasteDescriptionEntity[] types, PickupDetail detail) {
		super(new VerticalFieldManager(), Field.FOCUSABLE);
		
		LabelField caption;
		ButtonField removeField = null;
		if (detail == null) {
			_response = new PickupDetail();
			caption = new LabelField("Nuevo residuo");
		} else {
			_response = detail;
			caption = new LabelField("Editar residuo");
			removeField = new ButtonField("Eliminar", ButtonField.CONSUME_CLICK);
			removeField.setChangeListener(new FieldChangeListener() {
				public void fieldChanged(Field field, int context) {
					remove();
				}
			});
		}
		type = new DescriptionChoiceField("Descripción:", types, 0, Field.FOCUSABLE | Field.USE_ALL_WIDTH);
		add(caption);
		add(new SeparatorField());
		add(type);
		add(new SeparatorField());

		ButtonField acceptField = new ButtonField("Aceptar", ButtonField.CONSUME_CLICK);
		acceptField.setChangeListener(new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				accept();
			}
		});
		
		HorizontalFieldManager horizManager = new HorizontalFieldManager();
		horizManager.add(acceptField);
		if (removeField != null) {
			horizManager.add(removeField);
		}
		add(horizManager);
	}

	public void accept() {
		WasteDescriptionEntity theType = (WasteDescriptionEntity) type.getChoice(type.getSelectedIndex());
		if (theType.getId() == -1) {
			Dialog.alert("Debe escoger una descripción de residuo.");
		} else {
			_response.setCategoryCode(theType.getCode());
			_response.setCategoryDesc(theType.getDescription());
			_response.setState(Helper.DETAIL_UPDATED);
			close();
		}
	}

	public void remove() {
		if (Dialog.ask(Dialog.D_YES_NO, "¿Está seguro de que desea eliminar este contenedor?") == Dialog.YES) {
			_response.setState(Helper.DETAIL_DELETED);
			close();
		}
	}
	
	public void close() {
		UiApplication.getUiApplication().popScreen(this);
	}

	public boolean keyChar(char key, int status, int time) {
		switch (key) {
		case Characters.ENTER:
			accept();
			return true;
		case Characters.ESCAPE:
			close();
			return true;
		}
		return super.keyChar(key, status, time);
	}
}
