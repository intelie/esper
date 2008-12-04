package com.espertech.esper.support.event;

import com.espertech.esper.client.EventType;

import java.util.Map;

public class SupportEventTypeFactory
{
    public static EventType createBeanType(Class clazz)
    {
        return SupportEventAdapterService.getService().addBeanType(clazz.getName(), clazz, false);
    }

    public static EventType createMapType(Map<String,Object> map)
    {
        return SupportEventAdapterService.getService().createAnonymousMapType(map);
    }
}
