package com.espertech.esper.event;

import java.util.Map;
import java.lang.reflect.Array;

/**
 * A getter that works on POJO events residing within a Map as an event property.
 */
public class MapArrayPOJOBeanEntryIndexedPropertyGetter implements EventPropertyGetter {

    private final String propertyMap;
    private final int index;
    private final EventPropertyGetter mapEntryGetter;
    private final EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param propertyMap the property to look at
     * @param mapEntryGetter the getter for the map entry
     * @param eventAdapterService for producing wrappers to objects
     * @param index the index to fetch the array element for
     */
    public MapArrayPOJOBeanEntryIndexedPropertyGetter(String propertyMap, int index, EventPropertyGetter mapEntryGetter, EventAdapterService eventAdapterService) {
        this.propertyMap = propertyMap;
        this.index = index;
        this.mapEntryGetter = mapEntryGetter;
        this.eventAdapterService = eventAdapterService;
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

        // If the map does not contain the key, this is allowed and represented as null
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
        Object arrayItem = Array.get(value, index);
        if (arrayItem == null)
        {
            return null;
        }

        // Object within the map
        EventBean event = eventAdapterService.adapterForBean(arrayItem);
        return mapEntryGetter.get(event);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
