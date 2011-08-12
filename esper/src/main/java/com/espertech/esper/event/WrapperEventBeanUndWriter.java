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

package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;

import java.util.Map;

/**
 * Writer for values to a wrapper event.
 */
public class WrapperEventBeanUndWriter implements EventBeanWriter
{
    private final EventBeanWriter undWriter;

    /**
     * Ctor.
     * @param undWriter writer to the underlying object
     */
    public WrapperEventBeanUndWriter(EventBeanWriter undWriter)
    {
       this.undWriter = undWriter;
    }

    public void write(Object[] values, EventBean event)
    {
        DecoratingEventBean wrappedEvent = (DecoratingEventBean) event;
        EventBean eventWrapped = wrappedEvent.getUnderlyingEvent();
        undWriter.write(values, eventWrapped);
    }
}
