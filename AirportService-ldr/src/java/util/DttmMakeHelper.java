package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.log4j.Logger;

/**
 * Helper to create DTTM format field content or Timestamp value.<br>
 * <br>
 * - DTTM format length 14 is: yyyyMMddHHmmss<br>
 * - DTTM format length 16 is: yyyyMMddHHmmssSS<br>
 * - DTTM format length 17 is: yyyyMMddHHmmssSSS<br>
 */
public class DttmMakeHelper {
    private static final Logger LOG = Logger.getLogger(DttmMakeHelper.class);

    private DttmMakeHelper() {
    }

    /**
     * Take the date and make a DTTM formated string length 14 of out of it.
     *
     * @param date the date
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm(Date date) {
        String ret;

        LOG.info("date=[" + date + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmss").format(date);

        LOG.info("ret=[" + ret + ']');
        return ret;
    }

    /**
     * Make a DTTM formated string length 14 of out of the current date and time.
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm() {
        return makeDttm(new Date());
    }

    /**
     * Take the date and make a DTTM formated string length 16 of out of it.
     *
     * @param date the date
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm16(Date date) {
        String ret;

        LOG.info("date=[" + date + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmssSS").format(date).substring(0, 16);

        LOG.info("ret=[" + ret + ']');
        return ret;
    }

    /**
     * Make a DTTM formated string length 16 of out of the current date and time.
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm16() {
        return makeDttm16(new Date());
    }

    /**
     * Take the date and make a DTTM formated string length 17 of out of it.
     *
     * @param date the date
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm17(Date date) {
        String ret;

        LOG.info("date=[" + date + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(date);

        LOG.info("ret=[" + ret + ']');
        return ret;
    }

    /**
     * Make a DTTM formated string length 17 of out of the current date and time.
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm17() {
        return makeDttm17(new Date());
    }

    /**
     * Take the timestamp and make a DTTM formated string length 14 of out of it.
     *
     * @param timestamp the timestamp
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm(Timestamp timestamp) {
        String ret;

        LOG.debug("timestamp=[" + timestamp + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmss").format(timestamp);

        LOG.debug("ret=[" + ret + ']');
        return ret;
    }

    /**
     * Take the timestamp and make a DTTM formated string length 16 of out of it.
     *
     * @param timestamp the timestamp
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm16(Timestamp timestamp) {
        String ret;

        LOG.debug("timestamp=[" + timestamp + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmssSS").format(timestamp);

        LOG.debug("ret=[" + ret + ']');

        return ret;
    }

    /**
     * Take the timestamp and make a DTTM formated string length 17 of out of it.
     *
     * @param timestamp the timestamp
     *
     * @return the corresponding DTTM string
     */
    public static String makeDttm17(Timestamp timestamp) {
        String ret;

        LOG.debug("timestamp=[" + timestamp + ']');
        ret = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(timestamp);

        LOG.debug("ret=[" + ret + ']');

        return ret;
    }

    /**
     * Take the DTTM string and calculate the corresponding milli seconds.
     *
     * @param val1 the DTTM string. Length can be 14, 16 or 17 characters
     *
     * @return the milli seconds
     */
    public static long getMillis(String val1) {
        long ret;

        try {
            SimpleDateFormat sdf;

            switch (val1.length()) {
                case 17:
                    sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                    break;
                case 16:
                    sdf = new SimpleDateFormat("yyyyMMddHHmmssSS");
                    break;
                default:
                    sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    break;
            }
            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(sdf.parse(val1));

            ret = cal1.getTimeInMillis();

            LOG.debug(val1 + " : " + ret);

            return cal1.getTimeInMillis();
        }
        catch (ParseException pex) {
            LOG.error(pex.getMessage());
            return 0;
        }
    }

    /**
     * Take the DTTM string and make a Timestamp out of it.
     *
     * @param val1 the DTTM string
     *
     * @return the corresponding Timestamp
     */
    public static Timestamp makeTimestamp(String val1) {
        Timestamp ret = new Timestamp(getMillis(val1));

        LOG.debug(val1 + " : " + ret);

        return ret;
    }
}
