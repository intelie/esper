package com.espertech.esper.event;

import java.util.Map;

/**
 * Getter that reads a Map array from a Map.
 */
public class MapNamedMapArrayIndexPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;
    private final int index;

    /**
     * Ctor.
     * @param propertyName is the name of the property to look up in the map
     * @param index the index to fetch the array element for
     */
    public MapNamedMapArrayIndexPropertyGetter(String propertyName, int index)
    {
        this.propertyName = propertyName;
        this.index = index;
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
        if (!(value instanceof Map[]))
        {
            return null;
        }
        Map[] maps = (Map[]) value;
        if (maps.length <= index)
        {
            return null;
        }
        return maps[index];
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }
}
