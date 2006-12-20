package net.esper.event.property;

import net.esper.event.*;

/**
 * Interface for a property of an event of type BeanEventType (JavaBean event). Properties are designed to
 * handle the different types of properties for such events: indexed, mapped, simple, nested, or a combination of
 * those.
 */
public interface Property
{
    /**
     * Returns the property type.
     * @param eventType is the event type representing the JavaBean
     * @return property type class
     */
    public Class getPropertyType(BeanEventType eventType);

    /**
     * Returns value getter for the property of an event of the given event type.
     * @param eventType is the type of event to make a getter for
     * @return fast property value getter for property
     */
    public EventPropertyGetter getGetter(BeanEventType eventType);
}
