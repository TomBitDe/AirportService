package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import org.apache.log4j.Logger;

/**
 * Helper to calculate with DTTM format field content.
 */
public class DttmCalc {
    private static final Logger LOG = Logger.getLogger(DttmCalc.class);

    private DttmCalc() {
    }

    /**
     * Calculate datetime A minus datetime B.<br>
     * The datetime is a String in DTTM format "yyyyMMddHHmmss"<br>
     *
     * @param val1 first datetime
     * @param val2 second datetime
     *
     * @return the resulting milliseconds
     */
    public static long dttmDiff14(String val1, String val2) {
        long diff = 0;

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(val1));

            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(sdf.parse(val2));

            diff = cal1.getTimeInMillis() - cal2.getTimeInMillis();
            LOG.info(val1 + " - " + val2 + " = " + diff);

            return diff;
        }
        catch (ParseException pex) {
            LOG.error(pex.getMessage());
            return diff;
        }
    }

    /**
     * Calculate datetime A plus milliseconds.<br>
     * The datetime is a String in DTTM format "yyyyMMddHHmmss"<br>
     *
     * @param val   the datetime
     * @param milli milliseconds to add
     *
     * @return the resulting datetime in DTTM format
     */
    public static String dttmAdd14(String val, long milli) {
        String dttm = "";

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(val));

            long res = cal1.getTimeInMillis() + milli;

            Calendar cal2 = Calendar.getInstance();
            cal2.setTimeInMillis(res);
            dttm = sdf.format(cal2.getTime());
            LOG.info(val + " + " + milli + " = " + dttm);

            return dttm;
        }
        catch (ParseException pex) {
            LOG.error(pex.getMessage());
            return dttm;
        }
    }
}
