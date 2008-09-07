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
import com.espertech.esper.core.EPRuntimeEventSender;
import com.espertech.esper.util.JavaClassHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Event sender for POJO Java object events.
 * <p>
 * Allows sending only event objects of the underlying type matching the event type, or
 * implementing the interface or extending the type. Any other event object generates an error.
 */
public class EventSenderBean implements EventSender
{
    private final EPRuntimeEventSender runtime;
    private final BeanEventType beanEventType;
    private final Set<Class> compatibleClasses;

    /**
     * Ctor.
     * @param runtime for processing events
     * @param beanEventType the event type
     */
    public EventSenderBean(EPRuntimeEventSender runtime, BeanEventType beanEventType)
    {
        this.runtime = runtime;
        this.beanEventType = beanEventType;
        compatibleClasses = new HashSet<Class>();
    }

    public void sendEvent(Object event)
    {
        // type check
        if (event.getClass() != beanEventType.getUnderlyingType())
        {
            synchronized (this)
            {
                if (!compatibleClasses.contains(event.getClass()))
                {
                    if (JavaClassHelper.isSubclassOrImplementsInterface(event.getClass(), beanEventType.getUnderlyingType()))
                    {
                        compatibleClasses.add(event.getClass());
                    }
                    else
                    {
                        throw new EPException("Event object of type " + event.getClass().getName() +
                                " does not equal, extend or implement the type " + beanEventType.getUnderlyingType().getName() +
                                " of event type '" + beanEventType.getAlias() + "'");
                    }
                }
            }
        }

        runtime.processWrappedEvent(new BeanEventBean(event, beanEventType));
    }
}
