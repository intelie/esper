package com.espertech.esper.event;

import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.PropertyAccessException;

import java.util.Map;

public class MapMaptypedArrayPropertyGetter implements EventPropertyGetter
{
    private final String propertyName;
    private final EventType fragmentEventType;
    private final EventAdapterService eventAdapterService;

    public MapMaptypedArrayPropertyGetter(String propertyNameAtomic, EventType fragmentEventType, EventAdapterService eventAdapterService)
    {
        this.propertyName = propertyNameAtomic;
        this.fragmentEventType = fragmentEventType;
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

    public Object getFragment(EventBean eventBean) throws PropertyAccessException
    {
        Object value = get(eventBean);
        if (!(value instanceof Map[]))
        {
            return null;
        }
        Map[] mapTypedSubEvents = (Map[]) value;

        int countNull = 0;
        for (Map map : mapTypedSubEvents)
        {
            if (map != null)
            {
                countNull++;
            }
        }

        EventBean[] mapEvents = new EventBean[countNull];
        int count = 0;
        for (Map map : mapTypedSubEvents)
        {
            if (map != null)
            {
                mapEvents[count++] = eventAdapterService.adaptorForMap(map, fragmentEventType);
            }
        }

        return mapEvents;
    }
}
