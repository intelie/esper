package net.esper.event;

import java.lang.reflect.Method;

/**
 * Encapsulates the event property information available after introspecting an event's class members
 * for getter methods.
 */
public class EventPropertyDescriptor
{
	private String propertyName;
    private String listedName;
	private Method readMethod;
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
     * Returns the read method.
     * @return read method
     */
	public Method getReadMethod()
	{
		return readMethod;
	}

    public String toString()
    {
        return  "propertyName=" + propertyName +
                "listedName=" + listedName +
                " readMethod=" + readMethod.toString() +
                " propertyType=" + propertyType;
    }
}
