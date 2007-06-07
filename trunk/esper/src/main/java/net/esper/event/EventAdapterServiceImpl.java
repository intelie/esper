package net.esper.event;

/**
 * Provides event adapter services through it's base class.
 */
public class EventAdapterServiceImpl extends EventAdapterServiceBase
{
    public EventType addBeanType(String eventTypeAlias, Class clazz) throws EventAdapterException
    {
        return super.addBeanTypeByAliasAndClazz(eventTypeAlias, clazz);
    }

    public EventType addBeanType(String eventTypeAlias, String className) throws EventAdapterException
    {
        return super.addBeanTypeByAliasAndClassName(eventTypeAlias, className);
    }

    public EventBean adapterForBean(Object event)
    {
        return super.adapterForBean(event, null);
    }
}
