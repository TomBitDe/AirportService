package airportservice.manager;

import airportservice.model.Airport;
import java.util.List;
import javax.ejb.Local;

/**
 * Local airport services.<br>
 * <br>
 * Use % and _ for pattern matching e.g. getByIatacode("F%") or getByIatacode("F_").
 */
@Local
public interface AirportManagerLocal {
    /**
     * Get all airports
     *
     * @return a list of all airports
     */
    List<Airport> getAirports();

    /**
     * Check if a given airport exists by its id
     *
     * @param id the airports id
     *
     * @return true if the airport exists else false
     */
    boolean exists(final String id);
}
