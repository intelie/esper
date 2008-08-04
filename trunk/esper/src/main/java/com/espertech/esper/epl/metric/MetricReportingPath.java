package com.espertech.esper.epl.metric;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MetricReportingPath
{
    private static final Log log = LogFactory.getLog(MetricReportingPath.class);

    /**
     * Public access.
     */
    public static boolean isMetricsEnabled = false;

    /**
     * Sets execution path debug logging.
     * @param metricsEnabled
     */
    public static void setMetricsEnabled(boolean metricsEnabled)
    {
        String message;
        if (metricsEnabled)
        {
            message = "enabled";
        }
        else
        {
            message = "disabled";
        }
        log.info("Metrics reporting has been " + message + ", this setting takes affect for all engine instances at engine initialization time.");
        isMetricsEnabled = metricsEnabled;
    }
}
