/**
 * 
 */
package es.sendit2us.wastetracker.client.blackberry.screens;

import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.component.ListField;
import net.rim.device.api.ui.container.FullScreen;
import es.sendit2us.wastetracker.client.blackberry.Controller;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

/**
 * @author rodrigo
 *
 */
public abstract class AbstractPickupListScreen extends FullScreen {

	protected Controller controller;
	
	protected ListField pickupListField;
	protected PickupHeader[] roadmapCache;
	
	protected AbstractPickupListScreen(Controller controller) {
		super(Manager.VERTICAL_SCROLL);
		this.controller = controller;

		pickupListField = createListField();
		add(pickupListField);
	}

	public PickupHeader getSelectedPickup() {
		if (pickupListField.getSelectedIndex() < 0) {
			return null;
		}
		return roadmapCache[pickupListField.getSelectedIndex()];
	}
	
	public void refreshPickups() {
		this.roadmapCache = controller.getRoadmap();
		pickupListField.setSize(roadmapCache.length);
	}

	protected void registerIncidence() {
		controller.reportIncidence(this);
	}
	
	protected boolean keyDown(int keycode, int time) {
		int key = Keypad.key(keycode);

		switch (key) {
		case Keypad.KEY_ENTER:
		case Keypad.KEY_SPACE:
			accept();
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

	protected abstract void accept();

	protected abstract void showMenu();

	protected abstract ListField createListField();
}
