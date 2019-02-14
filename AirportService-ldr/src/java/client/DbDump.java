package client;

import filedumper.AirportDumper;
import java.io.File;
import org.apache.log4j.Logger;
import util.ArgsEvaluator;

/**
 * Dump database tables to file.
 */
public class DbDump {
    private static final Logger LOG = Logger.getLogger(DbDump.class.getName());

    /**
     * Dump Airports if requested
     *
     * @param args the arguments to check if dump is requested
     */
    private static void dumpAirports(String[] args) {
        if (args.length == 0 || ArgsEvaluator.contains("AIRPORTS", args)) {
            AirportDumper airportDumper = new AirportDumper(new File("./db/load_arpo.dmp"));
            airportDumper.dump();
        }
    }

    /**
     * Start DB dumping
     *
     * @param args the command line arguments to specify if dump is requested
     */
    public static void main(String[] args) {
        LOG.info("Dump DB to file...");
        dumpAirports(args);
        LOG.info("Finished");
    }
}
