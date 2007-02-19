package net.esper.event.property;

import net.esper.event.EventPropertyDescriptor;

import java.util.List;

/**
 * Interface for an introspector that generates a list of event property descriptors
 * given a clazz. The clazz could be a JavaBean-style class or any other legacy type.
 */
public interface PropertyListBuilder
{
    /**
     * Introspect the clazz and deterime exposed event properties.
     * @param clazz to introspect
     * @return list of event property descriptors
     */
    public List<EventPropertyDescriptor> assessProperties(Class clazz);
}
