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

package com.espertech.esper.support.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.support.bean.*;

import java.util.Map;

public class SupportEventBeanFactory
{
    public static EventBean createObject(Object event)
    {
        return SupportEventAdapterService.getService().adapterForBean(event);
    }

    public static EventBean createMapFromValues(Map<String, Object> testValuesMap, EventType eventType)
    {
        return SupportEventAdapterService.getService().adaptorForTypedMap(testValuesMap, eventType);
    }   

    public static EventBean[] makeEvents(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setString(ids[i]);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeEvents(boolean[] boolPrimitiveValues)
    {
        EventBean[] events = new EventBean[boolPrimitiveValues.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean bean = new SupportBean();
            bean.setBoolPrimitive(boolPrimitiveValues[i]);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeMarketDataEvents(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportMarketDataBean bean = new SupportMarketDataBean(ids[i], 0, 0L, null);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeEvents_A(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_A bean = new SupportBean_A(ids[i]);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeEvents_B(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_B bean = new SupportBean_B(ids[i]);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeEvents_C(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_C bean = new SupportBean_C(ids[i]);
            events[i] = createObject(bean);
        }
        return events;
    }

    public static EventBean[] makeEvents_D(String[] ids)
    {
        EventBean[] events = new EventBean[ids.length];
        for (int i = 0; i < events.length; i++)
        {
            SupportBean_D bean = new SupportBean_D(ids[i]);
            events[i] = createObject(bean);
        }
        return events;
    }
}
