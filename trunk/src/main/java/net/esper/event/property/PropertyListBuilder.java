package net.esper.event.property;

import net.esper.event.EventPropertyDescriptor;

import java.util.List;

public interface PropertyListBuilder
{
    public List<EventPropertyDescriptor> assessProperties(Class clazz);
}
