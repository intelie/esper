package com.espertech.esper.event;

import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;
import com.espertech.esper.event.property.IndexedProperty;
import com.espertech.esper.event.property.NestedProperty;
import com.espertech.esper.event.property.Property;
import com.espertech.esper.event.property.PropertyParser;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Map;
import java.util.LinkedHashSet;

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
     * Map of tag name and event type for all properties that are events.
     */
    protected final Map<String, Pair<EventType, String>> taggedEventTypes;

    /**
     * Map of tag name and event type for all properties that are array of events (multiple events same type).
     */
    protected final Map<String, Pair<EventType, String>> arrayEventTypes;

    private String alias;
    private String[] propertyNames;

    /**
     * Ctor.
     * @param alias is the event type alias
     * @param taggedEventTypes is a map of name tags and event type per tag 
     * @param arrayEventTypes is a map of name tags and event type per tag for repeat-expressions that generate an array of events
     */
    public CompositeEventType(String alias,
                              Map<String, Pair<EventType, String>> taggedEventTypes,
                              Map<String, Pair<EventType, String>> arrayEventTypes)
    {
        this.taggedEventTypes = taggedEventTypes;
        this.arrayEventTypes = arrayEventTypes;
        this.alias = alias;

        LinkedHashSet<String> propertySet = new LinkedHashSet<String>();
        propertySet.addAll(taggedEventTypes.keySet());

        for (String arrayProperty : arrayEventTypes.keySet())
        {
            if (propertySet.contains(arrayProperty))
            {
                throw new IllegalStateException("Unexpected overlap detected in composite event in tag '" + arrayProperty +
                        "' typed both event and event array");
            }
            propertySet.add(arrayProperty + "[]");
        }
        this.propertyNames = propertySet.toArray(new String[0]);
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

        // the array tag itself returns an array of the underlying, i.e. "match c=C" returns c as UnderyingObject[]
        result = arrayEventTypes.get(propertyName);
        if (result != null)
        {
            Class underlyingType = result.getFirst().getUnderlyingType();
            Object arr = Array.newInstance(underlyingType, 0);
            return arr.getClass();
        }

        // parse the property, can be index, i.e. "match c=C" returns c[0] as UnderyingObject
        Property prop = PropertyParser.parse(propertyName, null, false);
        if (prop instanceof IndexedProperty)
        {
            IndexedProperty indexProp = (IndexedProperty) prop;
            String name = indexProp.getPropertyNameAtomic();
            result = arrayEventTypes.get(name);
            if (result != null)
            {
                return result.getFirst().getUnderlyingType();
            }
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
        if (result != null)
        {
            return result.getFirst().getPropertyType(propertyNested);
        }

        // can be array, i.e. "match c=C" returns c[0].id as String
        if (prop instanceof NestedProperty)
        {
            NestedProperty nestedProp = (NestedProperty) prop;
            if (nestedProp.getProperties().get(0) instanceof IndexedProperty)
            {
                IndexedProperty indexProp = (IndexedProperty) nestedProp.getProperties().get(0);
                String name = indexProp.getPropertyNameAtomic();
                result = arrayEventTypes.get(name);
                return result.getFirst().getPropertyType(propertyNested);                
            }
        }

        return null;
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
            // check for straight property
            Pair<EventType, String> result = taggedEventTypes.get(propertyName);
            if (result != null)
            {
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

            // check for array property
            result = arrayEventTypes.get(propertyName);
            if (result != null)
            {
                // Return tag's array of underlyings
                final String tag = propertyName;
                final Class underlyingType = result.getFirst().getUnderlyingType();
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
                        EventBean[] wrapper = (EventBean[]) map.get(tag);
                        if (wrapper !=  null)
                        {
                            Object array = Array.newInstance(underlyingType, wrapper.length);
                            for (int i = 0; i < wrapper.length; i++)
                            {
                                Array.set(array, i, wrapper[i].getUnderlying());
                            }
                            return array;
                        }

                        return null;
                    }

                    public boolean isExistsProperty(EventBean eventBean)
                    {
                        return true; // Property exists as the property is not dynamic (unchecked)
                    }
                };
            }

            Property prop = PropertyParser.parse(propertyName, null, false);
            if (prop instanceof IndexedProperty)
            {
                IndexedProperty indexProp = (IndexedProperty) prop;
                String name = indexProp.getPropertyNameAtomic();
                result = arrayEventTypes.get(name);
                if (result != null)
                {
                    // Return tag's underlyings within the array index
                    final String tag = indexProp.getPropertyNameAtomic();
                    final int arrayIndex = indexProp.getIndex();
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

                            // If the map does not contain the index, this is allowed and represented as null
                            EventBean[] wrapper = (EventBean[]) map.get(tag);
                            if (wrapper !=  null)
                            {
                                if (wrapper.length > arrayIndex)
                                {
                                    return wrapper[arrayIndex].getUnderlying();
                                }
                                else
                                {
                                    return null;
                                }
                            }

                            return null;
                        }

                        public boolean isExistsProperty(EventBean eventBean)
                        {
                            return true; // Property exists as the property is not dynamic (unchecked)
                        }
                    };                    
                }
            }

            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        // handle simple property
        Pair<EventType, String> result = taggedEventTypes.get(propertyMap);
        if (result != null)
        {
            final EventPropertyGetter getterNested = result.getFirst().getGetter(propertyNested);
            if (getterNested != null)
            {
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
        }

        // can be array, i.e. "match c=C" returns c[0].id as String
        Property prop = PropertyParser.parse(propertyName, null, false);
        if (prop instanceof NestedProperty)
        {
            NestedProperty nestedProp = (NestedProperty) prop;
            if (nestedProp.getProperties().get(0) instanceof IndexedProperty)
            {
                IndexedProperty indexProp = (IndexedProperty) nestedProp.getProperties().get(0);
                final int arrayIndex = indexProp.getIndex();
                final String name = indexProp.getPropertyNameAtomic();
                result = arrayEventTypes.get(name);
                if (result != null)
                {
                    final EventPropertyGetter getterNested = result.getFirst().getGetter(propertyNested);
                    if (getterNested != null)
                    {
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

                                EventBean[] wrapper = (EventBean[]) map.get(name);
                                if (wrapper !=  null)
                                {
                                    if (wrapper.length > arrayIndex)
                                    {
                                        EventBean event = wrapper[arrayIndex];
                                        return getterNested.get(event);
                                    }
                                    else
                                    {
                                        return null;
                                    }
                                }
                                return null;
                            }
                            public boolean isExistsProperty(EventBean eventBean)
                            {
                                return true; // Property exists as the property is not dynamic (unchecked)
                            }
                        };
                    }
                }
            }
        }

        return null;
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
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

        if (!(other.arrayEventTypes.size() == arrayEventTypes.size()))
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

        for (Map.Entry<String, Pair<EventType, String>> entry : arrayEventTypes.entrySet())
        {
            EventType composed = entry.getValue().getFirst();
            Pair<EventType, String> otherComposed = other.arrayEventTypes.get(entry.getKey());

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
