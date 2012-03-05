package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.Menu;
import es.sendit2us.wastetracker.client.blackberry.Controller;
import es.sendit2us.wastetracker.client.blackberry.Helper;
import es.sendit2us.wastetracker.client.blackberry.UberListField;
import es.sendit2us.wastetracker.client.blackberry.menu.AcceptMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.CancelMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.IncidenceMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.SelectPickupMenuItem;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

public class PickupSelectionScreen extends AbstractPickupListScreen {

	private int rowHeightCache;
	private int rbOffsetCache;
	private int rbWidthCache;
	
	private Bitmap rbChecked;
	private Bitmap rbUnchecked;
	private Menu menu;

	public PickupSelectionScreen(Controller controller) {
		super(controller);
		
		// El menú de esta pantalla no depende de los datos disponibles.
		createImmutableMenu();
	}
	
	public void setAvailablePickups(PickupHeader[] allPickups) {
		// Refresh roadmap cache by newly loaded pickups
		PickupHeader[] oldCache = controller.getRoadmap();
		roadmapCache = allPickups;
		pickupListField.setSize(allPickups.length);
		
		for (int i = 0; i < allPickups.length; i++) {
			if (Helper.indexOf(oldCache, allPickups[i].getId()) >= 0) {
				((UberListField) pickupListField).setSelected(i);
			}
		}
	}
	
	public void refreshPickups() {
		super.refreshPickups();
		
		for (int i = 0; i < roadmapCache.length; i++) {
			if (roadmapCache[i].getDeviceCode() != null) {
				((UberListField) pickupListField).setSelected(i);
			}
		}
	}
	
	protected void accept() {
		boolean proceed = true;
		int[] selectedPickups = getSelectedPickups();
		if (selectedPickups.length == 0) {
			proceed = (Dialog.ask(Dialog.D_YES_NO, "No ha seleccionado ningún punto de recogida. " +
					"La hoja de ruta quedará vacía. ¿Desea continuar?") == Dialog.YES);
		}

		if (proceed) {
			controller.lockPickups(this, selectedPickups);
		}
	}
	
	protected void showMenu() {
		menu.show();
	}
	
	/**
	 * Esta es la única pantalla en la que "ESCAPE" sirve para aceptar.
	 */
	protected boolean keyDown(int keycode, int time) {
		if (Keypad.key(keycode) == Keypad.KEY_ESCAPE) {
			accept();
			return true;
		}
		return super.keyDown(keycode, time);
	}
	
	/**
	 * Se devuelven los IDs por una razón de rendimiento: con la lista de destinos seleccionada se
	 * llama al servidor para reservarlos y para ello es necesario cada ID. El servidor devolverá la
	 * lista de cabeceras de nuevo.
	 */
	private int[] getSelectedPickups() {
		int totalSelected = 0;
		for (int i = 0; i < roadmapCache.length; i++) {
			if (((UberListField) pickupListField).isSelected(i)) {
				totalSelected++;
			}
		}
		
		int[] selectedPickups = new int[totalSelected];
		for (int i = 0, j = 0; i < roadmapCache.length; i++) {
			if (((UberListField) pickupListField).isSelected(i)) {
				selectedPickups[j] = roadmapCache[i].getId();
				j++;
			}
		}
		return selectedPickups;
	}
	
	protected ListField createListField() {
		final UberListField list = new UberListField();
		list.setEmptyString("No hay puntos de recogida disponibles.", DrawStyle.LEFT);

		rbChecked = EncodedImage.getEncodedImageResource("rb_checked.png").getBitmap();
		rbUnchecked = EncodedImage.getEncodedImageResource("rb_unchecked.png").getBitmap();
		
		rowHeightCache = list.getRowHeight();
		if (rowHeightCache > rbChecked.getHeight()) {
			rbOffsetCache = (rowHeightCache - rbChecked.getHeight()) / 2;
			rbWidthCache = rowHeightCache;
		} else {
			rbOffsetCache = 0;
			rbWidthCache = rbChecked.getHeight();
		}
		
		list.setRowHeight(rowHeightCache * 2);
		list.setCallback(new ListFieldCallback() {
			
			public int indexOfList(ListField listField, String prefix, int start) {
				return listField.indexOfList(prefix, start);
			}
			
			public int getPreferredWidth(ListField listField) {
				return Display.getWidth();
			}
			
			public Object get(ListField listField, int index) {
				return roadmapCache[index];
			}
			
			public void drawListRow(ListField listField, Graphics graphics, int index, int y, int width) {
				Bitmap rb = (list.isSelected(index) ? rbChecked : rbUnchecked);
				graphics.drawBitmap(rbOffsetCache, y + rbOffsetCache, rb.getHeight(), rb.getHeight(), rb, 0, 0);
				
				PickupHeader pickup = roadmapCache[index];
				graphics.drawText("" + pickup.getId() + " - " + pickup.getName(), rbWidthCache, y, 0, width);
		      graphics.drawText("" + pickup.getAddress(),
		      		rbWidthCache, y + rowHeightCache, 0, width);
			}
		});
		
		return list;
	}
	
	private void createImmutableMenu() {
		this.menu = new Menu();
		menu.add(new SelectPickupMenuItem() {
			public void run() {
				((UberListField) pickupListField).toggleSelected();
			}
		});
		menu.addSeparator();
		menu.add(new IncidenceMenuItem() {			
			public void run() {
				registerIncidence();
			}
		});
		menu.addSeparator();
		menu.add(new AcceptMenuItem() {
			public void run() {
				accept();
			}
		});
		menu.add(new CancelMenuItem() {
			public void run() {
				cancel();
			}
		});
	}
}
