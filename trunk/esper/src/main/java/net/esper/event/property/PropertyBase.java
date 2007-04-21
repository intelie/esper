package net.esper.event.property;

/**
 * All properties have a property name and this is the abstract base class that serves up the property name.
 */
public abstract class PropertyBase implements Property
{
    /**
     * Property name.
     */
    protected String propertyName;

    /**
     * Ctor.
     * @param propertyName is the name of the property
     */
    public PropertyBase(String propertyName)
    {
        this.propertyName = propertyName;
    }

    /**
     * Returns the property name.
     * @return name of property
     */
    public String getPropertyName()
    {
        return propertyName;
    }
}
