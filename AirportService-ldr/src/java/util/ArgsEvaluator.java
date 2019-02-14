package util;

import org.apache.log4j.Logger;

/**
 * Helper to evaluate the arguments in String[] args<br>
 */
public class ArgsEvaluator {
    private static final Logger LOG = Logger.getLogger(ArgsEvaluator.class.getName());

    /**
     * An invisible constructor to keep PMD happy. You can not create in instance of this class
     */
    private ArgsEvaluator() {
    }

    /**
     * Check if args contains the value val no matter of upper-/lowercase
     *
     * @param val  the value to check for
     * @param args the String array to check
     *
     * @return true if args contains val else false
     */
    public static boolean contains(String val, String[] args) {
        boolean ret = false;

        if (val == null || args == null) {
            throw new IllegalArgumentException();
        }

        for (int idx = 0; idx < args.length; ++idx) {
            if (args[idx].trim().toUpperCase().equals(val.toUpperCase())) {
                return true;
            }
        }
        return ret;
    }
}
