package es.sendit2us.wastetracker.data;

public final class PickupStatus {

	/* Estado en previsión de algún error futuro */
	public static final int ERROR = 0;
	
	/* Item disponible para asignación */
	public static final int AVAIL = 10;
	
	/* Item bloqueado (asignado a mí) */
	public static final int LOCKED = 20;
	
	/* Item marcado como finalizado, a la espera de sincronización */
	public static final int CLOSED = 99;
}
