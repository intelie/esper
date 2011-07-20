/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.support.util;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.client.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PrintUpdateListener implements UpdateListener
{
    private static final Log log = LogFactory.getLog(PrintUpdateListener.class);

    private final String listenerName;

    public PrintUpdateListener()
    {
        listenerName = "";
    }

    public PrintUpdateListener(String listenerName)
    {
        this.listenerName = listenerName;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        for (int i = 0; i < newEvents.length; i++)
        {
            log.info(".update " + listenerName + " Event#" + i + " : " + dumpProperties(newEvents[i]));
        }
    }

    private static String dumpProperties(EventBean newEvent)
    {
        StringBuilder buf = new StringBuilder();
        for (String name : newEvent.getEventType().getPropertyNames())
        {
            buf.append(' ');
            buf.append(name);
            buf.append("=");
            buf.append(newEvent.get(name));
        }
        return buf.toString();
    }

    public static void print(String title, EventBean[] events)
    {
        for (int i = 0; i < events.length; i++)
        {
            log.info(".print " + title + " Event#" + i + " : " + dumpProperties(events[i]));
        }
    }
}
