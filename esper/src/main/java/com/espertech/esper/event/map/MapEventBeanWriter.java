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

package com.espertech.esper.event.map;

import com.espertech.esper.event.EventBeanWriter;
import com.espertech.esper.event.MappedEventBean;
import com.espertech.esper.client.EventBean;

import java.util.Map;

/**
 * Writer method for writing to Map-type events.
 */
public class MapEventBeanWriter implements EventBeanWriter
{
    private final String[] properties;

    /**
     * Ctor.
     * @param properties names of properties to write
     */
    public MapEventBeanWriter(String[] properties)
    {
        this.properties = properties;
    }

    /**
     * Write values to an event.
     * @param values to write
     * @param event to write to
     */
    public void write(Object[] values, EventBean event)
    {
        MappedEventBean mappedEvent = (MappedEventBean) event;
        Map<String, Object> map = mappedEvent.getProperties();

        for (int i = 0; i < properties.length; i++)
        {
            map.put(properties[i], values[i]);
        }
    }
}
