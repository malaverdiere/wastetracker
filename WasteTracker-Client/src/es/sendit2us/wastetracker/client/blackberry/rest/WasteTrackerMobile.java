// This class was generated by 172 StubGenerator.
// Contents subject to change without notice.
// @generated

package es.sendit2us.wastetracker.client.blackberry.rest;

import java.rmi.RemoteException;

public interface WasteTrackerMobile {
	public AssignResponse assignRequests(java.lang.String deviceCode, java.lang.String pickups) throws java.rmi.RemoteException;

	public PickupHeader[] getAssignedRequests(java.lang.String arg0) throws java.rmi.RemoteException;

	public CategoryIncidenceEntity[] getIncidenceCategories() throws java.rmi.RemoteException;

	public PickupHeader[] getUnassignedRequests() throws java.rmi.RemoteException;

	public WasteDescriptionEntity[] getWasteDescriptionList() throws java.rmi.RemoteException;

	public java.lang.String ping() throws java.rmi.RemoteException;

	public void registerIncidence(java.lang.String arg0, int arg1, java.lang.String arg2) throws java.rmi.RemoteException;

	public PickupHeader[] setupPickupRequests(java.lang.String arg0) throws java.rmi.RemoteException;

	public void unassignRequests(java.lang.String arg0, java.lang.Integer arg1) throws java.rmi.RemoteException;
}
