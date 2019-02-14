package filedumper;

import airportservice.facade.AirportManagerRemote;
import airportservice.facade.AirportVO;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import org.apache.log4j.Logger;
import util.BeanLocator;
import util.DttmMakeHelper;

/**
 * Airport file dumper.<br>
 * <br>
 * Save the content of entity airport in a file.
 */
public class AirportDumper {
    private static final Logger LOG = Logger.getLogger(AirportDumper.class.getName());
    private final AirportManagerRemote AIRPORT_FACADE = BeanLocator.lookup(AirportManagerRemote.class, "airportservice.facade.AirportManagerRemote");
    private File path;
    private static final int FETCH_LIMIT = 100;
    private int offset = 0;

    /**
     * Creates a new instance of AirportDumper
     *
     * @param path the path and file of the destination to dump the content of the entity
     */
    public AirportDumper(File path) {
        this.path = path;
    }

    /**
     * Dump the content of entity airport to file
     */
    public void dump() {
        String line;
        int count = 0;
        PrintWriter out = null;

        LOG.info("Dump Airports in DB to file [" + path + ']');

        try {
            out = new PrintWriter(path);

            for (;;) {
                List<AirportVO> airportList = AIRPORT_FACADE.getContent(offset, FETCH_LIMIT);

                if (airportList.isEmpty()) {
                    break;
                }

                for (AirportVO airport : airportList) {
                    airport.setDescr(airport.getDescr().replaceAll("''", "'"));

                    if (airport.getDescr().length() > 30) {
                        airport.setDescr(airport.getDescr().substring(0, 29));
                    }

                    line = String.format("%-3s|%-4s|%-30s|%-14s|%-14s|%-14s|%-14s",
                                         airport.getIatacode().replaceAll("''", "'"),
                                         airport.getIcaocode().replaceAll("''", "'").replaceAll("\t", ""),
                                         airport.getDescr(),
                                         AirportVO.convert2String(DttmMakeHelper.makeTimestamp(airport.getCreated()), "yyyyMMddHHmmss"),
                                         airport.getCreauser().replaceAll("''", "'"),
                                         AirportVO.convert2String(DttmMakeHelper.makeTimestamp(airport.getUpdated()), "yyyyMMddHHmmss"),
                                         airport.getUpdtuser().replaceAll("''", "'"));

                    out.println(line);

                    ++count;
                }
                offset += FETCH_LIMIT;
            }
            LOG.info("Finished: [" + count + "] AIRPORTS dumped");
        }
        catch (IOException ioex) {
            LOG.fatal(ioex.getMessage());
        }
        finally {
            if (out != null) {
                out.close();
            }
        }
    }

    /**
     * Get the dump destination
     *
     * @return the path and file of the destination to dump the content of the entity
     */
    public File getPath() {
        return path;
    }

    /**
     * Set the dump destination
     *
     * @param path the path and file of the destination to dump the content of the entity
     */
    public void setPath(File path) {
        this.path = path;
    }
}
