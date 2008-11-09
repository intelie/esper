package com.espertech.esper.event;

import java.util.Map;

/**
 * Getter that reads a Map out of a Map.
 */
public class MapNamedMapPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;

    /**
     * Ctor.
     * @param propertyName is the name of the property to look up in the map
     */
    public MapNamedMapPropertyGetter(String propertyName)
    {
        this.propertyName = propertyName;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object underlying = eventBean.getUnderlying();

        // The underlying is expected to be a map
        if (!(underlying instanceof Map))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type java.lang.Map");
        }

        Map map = (Map) underlying;

        Object value = map.get(propertyName);
        if (value == null)
        {
            return null;
        }
        if (!(value instanceof Map))
        {
            return null;
        }
        return value;
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }
}
