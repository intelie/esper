package com.espertech.esper.event;

import java.util.Map;

public class MapNamedMapArrayIndexPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;
    private final int index;

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
