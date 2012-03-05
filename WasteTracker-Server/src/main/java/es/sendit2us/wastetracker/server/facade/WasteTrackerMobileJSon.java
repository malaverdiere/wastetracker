package es.sendit2us.wastetracker.server.facade;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import es.sendit2us.wastetracker.server.exceptions.WasteTrackerException;

@Path("/wastetracker")
public interface WasteTrackerMobileJSon {

	
	@GET @Path("/getUnassignedRequests")
	public String getUnassignedRequests();

	@GET @Path("/getAssignedRequests/{device}")
	public String getAssignedRequests(@PathParam("device") String device);

	@GET @Path("/assignRequests/{deviceCode}/{pickups}")
	public String assignRequests(
			@PathParam("deviceCode") String deviceCode,
			@PathParam("pickups") String pStr);

	@GET @Path("/unassignRequests/{deviceCode}/{p}")
	public void unassignRequests(@PathParam("deviceCode") String deviceCode, @PathParam("p") Integer p) throws WasteTrackerException;

	@GET @Path("/setupPickupRequests/{deviceCode}")
	public String setupPickupRequests(@PathParam("deviceCode") String device);

	@GET @Path("/ping")
	public String ping();

	@GET @Path("/registerIncidence/{imei}/{category}/{obs}")
	public void registerIncidence(@PathParam("imei") String imei, @PathParam("category") int category, @PathParam("obs") String obs) throws WasteTrackerException;

	@GET @Path("/getIncidenceCategories")
	public String getIncidenceCategories();

	@GET @Path("/getWasteDescriptionList")
	public String getWasteDescriptionList();

	@GET @Path("/registerIncidence/{pickupHeader}")
	public void closeRequest(@PathParam("pickupHeader") String pickupHeader) throws WasteTrackerException;

	@GET @Path("/addIncidence/{iemi}/{incidenceCode}/{description}")
	public void addIncidence(@PathParam("imei") String imei, @PathParam("incidenceCode") int incidenceCode, @PathParam("description") String description) throws WasteTrackerException;
}