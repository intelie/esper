package com.espertech.esper.event.property;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.PropertyAccessException;

import java.util.Map;
import java.lang.reflect.Array;

/**
 * Getter for a dynamic mappeds property for maps.
 */
public class MapMappedPropertyGetter implements EventPropertyGetter
{
    private final String key;
    private final String fieldName;

    /**
     * Ctor.
     * @param fieldName property name
     * @param key get the element at
     */
    public MapMappedPropertyGetter(String fieldName, String key)
    {
        this.key = key;
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
        if (!(value instanceof Map))
        {
            return null;
        }
        Map innerMap = (Map) value;
        return innerMap.get(key);
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
        if (!(value instanceof Map))
        {
            return false;
        }
        Map innerMap = (Map) value;
        return innerMap.containsKey(key);
    }
}
