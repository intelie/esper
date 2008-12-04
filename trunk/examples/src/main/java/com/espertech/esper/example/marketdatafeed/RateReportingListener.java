/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RateReportingListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents.length > 0)
        {
            logRate(newEvents[0]);
        }
        if (newEvents.length > 1)
        {
            logRate(newEvents[1]);
        }
    }

    private void logRate(EventBean event)
    {
        log.info("Current rate for feed " + event.get("feed").toString() +
                  " is " + event.get("cnt"));
    }

    private static final Log log = LogFactory.getLog(RateReportingListener.class);
}
