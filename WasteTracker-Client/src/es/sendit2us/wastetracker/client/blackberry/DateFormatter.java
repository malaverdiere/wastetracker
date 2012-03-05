package es.sendit2us.wastetracker.client.blackberry;

import java.util.Date;

import net.rim.device.api.i18n.DateFormat;
import net.rim.device.api.i18n.SimpleDateFormat;

public class DateFormatter {
	
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

	public static String formatDate(Date date) {
		return df.formatLocal(date.getTime());
	}
	
	public static String formatDate(long date) {
		return df.formatLocal(date);
	}
}
