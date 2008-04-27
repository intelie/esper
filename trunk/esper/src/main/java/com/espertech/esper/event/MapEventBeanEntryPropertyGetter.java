package com.espertech.esper.event;

import java.util.Map;

/**
 * A getter that works on EventBean events residing within a Map as an event property.
 */
public class MapEventBeanEntryPropertyGetter implements EventPropertyGetter {

    private final String propertyMap;
    private final EventPropertyGetter eventBeanEntryGetter;

    /**
     * Ctor.
     * @param propertyMap the property to look at
     * @param eventBeanEntryGetter the getter for the map entry
     */
    public MapEventBeanEntryPropertyGetter(String propertyMap, EventPropertyGetter eventBeanEntryGetter) {
        this.propertyMap = propertyMap;
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

        // If the map does not contain the key, this is allowed and represented as null
        Object value = map.get(propertyMap);

        if (value == null)
        {
            return null;
        }

        // Object within the map
        EventBean event = (EventBean) value;
        return eventBeanEntryGetter.get(event);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }
}
