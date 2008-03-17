package com.espertech.esper.event;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

/**
 * Encapsulates the event property information available after introspecting an event's class members
 * for getter methods.
 */
public class EventPropertyDescriptor
{
    private String propertyName;
    private String listedName;
    private Method readMethod;
    private Field accessorField;
    private EventPropertyType propertyType;

    /**
     * Ctor.
     * @param propertyName - name of property, from getter method
     * @param listedName - name the property may show up when listed as a valid property, such as indexed[], mapped()
     * @param readMethod - read method to get value
     * @param propertyType - type of property
     */
    public EventPropertyDescriptor(String propertyName, String listedName, Method readMethod, EventPropertyType propertyType)
    {
        this.propertyName = propertyName;
        this.listedName = listedName;
        this.readMethod = readMethod;
        this.propertyType = propertyType;
    }

    /**
     * Ctor.
     * @param propertyName - name of property, from getter method
     * @param listedName - name the property may show up when listed as a valid property, such as indexed[], mapped()
     * @param accessorField - field to get value from
     * @param propertyType - type of property
     */
    public EventPropertyDescriptor(String propertyName, String listedName, Field accessorField, EventPropertyType propertyType)
    {
        this.propertyName = propertyName;
        this.listedName = listedName;
        this.accessorField = accessorField;
        this.propertyType = propertyType;
    }

    /**
     * Return the property name, for mapped and indexed properties this is just the property name
     * without parantheses or brackets.
     * @return property name
     */
    public String getPropertyName()
    {
        return propertyName;
    }

    /**
     * Returns the listed name, which is the name the property would show up as when asking an
     * event type for the set of valid properties. The listed name for indexed properties
     * is "name[]" since an index is required for valid property access.
     * The listed name for mapped properties is "name()".
     * @return listed name
     */
    public String getListedName()
    {
        return listedName;
    }

    /**
     * Returns an enum indicating the type of property: simple, mapped, indexed.
     * @return enum with property type info
     */
    public EventPropertyType getPropertyType()
    {
        return propertyType;
    }

    /**
     * Returns the read method. Can return null if the property is backed by a field..
     * @return read method of null if field property
     */
    public Method getReadMethod()
    {
        return readMethod;
    }

    /**
     * Returns the accessor field. Can return null if the property is backed by a method.
     * @return accessor field of null if method property
     */
    public Field getAccessorField()
    {
        return accessorField;
    }

    /**
     * Returns the type of the underlying method or field of the event property.
     * @return return type
     */
    public Class getReturnType()
    {
        if (readMethod != null)
        {
            return readMethod.getReturnType();
        }
        else
        {
            return accessorField.getType();
        }
    }

    public String toString()
    {
        return  "propertyName=" + propertyName +
                " listedName=" + listedName +
                " readMethod=" + readMethod +
                " accessorField=" + accessorField +
                " propertyType=" + propertyType;
    }

    public boolean equals(Object other)
    {
        if (!(other instanceof EventPropertyDescriptor))
        {
            return false;
        }
        EventPropertyDescriptor otherDesc = (EventPropertyDescriptor) other;
        if (!otherDesc.propertyName.equals(propertyName))
        {
            return false;
        }
        if (!otherDesc.listedName.equals(listedName))
        {
            return false;
        }
        if  ( ((otherDesc.readMethod == null) && (readMethod != null)) ||
              ((otherDesc.readMethod != null) && (readMethod == null)) )
        {
            return false;
        }
        if ((otherDesc.readMethod != null) && (readMethod != null) &&
            (!otherDesc.readMethod.equals(readMethod)))
        {
            return false;
        }
        if  ( ((otherDesc.accessorField == null) && (accessorField != null)) ||
              ((otherDesc.accessorField != null) && (accessorField == null)) )
        {
            return false;
        }
        if ((otherDesc.accessorField != null) && (accessorField != null) &&
            (!otherDesc.accessorField.equals(accessorField)))
        {
            return false;
        }
        if (otherDesc.propertyType != propertyType)
        {
            return false;
        }
        return true;
    }

    public int hashCode()
    {
        return propertyName.hashCode();
    }
}
