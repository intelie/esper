package net.esper.event;

import net.esper.event.property.*;
import net.esper.util.JavaClassHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Implementation of the {@link EventType} interface for handling plain Maps containing name value pairs.
 */
public class MapEventType implements EventType
{
    private final String typeName;
    private final String[] propertyNames;       // Cache an array of property names so not to construct one frequently
    private final Map<String, Class> types;     // Mapping of property name and type
    private Map<String, EventPropertyGetter> propertyGetters;   // Mapping of property name and getters
    private int hashCode;
    private EventAdapterService eventAdapterService;

    /**
     * Constructor takes a type name, map of property names and types.
     * @param typeName is the event type name used to distinquish map types that have the same property types,
     * empty string for anonymous maps, or for insert-into statements generating map events
     * the stream name
     * @param propertyTypes is pairs of property name and type
     * @param eventAdapterService is required for access to objects properties within map values
     */
    public MapEventType(String typeName,
                        Map<String, Class> propertyTypes,
                        EventAdapterService eventAdapterService)
    {
        this.typeName = typeName;
        this.eventAdapterService = eventAdapterService;
        // copy the property names and types
        this.types = new HashMap<String, Class>();
        this.types.putAll(propertyTypes);

        hashCode = typeName.hashCode();
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
                    return map.get(name);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true; // Property exists as the property is not dynamic (unchecked)
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
            // dynamic simple property
            if (propertyName.endsWith("?"))
            {
                return Object.class;
            }
            return null;
        }

        // Map event types allow 2 types of properties inside:
        //   - a property that is a Java object is interrogated via bean property getters and BeanEventType
        //   - a property that is a Map itself is interrogated via map property getters
        // The property getters therefore act on 

        // Take apart the nested property into a map key and a nested value class property name
        String propertyMap = propertyName.substring(0, index);
        String propertyNested = propertyName.substring(index + 1, propertyName.length());
        result = types.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        // If there is a map value in the map, return the Object value if this is a dynamic property
        if (result == Map.class)
        {
            // The inner property must be a dynamic property otherwise it cannot exist since the Map-of-Map
            // contents are undefined
            Property prop = PropertyParser.parse(propertyNested, null);
            return prop.getPropertyTypeMap();
        }
        else
        {
            // ask the nested class to resolve the property
            EventType nestedType = eventAdapterService.addBeanType(result.getName(), result);
            return nestedType.getPropertyType(propertyNested);
        }
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
            // dynamic property for maps is allowed
            Property prop = PropertyParser.parse(propertyName, null);
            if (prop instanceof DynamicProperty)
            {
                return prop.getGetterMap();
            }
            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = propertyName.substring(0, index);
        final String propertyNested = propertyName.substring(index + 1, propertyName.length());

        Class result = types.get(propertyMap);
        if (result == null)
        {
            return null;
        }

        // The map contains another map, we resolve the property dynamically
        if (result == Map.class)
        {
            // The inner property must be a dynamic property otherwise it cannot exist since the Map-of-Map
            // contents are undefined
            Property prop = PropertyParser.parse(propertyNested, null);
            EventPropertyGetter getterNestedMap = prop.getGetterMap();
            if (getterNestedMap == null)
            {
                return null;    // May not be possible as mapped property in "mapped('key')" is really nested "mapped.key"
            }
            return new MapPropertyGetter(propertyMap, getterNestedMap);
        }
        else
        {
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
                    EventBean event = MapEventType.this.eventAdapterService.adapterForBean(value);
                    return nestedGetter.get(event);
                }

                public boolean isExistsProperty(EventBean eventBean)
                {
                    return true; // Property exists as the property is not dynamic (unchecked)
                }
            };

            return getter;
        }
    }

    /**
     * Returns the value of the given property, allowing nested property names.
     * @param propertyName is the name of the property
     * @param values is the map to get the value from
     * @return property value
     */
    public Object getValue(String propertyName, Map values)
    {
        // if a known type, return value
        if (types.get(propertyName) != null)
        {
            return values.get(propertyName);
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

        // Wrap object
        Object value = values.get(propertyMap);
        if (value == null)
        {
            return null;
        }
        EventBean event = MapEventType.this.eventAdapterService.adapterForBean(value);
        return nestedGetter.get(event);
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }

    public boolean isProperty(String propertyName)
    {
        Class propertyType = getPropertyType(propertyName);
        if (propertyType == null)
        {
            // Could be a native null type, such as "insert into A select null as field..."
            if (types.containsKey(propertyName))
            {
                return true;
            }
        }
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
                "typeName=" + typeName +
                " propertyNames=" + Arrays.toString(propertyNames);
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

        // Should have the same type name
        if (!other.typeName.equals(this.typeName))
        {
            return false;
        }

        return isEqualsProperties(other.types, this.types);
    }

    public int hashCode()
    {
        return hashCode;
    }

    /**
     * Returns the event type alias.
     * @return event type alias
     */
    public String getAlias()
    {
        return typeName;
    }

    /**
     * Returns the name-type map of map properties.
     * @return is the property name and types
     */
    public Map<String, Class> getTypes()
    {
        return types;
    }

    public static boolean isEqualsProperties(Map<String, Class> setOne, Map<String, Class> setTwo)
    {
        // Should have the same number of properties
        if (setOne.size() != setTwo.size())
        {
            return false;
        }
        
        // Compare property by property
        for (Map.Entry<String, Class> entry : setOne.entrySet())
        {
            Class otherClass = setTwo.get(entry.getKey());
            Class thisClass = entry.getValue();
            if (((otherClass == null) && (thisClass != null)) ||
                 (otherClass != null) && (thisClass == null))
            {
                return false;
            }
            if (otherClass == null)
            {
                continue;
            }
            Class boxedOther = JavaClassHelper.getBoxedType(otherClass);
            Class boxedThis = JavaClassHelper.getBoxedType(thisClass);
            if (!boxedOther.equals(boxedThis))
            {
                return false;
            }
        }

        return true;
    }
}
