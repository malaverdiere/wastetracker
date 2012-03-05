package es.sendit2us.wastetracker.data;

public final class PickupStatus {

	/* Estado en previsi�n de alg�n error futuro */
	public static final int ERROR = 0;
	
	/* Item disponible para asignaci�n */
	public static final int AVAIL = 10;
	
	/* Item bloqueado (asignado a m�) */
	public static final int LOCKED = 20;
	
	/* Item marcado como finalizado, a la espera de sincronizaci�n */
	public static final int CLOSED = 99;
}
