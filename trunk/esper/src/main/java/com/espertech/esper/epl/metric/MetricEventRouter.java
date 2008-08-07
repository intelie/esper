/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.metric;

import com.espertech.esper.client.metric.MetricEvent;

/**
 * Interface for routing metric events for processing.
 */
public interface MetricEventRouter
{
    /**
     * Process metric event.
     * @param metricEvent metric event to process
     */
    public void route(MetricEvent metricEvent);
}
