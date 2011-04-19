package com.espertech.esper.support.event;

import com.espertech.esper.client.EventType;

import java.util.Map;

public class SupportEventTypeFactory
{
    public static EventType createBeanType(Class clazz, String name)
    {
        return SupportEventAdapterService.getService().addBeanType(name, clazz, false, false, false);
    }

    public static EventType createBeanType(Class clazz)
    {
        return SupportEventAdapterService.getService().addBeanType(clazz.getName(), clazz, false, false, false);
    }

    public static EventType createMapType(Map<String,Object> map)
    {
        return SupportEventAdapterService.getService().createAnonymousMapType(map);
    }
}
