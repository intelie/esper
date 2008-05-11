package com.espertech.esper.event;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;

import java.util.Iterator;
import java.util.Map;

/**
 * Event type for events that itself have event properties that are event wrappers.
 * <p>
 * For use in pattern expression statements in which multiple events match a pattern. There the
 * composite event indicates that the whole patterns matched, and indicates the
 * individual events that caused the pattern as event properties to the event.
 */
public class CompositeEventType implements EventType, TaggedCompositeEventType
{
    /**
     * Map of tag name and event type.
     */
    protected final Map<String, Pair<EventType, String>> taggedEventTypes;
    
    private String alias;

    /**
     * Ctor.
     * @param alias is the event type alias
     * @param taggedEventTypes is a map of name tags and event type per tag 
     */
    public CompositeEventType(String alias, Map<String, Pair<EventType, String>> taggedEventTypes)
    {
        this.taggedEventTypes = taggedEventTypes;
        this.alias = alias;
    }

    /**
     * Returns the event type alias.
     * @return event type alias 
     */
    public String getAlias()
    {
        return alias;
    }

    public Class getPropertyType(String propertyName)
    {
        Pair<EventType, String> result = taggedEventTypes.get(propertyName);
        if (result != null)
        {
            return result.getFirst().getUnderlyingType();
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
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
        return result.getFirst().getPropertyType(propertyNested);
    }

    public Class getUnderlyingType()
    {
        return java.util.Map.class;
    }

    public EventPropertyGetter getGetter(String propertyName)
    {
        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            Pair<EventType, String> result = taggedEventTypes.get(propertyName);
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

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true; // Property exists as the property is not dynamic (unchecked)
                }
            };
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        Pair<EventType, String> result = taggedEventTypes.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        final EventPropertyGetter getterNested = result.getFirst().getGetter(propertyNested);
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
            public boolean isExistsProperty(EventBean eventBean)
            {
                return true; // Property exists as the property is not dynamic (unchecked)
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

    public boolean equals(Object obj)
    {
        if (!(obj instanceof CompositeEventType))
        {
            return false;
        }

        CompositeEventType other = (CompositeEventType) obj;
        // Composite event types are always anonymous therefore not checking alias name

        if (!(other.taggedEventTypes.size() == taggedEventTypes.size()))
        {
            return false;
        }

        for (Map.Entry<String, Pair<EventType, String>> entry : taggedEventTypes.entrySet())
        {
            EventType composed = entry.getValue().getFirst();
            Pair<EventType, String> otherComposed = other.taggedEventTypes.get(entry.getKey());

            if (otherComposed == null)
            {
                return false;
            }
            EventType otherComposedType = otherComposed.getFirst();
            if (!(composed.equals(otherComposedType)))
            {
                return false;
            }
        }

        return true;
    }

    public int hashCode()
    {
        return alias.hashCode();
    }

    public Map<String, Pair<EventType, String>> getTaggedEventTypes()
    {
        return taggedEventTypes;
    }
}
