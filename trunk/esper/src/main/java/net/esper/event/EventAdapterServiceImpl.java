package net.esper.event;

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

    /**
     * Ctor.
     */
    public EventAdapterServiceImpl()
    {
        super();
    }
}
