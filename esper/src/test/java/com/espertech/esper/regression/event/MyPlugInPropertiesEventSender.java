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

package com.espertech.esper.regression.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.client.EventBean;

import java.util.Properties;

public class MyPlugInPropertiesEventSender implements EventSender
{
    private final MyPlugInPropertiesEventType type;
    private final EPRuntimeEventSender runtimeSender;

    public MyPlugInPropertiesEventSender(MyPlugInPropertiesEventType type, EPRuntimeEventSender runtimeSender)
    {
        this.type = type;
        this.runtimeSender = runtimeSender;
    }

    public void sendEvent(Object event)
    {
        if (!(event instanceof Properties))
        {
            throw new EPException("Sender expects a properties event");
        }
        EventBean eventBean = new MyPlugInPropertiesEventBean(type, (Properties) event);
        runtimeSender.processWrappedEvent(eventBean);
    }

    public void route(Object event)
    {
        if (!(event instanceof Properties))
        {
            throw new EPException("Sender expects a properties event");
        }
        EventBean eventBean = new MyPlugInPropertiesEventBean(type, (Properties) event);
        runtimeSender.routeEventBean(eventBean);
    }
}
