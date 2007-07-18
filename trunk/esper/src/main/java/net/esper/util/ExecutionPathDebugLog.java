package net.esper.util;

/**
 * Utility class that control debug-level logging in the execution path
 * beyond which is controlled by Log4j.
 * <p>
 * As Log4j will return true to the "log.isDebugEnabled()" call when
 * there is no log4j configuration, this leaves the door open to poor
 * execution time performance if one forgets the log4j config file.
 * <p>
 * Note that a static variable control this setting and therefore
 * the debug-enable applies to engines within the module or VM.
 */
public class ExecutionPathDebugLog
{
    private static Boolean isDebugEnabled;

    /**
     * Returns true to allow execution path debug logging, or false if not.
     * @return true for debug logging, false for no debug logging
     */
    public static boolean isEnabled()
    {
        if (isDebugEnabled != null)
        {
            return isDebugEnabled;
        }

        return false;
    }

    /**
     * Sets execution path debug logging.
     * @param debugEnabled true for allowing Log4j debug log messages to be generated for the execution path
     */
    public static void setDebugEnabled(Boolean debugEnabled)
    {
        isDebugEnabled = debugEnabled;
    }
}
