package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.GridFieldManager;
import es.sendit2us.wastetracker.client.blackberry.Controller;
import es.sendit2us.wastetracker.client.blackberry.Helper;
import es.sendit2us.wastetracker.client.blackberry.MasterData;
import es.sendit2us.wastetracker.client.blackberry.components.ColorLabelField;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupDetail;

public class PickupEditScreen extends PickupCertificationScreen {

	private FieldChangeListener buttonListener;

	public PickupEditScreen(Controller controller) {
		super(controller);
	}
	
	protected void certifyPickup() {
		if (Dialog.ask(Dialog.D_YES_NO, "Se va a proceder a revisar la recogida y generar el certificado correspondiente. "
				+ "Pasado este punto ya no se podrá modificar el destino. ¿Desea continuar?") == Dialog.YES) {
			controller.reviewPickup(this, pickup);
		}
	}

	protected GridFieldManager createItemsGrid(int rows, int cols) {
		final GridFieldManager grid = super.createItemsGrid(rows, cols + 1);
		buttonListener = new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				ButtonField buttonField = (ButtonField) field;
				int rowId = ((Integer)buttonField.getCookie()).intValue();
				System.out.println("Editando la fila " + rowId);
				
				DetailEditScreen editScreen = new DetailEditScreen(MasterData.wasteDescriptions, pickup.getDetail()[rowId]);
				UiApplication.getUiApplication().pushModalScreen(editScreen);
				ColorLabelField lbl = (ColorLabelField) grid.getFieldAtCell(rowId+1, 0);
				lbl.setColor(stateColors[pickup.getDetail()[rowId].getState()]);
				if (pickup.getDetail()[rowId].getState() == Helper.DETAIL_DELETED) {
					lbl.setFocus();
					grid.delete(buttonField);
				}
				grid.invalidate();
			}
		};
		return grid;
	}
	
	protected void addItemDetail(PickupDetail detail, GridFieldManager grid, int row) {
		super.addItemDetail(detail, grid, row);
		
		if (detail.getState() != Helper.DETAIL_DELETED) {
			ButtonField editButton = new ButtonField("Editar", ButtonField.CONSUME_CLICK);
			editButton.setCookie(new Integer(row-1)); // Items i-ésimo (row tiene uno más!)
			editButton.setChangeListener(buttonListener);
			grid.insert(editButton, row, 1, Field.FIELD_HCENTER);
		}
	}

	protected void createForm() {
		super.createForm();
	}
}
