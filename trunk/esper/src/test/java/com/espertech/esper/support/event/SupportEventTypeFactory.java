package com.espertech.esper.support.event;

import com.espertech.esper.event.EventType;

import java.util.Map;

public class SupportEventTypeFactory
{
    public static EventType createBeanType(Class clazz)
    {
        return SupportEventAdapterService.getService().addBeanType(clazz.getName(), clazz);
    }

    public static EventType createMapType(Map<String,Object> map)
    {
        return SupportEventAdapterService.getService().createAnonymousMapType(map);
    }
}
