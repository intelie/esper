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

import com.espertech.esper.plugin.PlugInEventTypeHandler;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeEventSender;

public class MyPlugInPropertiesEventTypeHandler implements PlugInEventTypeHandler
{
    private final MyPlugInPropertiesEventType eventType;

    public MyPlugInPropertiesEventTypeHandler(MyPlugInPropertiesEventType eventType)
    {
        this.eventType = eventType;
    }

    public EventSender getSender(EPRuntimeEventSender runtimeEventSender)
    {
        return new MyPlugInPropertiesEventSender(eventType, runtimeEventSender);
    }

    public EventType getType()
    {
        return eventType;
    }
}
