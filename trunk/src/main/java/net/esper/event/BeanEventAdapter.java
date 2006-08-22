package net.esper.event;

import java.util.Map;
import java.util.HashMap;

/**
 * A cache and factory class for obtaining {@link EventType} instances and {@link EventBean} instances
 * for Java Bean events. The class caches {@link EventType} instances already known for performance reasons.
 */
public class BeanEventAdapter
{
    private final Map<Class, BeanEventType> typesPerJavaBean;

    /**
     * Ctor.
     * Start with an empty list of known classes and event types.
     */
    public BeanEventAdapter()
    {
        typesPerJavaBean = new HashMap<Class, BeanEventType>();
    }

    /**
     * Returns an adapter for the given Java Bean.
     * @param event is the bean to wrap
     * @return EventBean wrapping Java Bean
     */
    public EventBean adapterForBean(Object event)
    {
        Class eventClass = event.getClass();
        EventType eventType = createBeanType(eventClass);
        return new BeanEventBean(event, eventType);
    }

    /**
     * Creates a new EventType object for a java bean of the specified class if this is the first time
     * the class has been seen. Else uses a cached EventType instance, i.e. client classes do not need to cache.
     * @param clazz is the class of the Java bean.
     * @return EventType implementation for bean class
     */
    public final BeanEventType createBeanType(Class clazz)
    {
        if (clazz == null)
        {
            throw new IllegalArgumentException("Null value passed as class");
        }

        // Check if its already there
        BeanEventType eventType = typesPerJavaBean.get(clazz);
        if (eventType != null)
        {
            return eventType;
        }

        eventType = new BeanEventType(clazz, this);
        typesPerJavaBean.put(clazz, eventType);

        return eventType;
    }
}
