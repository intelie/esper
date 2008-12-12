package com.espertech.esper.event;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;

import java.util.Map;

public class MapMaptypedUndPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;

    public MapMaptypedUndPropertyGetter(String propertyNameAtomic)
    {
        propertyName = propertyNameAtomic;
    }

    public Object get(EventBean obj) throws PropertyAccessException
    {
        Object underlying = obj.getUnderlying();

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
        if (!(value instanceof EventBean))
        {
            return null;
        }

        return ((EventBean)value).getUnderlying();
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }

    public Object getFragment(EventBean eventBean) throws PropertyAccessException
    {
        return null;  //TODO
    }
}
