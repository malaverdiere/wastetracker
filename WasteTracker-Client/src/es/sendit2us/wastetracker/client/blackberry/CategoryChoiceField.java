package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.ui.component.ObjectChoiceField;
import es.sendit2us.wastetracker.client.blackberry.rest.CategoryIncidenceEntity;

public class CategoryChoiceField extends ObjectChoiceField {

	public CategoryChoiceField(String label, CategoryIncidenceEntity[] choices, int initialIndex, long style) {
		super(label, null, initialIndex, style);
		
		CategoryIncidenceEntityDecorator[] newChoices = new CategoryIncidenceEntityDecorator[choices.length];
		for (int i = 0; i < choices.length; i++) {
			CategoryIncidenceEntity choice = choices[i];
			newChoices[i] = new CategoryIncidenceEntityDecorator(choice.getCode(), choice.getDescription(), choice.getId());
		}
		
		setChoices(newChoices);
	}
}
