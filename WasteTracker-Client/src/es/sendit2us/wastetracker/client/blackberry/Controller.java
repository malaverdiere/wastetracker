package es.sendit2us.wastetracker.client.blackberry;

import net.rim.device.api.ui.container.FullScreen;
import es.sendit2us.wastetracker.client.blackberry.rest.PickupHeader;

public interface Controller {

	/**
	 * Usado por diferentes pantallas para conocer cu�l es el roadmap que el usuario ha seleccionado.
	 */
	public PickupHeader[] getRoadmap();
	
	/**
	 * Invocado por la pantalla de trayecto para abrir la pantalla de
	 * edici�n del punto de recogida.
	 */
	public void openPickup(FullScreen screen, PickupHeader pickup);
	
	/**
	 * Invocado por la pantalla de selecci�n del roadmap para proceder
	 * a bloquear los destinos e iniciar el trayecto.
	 */
	public void lockPickups(FullScreen screen, int[] selectedPickups);
	
	/**
	 * Si devuelve TRUE, un destino es eliminado del roadmap para que
	 * pueda ser usado por otro transportista.
	 */
	public void unlockPickup(FullScreen screen, PickupHeader pickup);
	
	/**
	 * Invocado por la pantalla de trayecto para reseleccionar puntos de recogida.
	 */
	public void reselectPickups(FullScreen screen, PickupHeader[] currentlySelectedPickups);

	/**
	 * Cancelaci�n general. Seg�n la pantalla invocadora, el controlador podr�
	 * realizar una u otra acci�n.
	 */
	public void cancel(FullScreen screen);	

	/**
	 * Usado por la pantalla de men� principal, para indicar al controlador qu� opci�n
	 * principal se ha pulsado.
	 */
	public void menuItemSelected(FullScreen screen, int idx);
	
	/**
	 * Usado para reportar una incidencia.
	 */
	public void reportIncidence(FullScreen screen);
	
	/**
	 * Usado para cerrar un destino.
	 */
	public void closePickup(FullScreen screen, PickupHeader pickup);
	
	/**
	 * Usado para revisar un destino antes de proceder al cierre.
	 */
	public void reviewPickup(FullScreen screen, PickupHeader pickup);
	
	/**
	 * Punto com�n para mostrar mensajes de error.
	 */
	public void error(String message);
}
