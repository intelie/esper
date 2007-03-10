package net.esper.event;

import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.util.UuidGenerator;

import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * A cache and factory class for obtaining {@link EventType} instances and {@link EventBean} instances
 * for Java Bean events. The class caches {@link EventType} instances already known for performance reasons.
 * <p>
 * This class is multithread-safe. 
 */
public class BeanEventAdapter
{
    private final Map<Class, BeanEventType> typesPerJavaBean;
    private Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs;
    private final ReadWriteLock typesPerJavaBeanLock;

    /**
     * Ctor.
     */
    public BeanEventAdapter()
    {
        typesPerJavaBean = new HashMap<Class, BeanEventType>();
        typesPerJavaBeanLock = new ReentrantReadWriteLock();
        classToLegacyConfigs = new HashMap<String, ConfigurationEventTypeLegacy>();
    }

    /**
     * Set the additional mappings for legacy classes.
     * @param classToLegacyConfigs legacy class information
     */
    public void setClassToLegacyConfigs(Map<String, ConfigurationEventTypeLegacy> classToLegacyConfigs)
    {
        this.classToLegacyConfigs.putAll(classToLegacyConfigs);
    }

    /**
     * Returns an adapter for the given Java Bean.
     * @param event is the bean to wrap
     * @param eventId is an optional id to assigned to the event
     * @return EventBean wrapping Java Bean
     */
    public EventBean adapterForBean(Object event, Object eventId)
    {
        Class eventClass = event.getClass();
        EventType eventType = createOrGetBeanType(eventClass);
        return new BeanEventBean(event, eventType, eventId);
    }

    /**
     * Creates a new EventType object for a java bean of the specified class if this is the first time
     * the class has been seen. Else uses a cached EventType instance, i.e. client classes do not need to cache.
     * @param clazz is the class of the Java bean.
     * @return EventType implementation for bean class
     */
    public final BeanEventType createOrGetBeanType(Class clazz)
    {
        if (clazz == null)
        {
            throw new IllegalArgumentException("Null value passed as class");
        }

        // Check if its already there
        typesPerJavaBeanLock.readLock().lock();
        BeanEventType eventType = typesPerJavaBean.get(clazz);
        typesPerJavaBeanLock.readLock().unlock();
        if (eventType != null)
        {
            return eventType;
        }

        // not created yet, thread-safe create
        typesPerJavaBeanLock.writeLock().lock();
        try
        {
            eventType = typesPerJavaBean.get(clazz);
            if (eventType != null)
            {
                return eventType;
            }

            // Check if we have a legacy type definition for this class
            ConfigurationEventTypeLegacy legacyDef = classToLegacyConfigs.get(clazz.getName());

            String eventTypeId = "CLASS_" + clazz.getName();
            eventType = new BeanEventType(clazz, this, legacyDef, eventTypeId);
            typesPerJavaBean.put(clazz, eventType);
        }
        catch (RuntimeException ex)
        {
            throw ex;
        }
        finally
        {
            typesPerJavaBeanLock.writeLock().unlock();
        }

        return eventType;
    }
}
