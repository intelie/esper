/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.qos_sla.monitor;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AverageLatencyListener implements UpdateListener
{
    private long alertThreshold;

    public AverageLatencyListener(long alertThreshold)
    {
        this.alertThreshold = alertThreshold;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        long count = (Long) newEvents[0].get("datapoints");
        double avg = (Double) newEvents[0].get("average");

        if ((count < 100) || (avg < alertThreshold))
        {
            return;
        }

        String operation = (String) newEvents[0].get("operationName");
        String customer = (String) newEvents[0].get("customerId");

        log.debug("Alert, for operation '" + operation +
                "' and customer '" + customer + "'" +
                " average latency was " + avg);
    }

    private static final Log log = LogFactory.getLog(AverageLatencyListener.class);
}
