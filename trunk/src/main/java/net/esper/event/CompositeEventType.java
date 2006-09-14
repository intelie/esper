package net.esper.event;

import java.util.Iterator;
import java.util.Map;

public class CompositeEventType implements EventType
{
    private final Map<String, EventType> taggedEventTypes;

    public CompositeEventType(Map<String, EventType> taggedEventTypes)
    {
        this.taggedEventTypes = taggedEventTypes;
    }

    public Class getPropertyType(String propertyName)
    {
        EventType result = taggedEventTypes.get(propertyName);
        if (result != null)
        {
            return result.getUnderlyingType();
        }

        // see if this is a nested property
        int index = propertyName.indexOf('.');
        if (index == -1)
        {
            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());
        result = taggedEventTypes.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        // ask the nested class to resolve the property
        return result.getPropertyType(propertyNested);
    }

    public Class getUnderlyingType()
    {
        return java.util.Map.class;
    }

    public EventPropertyGetter getGetter(String propertyName)
    {
        // see if this is a nested property
        int index = propertyName.indexOf('.');
        if (index == -1)
        {
            EventType result = taggedEventTypes.get(propertyName);
            if (result == null)
            {
                return null;
            }

            // Not a nested property, return tag's underlying value
            final String tag = propertyName;
            return new EventPropertyGetter() {
                public Object get(EventBean obj)
                {
                    // The underlying is expected to be a map
                    if (!(obj.getUnderlying() instanceof Map))
                    {
                        throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                                "the underlying data object is not of type java.lang.Map");
                    }

                    Map map = (Map) obj.getUnderlying();

                    // If the map does not contain the key, this is allowed and represented as null
                    EventBean wrapper = (EventBean) map.get(tag);
                    if (wrapper !=  null)
                    {
                        return wrapper.getUnderlying();
                    }

                    return null;
                }
            };
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        EventType result = taggedEventTypes.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        final EventPropertyGetter getterNested = result.getGetter(propertyNested);
        if (getterNested == null)
        {
            return null;
        }

        return new EventPropertyGetter() {
            public Object get(EventBean obj)
            {
                // The underlying is expected to be a map
                if (!(obj.getUnderlying() instanceof Map))
                {
                    throw new PropertyAccessException("Mismatched property getter to event bean type, " +
                            "the underlying data object is not of type java.lang.Map");
                }

                Map map = (Map) obj.getUnderlying();

                // If the map does not contain the key, this is allowed and represented as null
                EventBean wrapper = (EventBean) map.get(propertyMap);
                if (wrapper !=  null)
                {
                    return getterNested.get(wrapper);
                }

                return null;
            }
        };
    }

    public String[] getPropertyNames()
    {
        return taggedEventTypes.keySet().toArray(new String[0]);
    }

    public boolean isProperty(String propertyName)
    {
        Class propertyType = getPropertyType(propertyName);
        return propertyType != null;
    }

    public EventType[] getSuperTypes()
    {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return null;
    }
}
