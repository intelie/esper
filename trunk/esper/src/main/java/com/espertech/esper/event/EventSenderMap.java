/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EventSender;
import com.espertech.esper.client.EPException;
import com.espertech.esper.core.EPRuntimeEventSender;

import java.util.Map;

/**
 * Event sender for map-backed events.
 * <p>
 * Allows sending only event objects of type map, does not check map contents. Any other event object generates an error.
 */
public class EventSenderMap implements EventSender
{
    private final EPRuntimeEventSender runtimeEventSender;
    private final MapEventType mapEventType;

    /**
     * Ctor.
     * @param runtimeEventSender for processing events
     * @param mapEventType the event type
     */
    public EventSenderMap(EPRuntimeEventSender runtimeEventSender, MapEventType mapEventType)
    {
        this.runtimeEventSender = runtimeEventSender;
        this.mapEventType = mapEventType;
    }

    public void sendEvent(Object event)
    {
        if (!(event instanceof Map))
        {
            throw new EPException("Unexpected event object of type " + event.getClass().getName() + ", expected " + Map.class.getName());
        }
        Map<String, Object> map = (Map<String, Object>) event;
        MapEventBean mapEvent = new MapEventBean(map, mapEventType);
        runtimeEventSender.processWrappedEvent(mapEvent);
    }

    public void route(Object event)
    {
        if (!(event instanceof Map))
        {
            throw new EPException("Unexpected event object of type " + event.getClass().getName() + ", expected " + Map.class.getName());
        }
        Map<String, Object> map = (Map<String, Object>) event;
        MapEventBean mapEvent = new MapEventBean(map, mapEventType);
        runtimeEventSender.routeEventBean(mapEvent);
    }
}
