package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.component.ListFieldCallback;
import net.rim.device.api.ui.component.Menu;
import es.sendit2us.wastetracker.client.blackberry.Controller;
import es.sendit2us.wastetracker.client.blackberry.menu.CancelMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.DiscardPickupMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.IncidenceMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.ReselectPickupMenuItem;
import es.sendit2us.wastetracker.client.blackberry.menu.SelectPickupMenuItem;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

public class RoadmapScreen extends AbstractPickupListScreen {

	private int rowHeightCache;

	public RoadmapScreen(Controller controller) {
		super(controller);
//		refreshPickups();
	}

	protected void discardSelected() {
		if (Dialog.ask(Dialog.D_YES_NO, "¿Desea descartar este punto de recogida del recorrido?"
				+ " Si continúa, se desbloqueará y podrá ser usado por otro transportista.") == Dialog.YES) {
			PickupHeader pickup = getSelectedPickup();
			//TODO: Revisar que pasa en caso de error
			controller.unlockPickup(this, pickup);
			refreshPickups();			
		}
	}

	protected void reselectPickups() {
		controller.reselectPickups(this, roadmapCache);
	}

	protected void accept() {
		controller.openPickup(this, getSelectedPickup());
	}

	protected void showMenu() {
		Menu menu = new Menu();

		if (roadmapCache.length > 0) {
			menu.add(new SelectPickupMenuItem() {
				public void run() {
					accept();
				}
			});

			menu.add(new DiscardPickupMenuItem() {
				public void run() {
					discardSelected();
				}
			});

			menu.addSeparator();
		}
		
		menu.add(new ReselectPickupMenuItem() {
			public void run() {
				reselectPickups();
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

	protected boolean navigationClick(int status, int time) {
		int sel = pickupListField.getSelectedIndex();
		if (sel > -1) {
			accept();
			return true;
		}
		return super.navigationClick(status, time);
	}

	protected ListField createListField() {
		final ListField list = new ListField();
		list.setEmptyString("No hay destinos seleccionados.", DrawStyle.LEFT);

		rowHeightCache = list.getRowHeight();
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
				PickupHeader dest = roadmapCache[index];
				graphics.drawText("" + dest.getId() + " - " + dest.getName(), 0, y, 0, width);
				graphics.drawText("" + dest.getAddress(), 0, y + rowHeightCache, 0, width);
			}
		});

		return list;
	}
}
