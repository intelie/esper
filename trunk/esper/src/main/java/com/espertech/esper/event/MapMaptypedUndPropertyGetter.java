package com.espertech.esper.event;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.EventType;

import java.util.Map;

public class MapMaptypedUndPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;
    private final EventAdapterService eventAdapterService;
    private final EventType fragmentType;

    public MapMaptypedUndPropertyGetter(String propertyNameAtomic, EventAdapterService eventAdapterService, EventType fragmentType)
    {
        propertyName = propertyNameAtomic;
        this.fragmentType = fragmentType;
        this.eventAdapterService = eventAdapterService;
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

        return map.get(propertyName);
    }

    public boolean isExistsProperty(EventBean eventBean)
    {
        return true;
    }

    public Object getFragment(EventBean obj) throws PropertyAccessException
    {
        Map<String, Object> value = (Map<String, Object>) get(obj);

        if (value == null)
        {
            return null;
        }

        return eventAdapterService.adaptorForMap(value, fragmentType);
    }
}
