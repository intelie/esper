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
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyGetter;

import java.util.List;
import java.util.ArrayList;

/**
 * Reader implementation that utilizes event property getters and thereby works with all
 * event types regardsless of whether a type returns an event reader when asked for.
 */
public class EventBeanReaderDefaultImpl implements EventBeanReader
{
    private EventPropertyGetter gettersArray[];

    /**
     * Ctor.
     * @param eventType the type of events to read
     */
    public EventBeanReaderDefaultImpl(EventType eventType)
    {
        String[] properties = eventType.getPropertyNames();
        List<EventPropertyGetter> getters = new ArrayList<EventPropertyGetter>();
        for (String property : properties)
        {
            EventPropertyGetter getter = eventType.getGetter(property);
            if (getter != null)
            {
                getters.add(getter);
            }
        }
        gettersArray = getters.toArray(new EventPropertyGetter[getters.size()]);
    }

    public Object[] read(EventBean event)
    {
        Object[] values = new Object[gettersArray.length];
        for (int i = 0; i < gettersArray.length; i++)
        {
            values[i] = gettersArray[i].get(event);
        }
        return values;
    }
}