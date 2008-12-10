package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;

import java.util.Map;

/**
 * A getter that works on EventBean events residing within a Map as an event property.
 */
public class MapMaptypedEntryPropertyGetter implements EventPropertyGetter {

    private final String propertyMap;
    private final EventPropertyGetter eventBeanEntryGetter;

    /**
     * Ctor.
     * @param propertyMap the property to look at
     * @param eventBeanEntryGetter the getter for the map entry
     */
    public MapMaptypedEntryPropertyGetter(String propertyMap, EventPropertyGetter eventBeanEntryGetter) {
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

        Object value = map.get(propertyMap);
        if (value == null)
        {
            return null;
        }
        if (!(value instanceof Map))
        {
            return null;
        }

        // If the map does not contain the key, this is allowed and represented as null
        EventBean eventBean = new MapEventBean((Map) value, null);
        return eventBeanEntryGetter.get(eventBean);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true; // Property exists as the property is not dynamic (unchecked)
    }

    public EventBean getFragment(EventBean eventBean)
    {
        return null; // TODO
    }

    public EventBean[] getFragmentArray(EventBean eventBean)
    {
        return null; // TODO
    }

    public Integer getIndexSize(EventBean eventBean)
    {
        return null; // TODO
    }    
}
