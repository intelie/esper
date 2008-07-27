package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;
import com.espertech.esper.event.property.DynamicProperty;
import com.espertech.esper.event.property.MapPropertyGetter;
import com.espertech.esper.event.property.Property;
import com.espertech.esper.event.property.PropertyParser;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.GraphUtil;

import java.util.*;

/**
 * Implementation of the {@link EventType} interface for handling plain Maps containing name value pairs.
 */
public class MapEventType implements EventType
{
    private final String typeName;
    private final EventAdapterService eventAdapterService;
    private final EventType[] optionalSuperTypes;
    private final Iterator<EventType> optionalDeepSupertypesIterator;

    // Simple (not-nested) properties are stored here
    private String[] propertyNames;       // Cache an array of property names so not to construct one frequently
    private final Map<String, Class> simplePropertyTypes;     // Mapping of property name (simple-only) and type
    private final Map<String, EventPropertyGetter> propertyGetters;   // Mapping of property name and getters

    // Nestable definition of Map contents is here
    private Map<String, Object> nestableTypes;  // Deep definition of the map-type, containing nested maps and objects

    private int hashCode;

    /**
     * Constructor takes a type name, map of property names and types.
     * @param typeName is the event type name used to distinquish map types that have the same property types,
     * empty string for anonymous maps, or for insert-into statements generating map events
     * the stream name
     * @param propertyTypes is pairs of property name and type
     * @param eventAdapterService is required for access to objects properties within map values
     * @param optionalSuperTypes the supertypes to this type if any, or null if there are no supertypes
     * @param optionalDeepSupertypes the deep supertypes to this type if any, or null if there are no deep supertypes
     */
    public MapEventType(String typeName,
                        Map<String, Class> propertyTypes,
                        EventAdapterService eventAdapterService,
                        EventType[] optionalSuperTypes,
                        Set<EventType> optionalDeepSupertypes)
    {
        this.typeName = typeName;
        this.eventAdapterService = eventAdapterService;

        // copy the property names and types (simple-properties only)
        this.nestableTypes = new HashMap<String, Object>();
        this.nestableTypes.putAll(propertyTypes);
        this.simplePropertyTypes = new HashMap<String, Class>();
        this.simplePropertyTypes.putAll(propertyTypes);

        this.optionalSuperTypes = optionalSuperTypes;
        if (optionalDeepSupertypes == null)
        {
            optionalDeepSupertypesIterator = null;
        }
        else
        {
            optionalDeepSupertypesIterator = optionalDeepSupertypes.iterator();
        }

        hashCode = typeName.hashCode();
        propertyNames = new String[simplePropertyTypes.size()];
        propertyGetters = new HashMap<String, EventPropertyGetter>();

        // Initialize getters and names array
        int index = 0;
        for (Map.Entry<String, Class> entry : simplePropertyTypes.entrySet())
        {
            final String name = entry.getKey();
            hashCode = hashCode ^ name.hashCode();

            EventPropertyGetter getter = new MapEventPropertyGetter(name);
            propertyGetters.put(name, getter);
            propertyNames[index++] = name;
        }

        // Copy parent properties to child
        copySuperTypes();
    }

    /**
     * Constructor takes a type name, map of property names and types, for
     * use with nestable Map events.
     * @param typeName is the event type name used to distinquish map types that have the same property types,
     * empty string for anonymous maps, or for insert-into statements generating map events
     * the stream name
     * @param propertyTypes is pairs of property name and type
     * @param eventAdapterService is required for access to objects properties within map values
     * @param optionalSuperTypes the supertypes to this type if any, or null if there are no supertypes
     * @param optionalDeepSupertypes the deep supertypes to this type if any, or null if there are no deep supertypes
     */
    public MapEventType(String typeName,
                        EventAdapterService eventAdapterService,
                        Map<String, Object> propertyTypes,
                        EventType[] optionalSuperTypes,
                        Set<EventType> optionalDeepSupertypes)
    {
        this.typeName = typeName;
        this.eventAdapterService = eventAdapterService;

        this.nestableTypes = new HashMap<String, Object>();
        this.nestableTypes.putAll(propertyTypes);

        this.optionalSuperTypes = optionalSuperTypes;
        if (optionalDeepSupertypes == null)
        {
            optionalDeepSupertypesIterator = null;
        }
        else
        {
            optionalDeepSupertypesIterator = optionalDeepSupertypes.iterator();
        }

        // determine property set and prepare getters
        PropertySetDescriptor propertySet = getNestableMapProperties(propertyTypes);
        List<String> propertyNameList = propertySet.getPropertyNameList();
        propertyNames = propertyNameList.toArray(new String[propertyNameList.size()]);
        propertyGetters = propertySet.getPropertyGetters();
        simplePropertyTypes = propertySet.getSimplePropertyTypes();

        hashCode = typeName.hashCode();
        for (Map.Entry<String, Class> entry : simplePropertyTypes.entrySet())
        {
            hashCode = hashCode ^ entry.getKey().hashCode();
        }

        // Copy parent properties to child
        copySuperTypes();
    }

    public final Class getPropertyType(String propertyName)
    {
        Class result = simplePropertyTypes.get(ASTFilterSpecHelper.unescapeDot(propertyName));
        if (result != null)
        {
            return result;
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
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
        String propertyMap = ASTFilterSpecHelper.unescapeDot(propertyName.substring(0, index));
        String propertyNested = propertyName.substring(index + 1, propertyName.length());
        boolean isRootedDynamic = false;

        // If the property is dynamic, remove the ? since the property type is defined without
        if (propertyMap.endsWith("?"))
        {
            propertyMap = propertyMap.substring(0, propertyMap.length() - 1);
            isRootedDynamic = true;
        }

        Object nestedType = nestableTypes.get(propertyMap);
        if (nestedType == null)
        {
            return null;
        }

        // If there is a map value in the map, return the Object value if this is a dynamic property
        if (nestedType == Map.class)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            return prop.getPropertyTypeMap(null);   // we don't have a definition of the nested props
        }
        else if (nestedType instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            Map nestedTypes = (Map) nestedType;
            return prop.getPropertyTypeMap(nestedTypes);
        }
        else if (nestedType instanceof Class)
        {
            Class simpleClass = (Class) nestedType;
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass);
            return nestedEventType.getPropertyType(propertyNested);
        }
        else if (nestedType instanceof EventType)
        {
            EventType innerType = (EventType) nestedType;
            return innerType.getPropertyType(propertyNested);
        }
        else
        {
            String message = "Nestable map type configuration encountered an unexpected value type of '"
                + nestedType.getClass() + " for property '" + propertyName + "', expected Class, Map.class or Map<String, Object> as value type";
            throw new PropertyAccessException(message);
        }
    }

    public final Class getUnderlyingType()
    {
        return java.util.Map.class;
    }

    public EventPropertyGetter getGetter(final String propertyName)
    {
        String unescapePropName = ASTFilterSpecHelper.unescapeDot(propertyName);
        EventPropertyGetter getter = propertyGetters.get(unescapePropName);
        if (getter != null)
        {
            return getter;
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            // dynamic property for maps is allowed
            BeanEventTypeFactory beanFactory = null;
            if (eventAdapterService != null)
            {
                beanFactory = eventAdapterService.getBeanEventTypeFactory();
            }

            Property prop = PropertyParser.parse(propertyName, beanFactory, false);
            if (prop instanceof DynamicProperty)
            {
                return prop.getGetterMap(null);
            }
            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        String propertyMap = ASTFilterSpecHelper.unescapeDot(propertyName.substring(0, index));
        String propertyNested = propertyName.substring(index + 1, propertyName.length());
        boolean isRootedDynamic = false;

        // If the property is dynamic, remove the ? since the property type is defined without
        if (propertyMap.endsWith("?"))
        {
            propertyMap = propertyMap.substring(0, propertyMap.length() - 1);
            isRootedDynamic = true;
        }

        Object nestedType = nestableTypes.get(propertyMap);
        if (nestedType == null)
        {
            return null;
        }

        // The map contains another map, we resolve the property dynamically
        if (nestedType == Map.class)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            EventPropertyGetter getterNestedMap = prop.getGetterMap(null);
            if (getterNestedMap == null)
            {
                return null;
            }
            return new MapPropertyGetter(propertyMap, getterNestedMap);
        }
        else if (nestedType instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            Map nestedTypes = (Map) nestedType;
            EventPropertyGetter getterNestedMap = prop.getGetterMap(nestedTypes);
            if (getterNestedMap == null)
            {
                return null;
            }
            return new MapPropertyGetter(propertyMap, getterNestedMap);
        }
        else if (nestedType instanceof Class)
        {
            // ask the nested class to resolve the property
            Class simpleClass = (Class) nestedType;
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass);
            final EventPropertyGetter nestedGetter = nestedEventType.getGetter(propertyNested);
            if (nestedGetter == null)
            {
                return null;
            }

            // construct getter for nested property
            getter = new MapPOJOEntryPropertyGetter(propertyMap, nestedGetter, eventAdapterService);

            return getter;
        }
        else if (nestedType instanceof EventType)
        {
            // ask the nested class to resolve the property
            EventType innerType = (EventType) nestedType;
            final EventPropertyGetter nestedGetter = innerType.getGetter(propertyNested);
            if (nestedGetter == null)
            {
                return null;
            }

            // construct getter for nested property
            getter = new MapEventBeanEntryPropertyGetter(propertyMap, nestedGetter);

            return getter;
        }
        else
        {
            String message = "Nestable map type configuration encountered an unexpected value type of '"
                + nestedType.getClass() + " for property '" + propertyName + "', expected Class, Map.class or Map<String, Object> as value type";
            throw new PropertyAccessException(message);            
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
        if (simplePropertyTypes.get(ASTFilterSpecHelper.unescapeDot(propertyName)) != null)
        {
            return values.get(ASTFilterSpecHelper.unescapeDot(propertyName));
        }

        // see if this is a nested property
        int index = ASTFilterSpecHelper.unescapedIndexOfDot(propertyName);
        if (index == -1)
        {
            return null;
        }

        // Take apart the nested property into a map key and a nested value class property name
        final String propertyMap = ASTFilterSpecHelper.unescapeDot(propertyName.substring(0, index));
        String propertyNested = propertyName.substring(index + 1, propertyName.length());

        Class result = simplePropertyTypes.get(propertyMap);
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
            if (simplePropertyTypes.containsKey(ASTFilterSpecHelper.unescapeDot(propertyName)))
            {
                return true;
            }
        }
        return propertyType != null;
    }

    public EventType[] getSuperTypes()
    {
        return optionalSuperTypes;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return optionalDeepSupertypesIterator;
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

        return isDeepEqualsProperties(other.nestableTypes, this.nestableTypes);
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
     * Returns the name-type map of map properties, each value in the map
     * can be a Class or a Map<String, Object> (for nested maps).
     * @return is the property name and types
     */
    public Map<String, Object> getTypes()
    {
        return this.nestableTypes;
    }

    /**
     * Compares two sets of properties and determines if they are the same, allowing for
     * boxed/unboxed types.
     * @param setOne is the first set of properties
     * @param setTwo is the second set of properties
     * @return true if the property set is equivalent, false if not
     */
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

    /**
     * Adds additional properties that do not yet exist on the given type.
     * <p.
     * Ignores properties already present. Allows nesting.
     * @param typeMap properties to add
     */
    public void addAdditionalProperties(Map<String, Object> typeMap)
    {
        // merge type graphs
        nestableTypes = GraphUtil.mergeNestableMap(typeMap, nestableTypes);

        // construct getters and types for each property (new or old)
        PropertySetDescriptor propertySet = getNestableMapProperties(typeMap);

        // add each that is not already present
        List<String> newPropertyNames = new ArrayList<String>();
        for (String propertyName : propertySet.getPropertyNameList())
        {
            if (propertyGetters.containsKey(propertyName))  // not a new property
            {
                continue;
            }
            newPropertyNames.add(propertyName);
            propertyGetters.put(propertyName, propertySet.getPropertyGetters().get(propertyName));
            simplePropertyTypes.put(propertyName, propertySet.getSimplePropertyTypes().get(propertyName));
        }

        String[] allPropertyNames = new String[propertyNames.length + newPropertyNames.size()];
        System.arraycopy(propertyNames, 0, allPropertyNames, 0, propertyNames.length);
        int count = propertyNames.length;
        for (String newProperty : newPropertyNames)
        {
            allPropertyNames[count++] = newProperty;
        }
        propertyNames = allPropertyNames;
    }

    /**
     * Compares two sets of properties and determines if they are the same, allowing for
     * boxed/unboxed types, and nested map types.
     * @param setOne is the first set of properties
     * @param setTwo is the second set of properties
     * @return true if the property set is equivalent, false if not
     */
    public static boolean isDeepEqualsProperties(Map<String, Object> setOne, Map<String, Object> setTwo)
    {
        // Should have the same number of properties
        if (setOne.size() != setTwo.size())
        {
            return false;
        }

        // Compare property by property
        for (Map.Entry<String, Object> entry : setOne.entrySet())
        {
            Object setTwoType = setTwo.get(entry.getKey());
            Object setOneType = entry.getValue();
            if (((setTwoType == null) && (setOneType != null)) ||
                 (setTwoType != null) && (setOneType == null))
            {
                return false;
            }
            if (setTwoType == null)
            {
                continue;
            }

            if ((setTwoType instanceof Class) && (setOneType instanceof Class))
            {
                Class boxedOther = JavaClassHelper.getBoxedType((Class)setTwoType);
                Class boxedThis = JavaClassHelper.getBoxedType((Class)setOneType);
                if (!boxedOther.equals(boxedThis))
                {
                    return false;
                }
            }
            else if ((setTwoType instanceof Map) && (setOneType instanceof Map))
            {
                boolean isDeepEqual = isDeepEqualsProperties((Map<String, Object>)setOneType, (Map<String, Object>)setTwoType);
                if (!isDeepEqual)
                {
                    return false;
                }
            }
            else if ((setTwoType instanceof EventType) && (setOneType instanceof EventType))
            {
                if (!setOneType.equals(setTwoType))
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }

        return true;
    }

    private static void generateExceptionNestedProp(String name, Object value) throws EPException
    {
        String clazzName = (value == null) ? "null" : value.getClass().getSimpleName();
        throw new EPException("Nestable map type configuration encountered an unexpected property type of '"
            + clazzName + "' for property '" + name + "', expected java.lang.Class or java.util.Map");
    }

    private void copySuperTypes()
    {
        if (optionalSuperTypes != null)
        {
            Set<String> allProperties = new LinkedHashSet<String>(Arrays.asList(propertyNames));
            for (int i = 0; i < optionalSuperTypes.length; i++)
            {
                allProperties.addAll(Arrays.asList(optionalSuperTypes[i].getPropertyNames()));
                MapEventType mapSuperType = (MapEventType) optionalSuperTypes[i];
                simplePropertyTypes.putAll(mapSuperType.simplePropertyTypes);
                propertyGetters.putAll(mapSuperType.propertyGetters);
                nestableTypes.putAll(mapSuperType.nestableTypes);
            }
            propertyNames = allProperties.toArray(new String[allProperties.size()]);
        }
    }

    private static PropertySetDescriptor getNestableMapProperties(Map<String, Object> propertiesToAdd)
            throws EPException
    {
        List<String> propertyNameList = new ArrayList<String>();
        Map<String, Class> simplePropertyTypes = new HashMap<String, Class>();
        Map<String, EventPropertyGetter> propertyGetters = new HashMap<String, EventPropertyGetter>();

        // Initialize getters and names array: at this time we do not care about nested types,
        // these are handled at the time someone is asking for them
        for (Map.Entry<String, Object> entry : propertiesToAdd.entrySet())
        {
            if (!(entry.getKey() instanceof String))
            {
                throw new EPException("Invalid map type configuration: property name is not a String-type value");
            }
            String name = entry.getKey();

            if (entry.getValue() instanceof Class)
            {
                simplePropertyTypes.put(name, (Class) entry.getValue());
                propertyNameList.add(name);
                EventPropertyGetter getter = new MapEventPropertyGetter(name);
                propertyGetters.put(name, getter);
                continue;
            }

            // A null-type is also allowed
            if (entry.getValue() == null)
            {
                simplePropertyTypes.put(name, null);
                propertyNameList.add(name);
                EventPropertyGetter getter = new MapEventPropertyGetter(name);
                propertyGetters.put(name, getter);
                continue;
            }

            if (entry.getValue() instanceof Map)
            {
                // Add Map itself as a property
                simplePropertyTypes.put(name, Map.class);
                propertyNameList.add(name);
                EventPropertyGetter getter = new MapEventPropertyGetter(name);
                propertyGetters.put(name, getter);
                continue;
            }

            if (!(entry.getValue() instanceof EventType))
            {
                generateExceptionNestedProp(name, entry.getValue());
            }

            // Add EventType itself as a property
            EventType eventType = (EventType) entry.getValue();
            simplePropertyTypes.put(name, eventType.getUnderlyingType());
            propertyNameList.add(name);
            EventPropertyGetter getter = new MapEventBeanPropertyGetter(name);
            propertyGetters.put(name, getter);
        }

        return new PropertySetDescriptor(propertyNameList, simplePropertyTypes, propertyGetters);
    }

    /**
     * Descriptor of a property set.
     */
    public static class PropertySetDescriptor
    {
        private final List<String> propertyNameList;
        private final Map<String, Class> simplePropertyTypes;
        private final Map<String, EventPropertyGetter> propertyGetters;

        /**
         * Ctor.
         * @param propertyNameList property name list
         * @param simplePropertyTypes property types
         * @param propertyGetters property getters
         */
        public PropertySetDescriptor(List<String> propertyNameList, Map<String, Class> simplePropertyTypes, Map<String, EventPropertyGetter> propertyGetters)
        {
            this.propertyNameList = propertyNameList;
            this.simplePropertyTypes = simplePropertyTypes;
            this.propertyGetters = propertyGetters;
        }

        /**
         * Returns map of property name and class.
         * @return property name and class
         */
        public Map<String, Class> getSimplePropertyTypes()
        {
            return simplePropertyTypes;
        }

        /**
         * Returns map of property name and getter.
         * @return property name and getter
         */
        public Map<String, EventPropertyGetter> getPropertyGetters()
        {
            return propertyGetters;
        }

        /**
         * Returns property name list.
         * @return property name list
         */
        public List<String> getPropertyNameList()
        {
            return propertyNameList;
        }
    }
}
