package com.espertech.esper.event.property;

import com.espertech.esper.event.*;

import java.util.Map;
import java.io.StringWriter;

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

    /**
     * Returns the property type for use with Map event representations.
     * @param optionalMapPropTypes a map-within-map type definition, if supplied, or null if not supplied
     * @return property type @param optionalMapPropTypes
     */
    public Class getPropertyTypeMap(Map optionalMapPropTypes);

    /**
     * Returns the getter-method for use with Map event representations.
     * @param optionalMapPropTypes a map-within-map type definition, if supplied, or null if not supplied
     * @return getter for maps @param optionalMapPropTypes
     */
    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes);

    /**
     * Write the EPL-representation of the property.
     * @param writer to write to
     */
    public void toPropertyEPL(StringWriter writer);    
}
