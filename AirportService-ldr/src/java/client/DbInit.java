package client;

import java.io.File;
import loader.AirportLoader;
import loader.FileAirportLoader;
import org.apache.log4j.Logger;
import util.ArgsEvaluator;

/**
 * Initialize database tables.
 */
public class DbInit {
    private static final Logger LOG = Logger.getLogger(DbInit.class.getName());

    /**
     * Initialize Airports if requested
     *
     * @param args the arguments to check if initialization is requested
     */
    private static void initAirports(String[] args) {
        if (args.length == 0 || ArgsEvaluator.contains("AIRPORTS", args)) {
            AirportLoader airportLoader = new FileAirportLoader(new File("./db/load_arpo.dat"));
            airportLoader.init();
        }
    }

    /**
     * Start DB initializing
     *
     * @param args the command line arguments to specify if initialization is requested
     */
    public static void main(String[] args) {
        LOG.info("Initialize DB...");
        initAirports(args);
        LOG.info("Finished");
    }
}
