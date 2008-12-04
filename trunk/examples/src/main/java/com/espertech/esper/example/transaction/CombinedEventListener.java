/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.transaction;

import com.espertech.esper.client.*;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CombinedEventListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)
            return;
        }

        EventBean event = newEvents[0];
        log.debug("Combined event detected " +
                " transactionId=" + event.get("transactionId") +
                " customerId=" + event.get("customerId") +
                " supplierId=" + event.get("supplierId") +
                " latencyAC=" + event.get("latencyAC") +
                " latencyAB=" + event.get("latencyAB") +
                " latencyBC=" + event.get("latencyBC")
                );
    }

    private static final Log log = LogFactory.getLog(CombinedEventListener.class);
}
