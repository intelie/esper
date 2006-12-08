package net.esper.event;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Implementation of the {@link EventType} interface for handling plain Maps containing name value pairs.
 */
class MapEventType implements EventType
{
    private final String[] propertyNames;       // Cache an array of property names so not to construct one frequently
    private final Map<String, Class> types;     // Mapping of property name and type
    private Map<String, EventPropertyGetter> propertyGetters;   // Mapping of property name and getters
    private int hashCode;
    private EventAdapterService eventAdapterService;

    /**
     * Constructor takes a map of property names and types.
     * @param propertyTypes is pairs of property name and type
     * @param eventAdapterService is 
     */
    public MapEventType(Map<String, Class> propertyTypes,
                        EventAdapterService eventAdapterService)
    {
        this.eventAdapterService = eventAdapterService;
        // copy the property names and types
        this.types = new HashMap<String, Class>();
        this.types.putAll(propertyTypes);

        hashCode = 0;
        propertyNames = new String[types.size()];
        propertyGetters = new HashMap<String, EventPropertyGetter>();

        // Initialize getters and names array
        int index = 0;
        for (Map.Entry<String, Class> entry : types.entrySet())
        {
            final String name = entry.getKey();
            hashCode = hashCode ^ name.hashCode();

            propertyNames[index++] = name;

            EventPropertyGetter getter = new EventPropertyGetter()
            {
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
                    Object value = map.get(name);
                    return value;
                }
            };

            propertyGetters.put(name, getter);
        }
    }

    public final Class getPropertyType(String propertyName)
    {
        Class result = types.get(propertyName);
        if (result != null)
        {
            return result;
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
        result = types.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        // ask the nested class to resolve the property
        EventType nestedType = eventAdapterService.addBeanType(result.getName(), result);
        return nestedType.getPropertyType(propertyNested);
    }

    public final Class getUnderlyingType()
    {
        return java.util.Map.class;
    }    

    public EventPropertyGetter getGetter(final String propertyName)
    {
        EventPropertyGetter getter = propertyGetters.get(propertyName);
        if (getter != null)
        {
            return getter;
        }

        // see if this is a nested property
        int index = propertyName.indexOf('.');
        if (index == -1)
        {
            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        Class result = types.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        // ask the nested class to resolve the property
        EventType nestedType = eventAdapterService.addBeanType(result.getName(), result);
        final EventPropertyGetter nestedGetter = nestedType.getGetter(propertyNested);
        if (nestedGetter == null)
        {
            return null;
        }

        // construct getter for nested property
        getter = new EventPropertyGetter()
        {
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
                Object value = map.get(propertyMap);

                if (value == null)
                {
                    return null;
                }
                
                // Wrap object
                EventBean event = MapEventType.this.eventAdapterService.adapterForBean(value);
                return nestedGetter.get(event);
            }
        };

        return getter;
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

    public String toString()
    {
        return "MapEventType " +
                "propertyNames=" + Arrays.toString(propertyNames);
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof MapEventType))
        {
            return false;
        }

        MapEventType other = (MapEventType) obj;

        // Should have the same number of properties
        if (other.types.size() != this.types.size())
        {
            return false;
        }

        // Compare property by property
        for (Map.Entry<String, Class> entry : types.entrySet())
        {
            Class otherClass = other.types.get(entry.getKey());
            if (otherClass == null)
            {
                return false;
            }
            if (!otherClass.equals(entry.getValue()))
            {
                return false;
            }
        }

        return true;
    }

    public int hashCode()
    {
        return hashCode;
    }

}