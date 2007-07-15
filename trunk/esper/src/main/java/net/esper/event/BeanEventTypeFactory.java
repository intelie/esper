package net.esper.event;

import net.esper.client.ConfigurationEventTypeLegacy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Interface for a factory for obtaining {@link BeanEventType} instances.
 */
public interface BeanEventTypeFactory
{
    /**
     * Returns the bean event type for a given class assigning the given alias.
     * @param alias is the alias
     * @param clazz is the class for which to generate an event type
     * @return is the event type for the class
     */
    public BeanEventType createBeanType(String alias, Class clazz);
}
