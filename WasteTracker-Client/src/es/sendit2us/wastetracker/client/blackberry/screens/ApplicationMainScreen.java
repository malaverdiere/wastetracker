package es.sendit2us.wastetracker.client.blackberry.screens;

/**
 * PictureScrollFieldDemoScreen.java
 *
 * Copyright © 1998-2009 Research In Motion Ltd.
 * 
 * Note: For the sake of simplicity, this sample application may not leverage
 * resource bundles and resource strings.  However, it is STRONGLY recommended
 * that application developers make use of the localization features available
 * within the BlackBerry development platform to ensure a seamless application
 * experience across a variety of languages and geographies.  For more information
 * on localizing your application, please refer to the BlackBerry Java Development
 * Environment Development Guide associated with this release.
 */

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.EncodedImage;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.TouchEvent;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.decor.Background;
import net.rim.device.api.ui.decor.BackgroundFactory;
import net.rim.device.api.ui.extension.component.PictureScrollField;
import net.rim.device.api.ui.extension.component.PictureScrollField.HighlightStyle;
import net.rim.device.api.ui.extension.component.PictureScrollField.ScrollEntry;
import es.sendit2us.wastetracker.client.blackberry.Controller;

/**
 * The main screen class for the the Picture Scroll Field Demo application
 */
public final class ApplicationMainScreen extends MainScreen {

	private Controller controller;
	private PictureScrollField scrollField;

	/**
	 * Creates a new PictureScrollFieldDemoScreen object
	 */
	public ApplicationMainScreen(Controller controller) {
		super(Field.FIELD_VCENTER | Field.USE_ALL_HEIGHT);
		this.controller = controller;
		setTitle("Waste Tracker v1.2");
		getMainManager().setBackground(BackgroundFactory.createSolidBackground(0));

		// Initialize an array of scroll entries
		ScrollEntry[] entries = new ScrollEntry[3];
		entries[0] = new ScrollEntry(Bitmap.getBitmapResource("list.png"), "Abrir/Crear una hoja de ruta", null);
		entries[1] = new ScrollEntry(Bitmap.getBitmapResource("reload.png"), "Sincronizar los datos maestros", null);
		entries[2] = new ScrollEntry(Bitmap.getBitmapResource("wizard.png"), "Operaciones de almacén", null);

		// Initialize the picture scroll field
		scrollField = new PictureScrollField(96, 96);
		scrollField.setData(entries, 0);
		scrollField.setHighlightStyle(HighlightStyle.ILLUMINATE_WITH_MAGNIFY_LENS);
		scrollField.setHighlightBorderColor(Color.RED);
		Bitmap background = EncodedImage.getEncodedImageResource("mainbar.png").getBitmap();
		scrollField.setBackground(BackgroundFactory.createBitmapBackground(background,
					Background.POSITION_X_CENTER, Background.POSITION_Y_CENTER, Background.REPEAT_SCALE_TO_FIT));
		scrollField.setCenteredLens(true);
		scrollField.setLabelsVisible(true);
		add(scrollField);
	}
	
	public boolean onClose() {
		if (Dialog.ask(Dialog.D_YES_NO, "¿Desea salir de la aplicación?") == Dialog.YES) {
			return super.onClose();
		}
		return false;
	}

	protected boolean navigationClick(int status, int time) {
		if (scrollField.isFocus()) {
			menuEvent(scrollField.getCurrentImageIndex());
			return true;
		}

		return super.navigationClick(status, time);
	}

	protected boolean touchEvent(TouchEvent message) {
		if (message.getEvent() == TouchEvent.CLICK) {
			if (scrollField.isFocus()) {
				menuEvent(scrollField.getCurrentImageIndex());
				return true;
			}
		}

		return super.touchEvent(message);
	}
	
	protected void menuEvent(int idx) {
		controller.menuItemSelected(this, idx);
	}
}
