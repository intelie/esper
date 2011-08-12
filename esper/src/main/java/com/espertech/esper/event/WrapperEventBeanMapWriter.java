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
 * Writer for wrapper events.
 */
public class WrapperEventBeanMapWriter implements EventBeanWriter
{
    private final String[] properties;

    /**
     * Ctor.
     * @param properties to write
     */
    public WrapperEventBeanMapWriter(String[] properties)
    {
        this.properties = properties;
    }

    public void write(Object[] values, EventBean event)
    {
        DecoratingEventBean mappedEvent = (DecoratingEventBean) event;
        Map<String, Object> map = mappedEvent.getDecoratingProperties();

        for (int i = 0; i < properties.length; i++)
        {
            map.put(properties[i], values[i]);
        }
    }
}
