package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.ui.component.ObjectChoiceField;
import es.sendit2us.wastetracker.client.blackberry.rest.WasteDescriptionEntity;

public class DescriptionChoiceField extends ObjectChoiceField {

	public DescriptionChoiceField(String label, WasteDescriptionEntity[] choices, int initialIndex, long style) {
		super(label, null, initialIndex, style);
		
		WasteDescriptionEntityDecorator[] newChoices = new WasteDescriptionEntityDecorator[choices.length];
		for (int i = 0; i < choices.length; i++) {
			WasteDescriptionEntity choice = choices[i];
			newChoices[i] = new WasteDescriptionEntityDecorator(choice.getCode(), choice.getDescription(), choice.getId());
		}
		
		setChoices(newChoices);
	}
}
