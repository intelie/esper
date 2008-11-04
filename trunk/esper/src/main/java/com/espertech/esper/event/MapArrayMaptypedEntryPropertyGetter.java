package com.espertech.esper.event;

import java.util.Map;
import java.lang.reflect.Array;

/**
 * A getter that works on EventBean events residing within a Map as an event property.
 */
public class MapArrayMaptypedEntryPropertyGetter implements EventPropertyGetter {

    private final String propertyMap;
    private final int index;
    private final EventPropertyGetter eventBeanEntryGetter;

    /**
     * Ctor.
     * @param propertyMap the property to look at
     * @param eventBeanEntryGetter the getter for the map entry
     * @param index the index to fetch the array element for
     */
    public MapArrayMaptypedEntryPropertyGetter(String propertyMap, int index, EventPropertyGetter eventBeanEntryGetter) {
        this.propertyMap = propertyMap;
        this.index = index;
        this.eventBeanEntryGetter = eventBeanEntryGetter;
    }

    public Object get(EventBean obj)
    {
        Object underlying = obj.getUnderlying();

        // The underlying is expected to be a map
        if (!(underlying instanceof Map))
        {
            throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                    "the underlying data object is not of type java.lang.Map");
        }

        Map map = (Map) underlying;

        Object value = map.get(propertyMap);
        if (value == null)
        {
            return null;
        }
        if (!value.getClass().isArray())
        {
            return null;
        }
        if (Array.getLength(value) <= index)
        {
            return null;
        }
        Object valueMap = Array.get(value, index);
        if (!(valueMap instanceof Map))
        {
            return null;
        }

        // If the map does not contain the key, this is allowed and represented as null
        EventBean eventBean = new MapEventBean((Map) valueMap, null);
        return eventBeanEntryGetter.get(eventBean);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
