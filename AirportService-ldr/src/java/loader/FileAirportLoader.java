package loader;

import airportservice.facade.AirportManagerRemote;
import airportservice.model.Airport;
import java.io.*;
import org.apache.log4j.Logger;
import util.BeanLocator;
import util.DttmMakeHelper;

/**
 * Airport DB table loader.<br>
 * <br>
 * Initialize the airport entity by loading the master data from a file.
 */
public class FileAirportLoader implements AirportLoader {
    private static final Logger LOG = Logger.getLogger(FileAirportLoader.class.getName());
    private static final AirportManagerRemote AIRPORT_FACADE = BeanLocator.lookup(AirportManagerRemote.class, "airportservice.facade.AirportManagerRemote");
    private File path;

    /**
     * Creates a new instance of AirportLoader
     *
     * @param path the path and file of the destination to load the master data from
     */
    public FileAirportLoader(File path) {
        this.path = path;
    }

    /**
     * Initialize the airport entity
     */
    @Override
    public void init() {
        BufferedReader in = null;
        String line;
        int count = 0;

        LOG.info("Initialize basic Airports in DB from file [" + path + ']');

        try {
            in = new BufferedReader(new FileReader(path));

            while ((line = in.readLine()) != null) {
                LOG.debug(line);

                Airport removedAirport = AIRPORT_FACADE.remove(parseAirport(line).getIatacode());
                LOG.debug("Removed Airport = " + removedAirport);

                AIRPORT_FACADE.create(parseAirport(line));
                ++count;
            }
            LOG.info("Finished: [" + count + "] AIRPORTS loaded");
        }
        catch (IOException ioex) {
            LOG.fatal("An IO error occured. The error message is: " + ioex.getMessage());
        }
        catch (Exception ex) {
            LOG.fatal("A GENERAL error occured. The error message is: " + ex.getMessage());
        }
        finally {
            if (in != null) {
                try {
                    in.close();
                }
                catch (IOException e) {
                }
            }
        }
    }

    /**
     * Get the destination
     *
     * @return the path and file of the destination to load master data from
     */
    public File getPath() {
        return path;
    }

    /**
     * Set the destination
     *
     * @param path the path and file of the destination to load master data from
     */
    public void setPath(File path) {
        this.path = path;
    }

    /**
     * Create an airport object using the content from the file line
     *
     * @param line the content line read from file
     *
     * @return the airport object
     */
    private Airport parseAirport(String line) {
        Airport airport = new Airport(
                line.substring(0, 3).replaceAll("'", "''").trim(),
                "",
                line.substring(4, 34).replaceAll("'", "''").trim(),
                DttmMakeHelper.makeTimestamp(line.substring(35, 49)),
                line.substring(50, 64).replaceAll("'", "''").trim(),
                DttmMakeHelper.makeTimestamp(line.substring(65, 79)),
                line.substring(80, 94).replaceAll("'", "''").trim());

        LOG.debug(airport.toString());

        return airport;
    }
}
