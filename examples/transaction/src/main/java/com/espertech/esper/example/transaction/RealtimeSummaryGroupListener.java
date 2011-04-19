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

public class RealtimeSummaryGroupListener implements UpdateListener
{
    private String groupIdentifier;

    public RealtimeSummaryGroupListener(String groupIdentifier)
    {
        this.groupIdentifier = groupIdentifier;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)
            return;
        }

        EventBean event = newEvents[0];
        log.debug(
                groupIdentifier + "=" + event.get(groupIdentifier) +
                " minAC=" + event.get("minLatency") +
                " maxAC=" + event.get("maxLatency") +
                " avgAC=" + event.get("avgLatency")
                );
    }

    private static final Log log = LogFactory.getLog(RealtimeSummaryGroupListener.class);
}
