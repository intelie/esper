package net.esper.support.event;

import net.esper.event.EventType;

import java.util.Map;

public class SupportEventTypeFactory
{
    public static EventType createBeanType(Class clazz)
    {
        return SupportEventAdapterService.getService().addBeanType(clazz.getName(), clazz);
    }

    public static EventType createMapType(Map<String,Class> map)
    {
        return SupportEventAdapterService.getService().createAnonymousMapType(map);
    }
}
