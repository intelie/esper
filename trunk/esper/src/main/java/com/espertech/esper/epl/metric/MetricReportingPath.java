/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Global boolean for enabling and disable metrics reporting.
 */
public class MetricReportingPath
{
    private static final Log log = LogFactory.getLog(MetricReportingPath.class);

    /**
     * Public access.
     */
    public static boolean isMetricsEnabled = false;

    /**
     * Sets execution path debug logging.
     * @param metricsEnabled true if metric reporting should be enabled
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
