package service;

import airportservice.facade.AirportListVO;
import airportservice.facade.AirportManagerRemote;
import airportservice.facade.AirportVO;
import airportservice.model.Airport;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * RESTful Airport Service
 */
@Path("/AirportService")
@Stateless
public class AirportService {
    @EJB
    AirportManagerRemote airportMangerRemote;

    /**
     * Get a list of all airports.
     *
     * @param offset the position to start fetching
     * @param count  the number of fetches to do
     *
     * @return the airport list based on offset and count
     */
    @PermitAll
    @GET
    @Path("/airports/{offset}/{count}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getAll(@PathParam("offset") String offset, @PathParam("count") String count) {
        int intOffset = Integer.valueOf(offset);
        int intCount = Integer.valueOf(count);

        // Get the AirportVOs from the remote call as a List
        List<AirportVO> airportList = airportMangerRemote.getContent(intOffset, intCount);

        GenericEntity<List<AirportVO>> entity
                = new GenericEntity<List<AirportVO>>(new ArrayList(airportList)) {
        };

        Response response = Response.ok(entity).build();

        return response;
    }

    /**
     * Get an airport by its id (iatacode).
     *
     * @param id the id (iatacode) of the airport
     *
     * @return the matching airport
     */
    @PermitAll
    @GET
    @Path("/id/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getById(@PathParam("id") String id) {
        Airport airport = airportMangerRemote.getById(id);

        AirportVO airportVO = null;
        Response response;

        if (airport != null) {
            airportVO = new AirportVO(airport.getIatacode(), airport.getIcaocode(),
                                      airport.getDescr(), AirportVO.convert2String(airport.getCreated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      airport.getCreauser(), AirportVO.convert2String(airport.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      airport.getUpdtuser());
        }
        response = Response.ok().entity(airportVO).build();

        return response;
    }

    /**
     * Check if an airport exists by its id (iatacode).
     *
     * @param id the id (iatacode) of the airport
     *
     * @return true if the airport exists otherwise false
     */
    @PermitAll
    @GET
    @Path("/exists/{id}")
    @Produces({MediaType.APPLICATION_XML})
    public Response exists(@PathParam("id") String id) {
        Airport airport = airportMangerRemote.getById(id);

        if (airport != null) {
            return Response.ok().entity("true").build();
        }
        return Response.ok().entity("false").build();

    }

    /**
     * Get an airport by its icaocode.
     *
     * @param icaocode the icaocode of the airport
     *
     * @return the matching airport
     */
    @PermitAll
    @GET
    @Path("/icao/{icaocode}")
    @Produces({MediaType.APPLICATION_XML})
    public Response getByIcaocode(@PathParam("icaocode") String icaocode) {
        List<Airport> airportList = airportMangerRemote.getByIcaocode(icaocode);

        AirportListVO airportListVO = null;
        Response response;

        if (airportList != null) {
            List<AirportVO> airports = new ArrayList();
            airportListVO = new AirportListVO();

            for (Airport airport : airportList) {
                AirportVO airportVO = new AirportVO(airport.getIatacode(), airport.getIcaocode(),
                                                    airport.getDescr(), AirportVO.convert2String(airport.getCreated(), "yyyy-MM-dd HH:mm:ss.S"),
                                                    airport.getCreauser(), AirportVO.convert2String(airport.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"),
                                                    airport.getUpdtuser());
                airports.add(airportVO);
            }
            airportListVO.setAirportList(airports);
        }

        response = Response.ok().entity(airportListVO).build();

        return response;
    }

    /**
     * Count the airports.
     *
     * @return the number of airports
     */
    @PermitAll
    @GET
    @Path("/count")
    @Produces({MediaType.APPLICATION_XML})
    public Response count() {
        long val = airportMangerRemote.countAirports();

        Response response;

        response = Response.ok().entity(String.valueOf(val)).build();

        return response;
    }

    /**
     * Delete an airport by iatacode.
     *
     * @param iatacode the iatacode of the airport to delete
     *
     * @return the data of the removed airport
     */
    @DELETE
    @Path("/airport/{iatacode}")
    @Produces({MediaType.APPLICATION_XML})
    public Response delete(@PathParam("iatacode") String iatacode) {
        Airport removedAirport = airportMangerRemote.remove(iatacode);

        AirportVO airportVO = null;
        Response response;

        if (removedAirport != null) {
            airportVO = new AirportVO(removedAirport.getIatacode(), removedAirport.getIcaocode(),
                                      removedAirport.getDescr(), AirportVO.convert2String(removedAirport.getCreated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      removedAirport.getCreauser(), AirportVO.convert2String(removedAirport.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      removedAirport.getUpdtuser());
        }
        response = Response.ok().entity(airportVO).build();

        return response;
    }

    /**
     * Create an airport.
     *
     * @param iatacode the iatacode of the airport
     * @param icaocode the icaocode of the airport
     * @param descr    a description or comment for the airport
     *
     * @return the data of the created airport
     */
    @PermitAll
    @PUT
    @Path("/airports/{iatacode}/{icaocode}/{descr}")
    @Produces({MediaType.APPLICATION_XML})
    public Response create(@PathParam("iatacode") String iatacode,
                           @PathParam("icaocode") String icaocode,
                           @PathParam("descr") String descr) {

        Airport createdAirport = airportMangerRemote.create(new Airport(iatacode, icaocode, descr, null, null));

        AirportVO airportVO = null;
        Response response;

        if (createdAirport != null) {
            airportVO = new AirportVO(createdAirport.getIatacode(), createdAirport.getIcaocode(),
                                      createdAirport.getDescr(), AirportVO.convert2String(createdAirport.getCreated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      createdAirport.getCreauser(), AirportVO.convert2String(createdAirport.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      createdAirport.getUpdtuser());
        }
        response = Response.ok().entity(airportVO).build();

        return response;
    }

    /**
     * Update airport data.
     *
     * @param iatacode the iatacode of the airport to update
     * @param icaocode the updated icaocode of the airport
     * @param descr    an updated description or comment for the airport
     *
     * @return the data of the updated airport
     */
    @PermitAll
    @POST
    @Path("/airports/{iatacode}/{icaocode}/{descr}")
    @Produces({MediaType.APPLICATION_XML})
    public Response update(@PathParam("iatacode") String iatacode,
                           @PathParam("icaocode") String icaocode,
                           @PathParam("descr") String descr) {

        Airport updatedAirport = airportMangerRemote.update(new Airport(iatacode, icaocode, descr, null, null));

        AirportVO airportVO = null;
        Response response;

        if (updatedAirport != null) {
            airportVO = new AirportVO(updatedAirport.getIatacode(), updatedAirport.getIcaocode(),
                                      updatedAirport.getDescr(), AirportVO.convert2String(updatedAirport.getCreated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      updatedAirport.getCreauser(), AirportVO.convert2String(updatedAirport.getUpdated(), "yyyy-MM-dd HH:mm:ss.S"),
                                      updatedAirport.getUpdtuser());
        }
        response = Response.ok().entity(airportVO).build();

        return response;
    }

    /**
     * Give a list of all supported service operations
     *
     * @return a list of service operations
     */
    @OPTIONS
    @Produces({MediaType.TEXT_PLAIN})
    public String getSupportedOperations() {
        return "GET, DELETE, PUT, POST";
    }
}
