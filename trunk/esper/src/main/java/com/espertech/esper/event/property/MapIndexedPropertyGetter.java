package com.espertech.esper.event.property;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.PropertyAccessException;

import java.lang.reflect.Array;
import java.util.Map;

/**
 * Getter for a dynamic indexed property for maps.
 */
public class MapIndexedPropertyGetter implements EventPropertyGetter
{
    private final int index;
    private final String fieldName;

    /**
     * Ctor.
     * @param fieldName property name
     * @param index index to get the element at
     */
    public MapIndexedPropertyGetter(String fieldName, int index)
    {
        this.index = index;
        this.fieldName = fieldName;
    }

    public Object get(EventBean eventBean) throws PropertyAccessException
    {
        Object underlying = eventBean.getUnderlying();
        if (!(underlying instanceof Map))
        {
            return null;
        }
        Map map = (Map) underlying;
        Object value = map.get(fieldName);
        if (value == null)
        {
            return null;
        }
        if (!value.getClass().isArray())
        {
            return null;
        }
        if (index >= Array.getLength(value))
        {
            return null;
        }
        return Array.get(value, index);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        Object underlying = eventBean.getUnderlying();
        if (!(underlying instanceof Map))
        {
            return false;
        }
        Map map = (Map) underlying;
        Object value = map.get(fieldName);
        if (value == null)
        {
            return false;
        }
        if (!value.getClass().isArray())
        {
            return false;
        }
        if (index >= Array.getLength(value))
        {
            return false;
        }
        return true;
    }
}