package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.component.TextField;
import net.rim.device.api.ui.container.GridFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import es.sendit2us.wastetracker.client.blackberry.Controller;
import es.sendit2us.wastetracker.client.blackberry.DateFormatter;
import es.sendit2us.wastetracker.client.blackberry.components.ColorLabelField;
import es.sendit2us.wastetracker.client.blackberry.menu.CancelMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.ClosePickupMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.IncidenceMenuItem;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupDetail;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

public class PickupCertificationScreen extends MainScreen {

	private static float HEADER_FONT_MULTIPLIER = 0.6f;
	private static float ITEM_FONT_MULTIPLIER = 0.8f;

	private static Font headerBoldFont = Font.getDefault().derive(Font.BOLD, (int) (Font.getDefault().getHeight() * HEADER_FONT_MULTIPLIER));
	private static Font headerNormalFont = Font.getDefault().derive(Font.PLAIN, (int) (Font.getDefault().getHeight() * HEADER_FONT_MULTIPLIER));
	private static Font detailBoldFont = Font.getDefault().derive(Font.BOLD, (int) (Font.getDefault().getHeight() * ITEM_FONT_MULTIPLIER));
	private static Font detailNormalFont = Font.getDefault().derive(Font.PLAIN, (int) (Font.getDefault().getHeight() * ITEM_FONT_MULTIPLIER));
	
	private FieldChangeListener certifyListener;

	private LabelField gridTitle;
	private GridFieldManager headerGrid;
	private GridFieldManager itemsGrid;
	private TextField id;
	private TextField name;
	private TextField address;
	private TextField date;

	protected Controller controller;
	protected PickupHeader pickup;
	protected int[] stateColors = {-1, Color.RED, Color.BLUE, Color.DARKCYAN};

	public PickupCertificationScreen(Controller controller) {
		super(Manager.VERTICAL_SCROLL | Field.USE_ALL_HEIGHT);
		this.controller = controller;
		createForm();
	}

	public void setPickupHeader(PickupHeader pickupHeader) {
		this.pickup = pickupHeader;
		
		id.setText("" + pickup.getId());
		name.setText(pickup.getName());
		address.setText(pickup.getAddress());
		date.setText(DateFormatter.formatDate(pickup.getPickupDate()));

		headerGrid.setDirty(true);

		updatePickupItemList();
	}

	protected boolean keyDown(int keycode, int time) {
		int key = Keypad.key(keycode);

		switch (key) {
		case Keypad.KEY_ENTER:
		case Keypad.KEY_SPACE:
			// accept();
			return true;
		case Keypad.KEY_ESCAPE:
			cancel();
			return true;
		case Keypad.KEY_MENU:
			showMenu();
			return true;
		}
		return super.keyDown(keycode, time);
	}

	protected void cancel() {
		controller.cancel(this);
	}
	
	protected void registerIncidence() {
		controller.reportIncidence(this);
	}
	
	protected void certifyPickup() {
		controller.closePickup(this, pickup);
	}

	protected void showMenu() {
		Menu menu = new Menu();

		menu.add(new ClosePickupMenuItem() {
			public void run() {
				certifyPickup();
			}
		});
		menu.addSeparator();
		menu.add(new CancelMenuItem() {
			public void run() {
				cancel();
			}
		});
		menu.addSeparator();
		menu.add(new IncidenceMenuItem() {
			public void run() {
				registerIncidence();
			}
		});
		
		menu.show();
	}

	protected void updatePickupItemList() {
		if (itemsGrid != null) {
			itemsGrid.delete(gridTitle);
			delete(itemsGrid);
		}

		PickupDetail[] itemDetails = pickup.getDetail();
		itemsGrid = createItemsGrid(itemDetails.length + 3, 1);
		
		itemsGrid.add(gridTitle, Field.USE_ALL_WIDTH | Field.NON_FOCUSABLE);
		
		for (int i = 0; i < itemDetails.length; i++) {
			addItemDetail(itemDetails[i], itemsGrid, i+1);
		}
		itemsGrid.insert(new SeparatorField(SeparatorField.LINE_HORIZONTAL), itemDetails.length + 1, 0);

		/* Botón de certificación */
		ButtonField certifyButton = new ButtonField("Certificar", ButtonField.CONSUME_CLICK);
		certifyButton.setChangeListener(certifyListener);
		itemsGrid.insert(certifyButton, itemDetails.length + 2, 0, Field.FIELD_HCENTER);
		
		add(itemsGrid);
		itemsGrid.setFocus();
	}
	
	protected GridFieldManager createItemsGrid(int rows, int cols) {
		GridFieldManager grid = new GridFieldManager(rows, cols, Field.USE_ALL_WIDTH | Field.USE_ALL_HEIGHT);
		grid.setFont(detailNormalFont);
		grid.setColumnProperty(0, GridFieldManager.AUTO_SIZE, 0);
		grid.setColumnPadding(8);
		return grid;
	}
	
	protected void addItemDetail(PickupDetail detail, GridFieldManager grid, int row) {
		ColorLabelField lbl = new ColorLabelField(detail.getCategoryDesc(), DrawStyle.LEFT | Field.FOCUSABLE);
		lbl.setColor(stateColors[detail.getState()]);
		grid.insert(lbl, row, 0, Field.FIELD_LEFT);
	}

	protected void createForm() {
		headerGrid = new GridFieldManager(5, 2, Field.USE_ALL_WIDTH);
		headerGrid.setColumnProperty(1, GridFieldManager.AUTO_SIZE, 0);
		headerGrid.setColumnPadding(8);
		id = createRow(headerGrid, "Código:");
		name = createRow(headerGrid, "Solicitante:");
		address = createRow(headerGrid, "Contacto:");
		date = createRow(headerGrid, "Fecha:");
		setTitle(headerGrid);

		gridTitle = new LabelField("Residuo");
		gridTitle.setFont(detailBoldFont);

		certifyListener = new FieldChangeListener() {
			public void fieldChanged(Field field, int context) {
				certifyPickup();
			}
		};
	}

	protected TextField createRow(GridFieldManager grid, String label) {
		LabelField labelField = new LabelField(label);
		labelField.setFont(headerBoldFont);

		TextField textField = new RichTextField(Field.READONLY | Field.NON_FOCUSABLE);
		textField.setFont(headerNormalFont);
		grid.add(labelField, Field.FIELD_RIGHT);
		grid.add(textField, Field.USE_ALL_WIDTH);
		return textField;
	}
}
