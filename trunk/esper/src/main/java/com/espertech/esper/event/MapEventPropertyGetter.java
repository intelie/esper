package com.espertech.esper.event;

import java.util.Map;

/**
 * A getter for use with Map-based events simply returns the value for the key.
 */
public class MapEventPropertyGetter implements EventPropertyGetter 
{
    private final String propertyName;

    /**
     * Ctor.
     * @param propertyName property to get
     */
    public MapEventPropertyGetter(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object get(EventBean obj)
    {
        // The underlying is expected to be a map
        if (!(obj.getUnderlying() instanceof Map))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type java.lang.Map");
        }

        Map map = (Map) obj.getUnderlying();

        // If the map does not contain the key, this is allowed and represented as null
        return map.get(propertyName);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
