package es.sendit2us.wastetracker.client.blackberry.components;

import net.rim.device.api.i18n.ResourceBundleFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.component.LabelField;

public class ColorLabelField extends LabelField {
	
	private int color = -1;

	public ColorLabelField() {
		super();
	}

	public ColorLabelField(Object text, int offset, int length, long style) {
		super(text, offset, length, style);
	}

	public ColorLabelField(Object text, long style) {
		super(text, style);
	}

	public ColorLabelField(Object text) {
		super(text);
	}

	public ColorLabelField(ResourceBundleFamily rb, int key) {
		super(rb, key);
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	protected void paint(Graphics graphics) {
		if (color != -1) {
			graphics.setColor(color);
		}
		super.paint(graphics);
	}
}
