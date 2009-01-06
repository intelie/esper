/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.transaction;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RealtimeSummaryTotalsListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)
            return;
        }

        EventBean event = newEvents[0];
        log.debug(
                " Totals minAC=" + event.get("minLatencyAC") +
                " maxAC=" + event.get("maxLatencyAC") +
                " avgAC=" + event.get("avgLatencyAC") +
                " minAB=" + event.get("minLatencyAB") +
                " maxAB=" + event.get("maxLatencyAB") +
                " avgAB=" + event.get("avgLatencyAB") +
                " minBC=" + event.get("minLatencyBC") +
                " maxBC=" + event.get("maxLatencyBC") +
                " avgBC=" + event.get("avgLatencyBC")
                );
    }

    private static final Log log = LogFactory.getLog(RealtimeSummaryTotalsListener.class);
}
