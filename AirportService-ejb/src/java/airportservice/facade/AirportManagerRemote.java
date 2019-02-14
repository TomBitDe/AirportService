package airportservice.facade;

import airportservice.model.Airport;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote airport services.
 */
@Remote
public interface AirportManagerRemote {
    /**
     * Create an airport
     *
     * @param airport the airports master data
     *
     * @return the airport master data
     */
    Airport create(Airport airport);

    /**
     * Remove an airport by its id
     *
     * @param id the airports id
     *
     * @return the removed airport or null
     */
    Airport remove(final String id);

    /**
     * Update an airport
     *
     * @param airport the airport master data
     *
     * @return the airport
     */
    Airport update(Airport airport);

    /**
     * Get the content of airport entity (all airports in DB table)
     *
     * @return a list of all airports
     */
    List<AirportVO> getContent();

    /**
     * Get the content of airport entity starting at row offset fetching count rows
     *
     * @param offset the offset to start fetching
     * @param count  the number of rows to fetch
     *
     * @return a list of airports
     */
    List<AirportVO> getContent(int offset, int count);

    /**
     * Get an airport by its id (id)
     *
     * @param id the id (id) of the airport
     *
     * @return the matching airport
     */
    Airport getById(String id);

    /**
     * Get an airport by its iatacode
     *
     * @param icaocode the icaocode of the airport
     *
     * @return the matching airport
     */
    List<Airport> getByIcaocode(String icaocode);

    /**
     * Get the number of airports
     *
     * @return the number of airports
     */
    long countAirports();
}
