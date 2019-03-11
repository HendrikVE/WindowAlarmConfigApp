package de.vanappsteer.windowalarmconfig.util;

import android.util.Log;

public class LoggingUtil {

    /*
    ALL PUBLIC METHODS MUST BE HANDLED DIRECTLY WITHOUT MOVING THEM IN TO ANOTHER ONE,
    BECAUSE getPrefixString() PRODUCES STACKTRACAE AND TAKES THIRD ENTRY
    */

    private static boolean IS_DEBUG = true;//BuildConfig.DEBUG;
    private static String LOGGING_TAG = "WindowAlarmConfig";//BuildConfig.loggingTag;


    //HAS TO BE CALLED AS SECOND, DIRECTLY AFTER ONE OF THE BELOW METHODS!!!
    private static String getPrefixString() {

        String prefix = "";

        //produce NullPointerException to get stacktrace
        Object object = null;
        try {
            object.toString();
        }
        catch (NullPointerException e) {

            StackTraceElement stackTrace = e.getStackTrace()[2];
            prefix = stackTrace.getClassName() + " @ " + stackTrace.getMethodName() + "(): ";
        }

        return prefix;
    }

    /*
    DEBUG
     */
    public static void debug(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.d(LOGGING_TAG, prefix + message);
    }

    /*
    WARNING
     */
    public static void warning(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.w(LOGGING_TAG, prefix + ": "+message);
    }

    /*
    ERROR
     */
    public static void error(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.e(LOGGING_TAG, prefix + ": "+message);
    }

    /*
    VERBOSE
     */
    public static void verbose(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.v(LOGGING_TAG, prefix + ": "+message);
    }

    /*
    INFO
     */
    public static void info(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.i(LOGGING_TAG, prefix + ": "+message);
    }

    /*
    WTF
     */
    public static void wtf(String message) {

        if( !IS_DEBUG) {
            return;
        }

        String prefix = getPrefixString();
        Log.wtf(LOGGING_TAG, prefix + ": "+message);
    }

}
