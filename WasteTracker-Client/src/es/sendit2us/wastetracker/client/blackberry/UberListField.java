package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.ui.Keypad;
import net.rim.device.api.ui.component.ListField;

public class UberListField extends ListField {

	private boolean[] selectedPickups = null;
	
	public UberListField() {
	}
	
	public void setSize(int size) {
		super.setSize(size);
		if (size < 1) {
			selectedPickups = null;
		} else {
			selectedPickups = new boolean[size];
		}
	}
	
	public boolean isSelected(int idx) {
		return selectedPickups[idx];
	}
	
	public void setSelected(int idx) {
		selectedPickups[idx] = true;
	}
	
	public void clearSelection() {
		setSize(getSize());
	}
	
	public void toggleSelected() {
		doToggleSelected();
	}
	
	protected boolean keyDown(int keycode, int time) {
		int key = Keypad.key(keycode);

		switch(key) {
		case Keypad.KEY_ENTER:
		case Keypad.KEY_SPACE:
			return navigationClick(0, time);
		}
		return super.keyDown(keycode, time);
	}
	
	protected boolean navigationClick(int status, int time) {
		return (doToggleSelected() ? true : super.navigationClick(status, time));
	}
	
	protected boolean doToggleSelected() {
		int sel = getSelectedIndex();
		if (sel > -1) {
			selectedPickups[sel] = !selectedPickups[sel];
			invalidate(sel);
			return true;
		}
		return false;
	}
}
