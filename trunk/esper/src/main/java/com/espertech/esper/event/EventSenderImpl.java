/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventSender;
import com.espertech.esper.core.EPRuntimeImpl;
import com.espertech.esper.core.EPRuntimeEventSender;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;

/**
 * Event sender for use with plug-in event representations.
 * <p>
 * The implementation asks a list of event bean factoryies originating from plug-in event representations
 * to each reflect on the event and generate an event bean. The first one to return an event bean
 * wins.
 */
public class EventSenderImpl implements EventSender
{
    private static Log log = LogFactory.getLog(EventSenderImpl.class);
    private final List<EventSenderURIDesc> handlingFactories;
    private final EPRuntimeEventSender epRuntime;

    /**
     * Ctor.
     * @param handlingFactories list of factories
     * @param epRuntime the runtime to use to process the event
     */
    public EventSenderImpl(List<EventSenderURIDesc> handlingFactories, EPRuntimeEventSender epRuntime)
    {
        this.handlingFactories = handlingFactories;
        this.epRuntime = epRuntime;
    }

    public void sendEvent(Object event) throws EPException
    {
        // Ask each factory in turn to take care of it
        for (EventSenderURIDesc entry : handlingFactories)
        {
            EventBean eventBean = null;

            try
            {
                eventBean = entry.getBeanFactory().create(event, entry.getResolutionURI());
            }
            catch (RuntimeException ex)
            {
                log.warn("Unexpected exception thrown by plug-in event bean factory '" + entry.getBeanFactory() + "' processing event " + event, ex);
            }

            if (eventBean != null)
            {
                epRuntime.processWrappedEvent(eventBean);
                return;
            }
        }
    }
}
