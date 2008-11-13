/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.EPException;
import com.espertech.esper.epl.parse.ASTFilterSpecHelper;
import com.espertech.esper.event.property.*;
import com.espertech.esper.util.GraphUtil;
import com.espertech.esper.util.JavaClassHelper;

import java.util.*;

/**
 * Implementation of the {@link EventType} interface for handling plain Maps containing name value pairs.
 */
public class MapEventType implements EventTypeSPI
{
    private final EventTypeMetadata metadata;
    private final String typeName;
    private final EventAdapterService eventAdapterService;
    private final EventType[] optionalSuperTypes;
    private final Set<EventType> optionalDeepSupertypes;

    // Simple (not-nested) properties are stored here
    private String[] propertyNames;       // Cache an array of property names so not to construct one frequently
    private final Map<String, Class> simplePropertyTypes;     // Mapping of property name (simple-only) and type
    private final Map<String, EventPropertyGetter> propertyGetters;   // Mapping of simple property name and getters
    private final Map<String, EventPropertyGetter> propertyGetterCache; // Mapping of all property names and getters

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
     * @param metadata event type metadata
     */
    public MapEventType(EventTypeMetadata metadata,
                        String typeName,
                        Map<String, Class> propertyTypes,
                        EventAdapterService eventAdapterService,
                        EventType[] optionalSuperTypes,
                        Set<EventType> optionalDeepSupertypes)
    {
        this.metadata = metadata;
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
            this.optionalDeepSupertypes = Collections.EMPTY_SET;
        }
        else
        {
            this.optionalDeepSupertypes = optionalDeepSupertypes;
        }

        hashCode = typeName.hashCode();
        propertyNames = new String[simplePropertyTypes.size()];
        propertyGetters = new HashMap<String, EventPropertyGetter>();
        propertyGetterCache = new HashMap<String, EventPropertyGetter>();

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
     * @param metadata event type metadata
     */
    public MapEventType(EventTypeMetadata metadata,
                        String typeName,
                        EventAdapterService eventAdapterService,
                        Map<String, Object> propertyTypes,
                        EventType[] optionalSuperTypes,
                        Set<EventType> optionalDeepSupertypes)
    {
        this.metadata = metadata;
        this.typeName = typeName;
        this.eventAdapterService = eventAdapterService;

        this.nestableTypes = new HashMap<String, Object>();
        this.nestableTypes.putAll(propertyTypes);

        this.optionalSuperTypes = optionalSuperTypes;
        if (optionalDeepSupertypes == null)
        {
            this.optionalDeepSupertypes = Collections.EMPTY_SET;
        }
        else
        {
            this.optionalDeepSupertypes = optionalDeepSupertypes;
        }

        // determine property set and prepare getters
        PropertySetDescriptor propertySet = getNestableMapProperties(propertyTypes, eventAdapterService);
        List<String> propertyNameList = propertySet.getPropertyNameList();
        propertyNames = propertyNameList.toArray(new String[propertyNameList.size()]);
        propertyGetters = propertySet.getPropertyGetters();
        propertyGetterCache = new HashMap<String, EventPropertyGetter>();
        simplePropertyTypes = propertySet.getSimplePropertyTypes();

        hashCode = typeName.hashCode();
        for (Map.Entry<String, Class> entry : simplePropertyTypes.entrySet())
        {
            hashCode = hashCode ^ entry.getKey().hashCode();
        }

        // Copy parent properties to child
        copySuperTypes();
    }

    public String getName()
    {
        return metadata.getPublicName();
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

            // parse, can be an indexed property
            Property property = PropertyParser.parse(propertyName, eventAdapterService.getBeanEventTypeFactory(), false);
            if (!(property instanceof IndexedProperty))
            {
                return null;
            }
            IndexedProperty indexedProp = (IndexedProperty) property;
            Object type = nestableTypes.get(indexedProp.getPropertyNameAtomic());
            if (type == null)
            {
                return null;
            }
            if (!(type instanceof Class))
            {
                return null;
            }
            if (!((Class) type).isArray())
            {
                return null;
            }
            // its an array
            return ((Class)type).getComponentType();
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
            // parse, can be an indexed property
            Property property = PropertyParser.parse(propertyMap, eventAdapterService.getBeanEventTypeFactory(), false);
            if (!(property instanceof IndexedProperty))
            {
                return null;
            }
            IndexedProperty indexedProp = (IndexedProperty) property;
            Object type = nestableTypes.get(indexedProp.getPropertyNameAtomic());
            if (type == null)
            {
                return null;
            }
            // handle map-in-map case
            if (type instanceof String) {
                String propTypeName = type.toString();
                boolean isArray = isPropertyArray(propTypeName);
                if (isArray) {
                    propTypeName = getPropertyRemoveArray(propTypeName);
                }
                EventType innerType = eventAdapterService.getExistsTypeByAlias(propTypeName);
                if (!(innerType instanceof MapEventType))
                {
                    return null;
                }
                return innerType.getPropertyType(propertyNested);
            }
            // handle array class in map case
            else
            {
                if (!(type instanceof Class))
                {
                    return null;
                }
                if (!((Class) type).isArray())
                {
                    return null;
                }
                Class componentType = ((Class) type).getComponentType();
                EventType nestedEventType = eventAdapterService.addBeanType(componentType.getName(), componentType, false);
                return nestedEventType.getPropertyType(propertyNested);
            }
        }

        // If there is a map value in the map, return the Object value if this is a dynamic property
        if (nestedType == Map.class)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            return prop.getPropertyTypeMap(null, eventAdapterService);   // we don't have a definition of the nested props
        }
        else if (nestedType instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            Map nestedTypes = (Map) nestedType;
            return prop.getPropertyTypeMap(nestedTypes, eventAdapterService);
        }
        else if (nestedType instanceof Class)
        {
            Class simpleClass = (Class) nestedType;
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass, false);
            return nestedEventType.getPropertyType(propertyNested);
        }
        else if (nestedType instanceof EventType)
        {
            EventType innerType = (EventType) nestedType;
            return innerType.getPropertyType(propertyNested);
        }
        else if (nestedType instanceof String)
        {
            String nestedName = nestedType.toString();
            boolean isArray = isPropertyArray(nestedName);
            if (isArray) {
                nestedName = getPropertyRemoveArray(nestedName);
            }
            EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
            if (!(innerType instanceof MapEventType))
            {
                return null;
            }
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
        EventPropertyGetter cachedGetter = propertyGetterCache.get(propertyName);
        if (cachedGetter != null)
        {
            return cachedGetter;
        }

        String unescapePropName = ASTFilterSpecHelper.unescapeDot(propertyName);
        EventPropertyGetter getter = propertyGetters.get(unescapePropName);
        if (getter != null)
        {
            propertyGetterCache.put(propertyName, getter);
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
                EventPropertyGetter getterDyn = prop.getGetterMap(null, eventAdapterService);
                propertyGetterCache.put(propertyName, getterDyn);
                return getterDyn;
            }
            if (!(prop instanceof IndexedProperty))
            {
                return null;
            }
            IndexedProperty indexedProp = (IndexedProperty) prop;
            Object type = nestableTypes.get(indexedProp.getPropertyNameAtomic());
            if (type == null)
            {
                return null;
            }
            // handle map type name in map
            if (!(type instanceof Class))
            {
                return null;
            }
            if (!((Class) type).isArray())
            {
                return null;
            }

            // its an array
            EventPropertyGetter indexedGetter = new MapArrayPOJOEntryIndexedPropertyGetter(indexedProp.getPropertyNameAtomic(), indexedProp.getIndex());
            propertyGetterCache.put(propertyName, indexedGetter);
            return indexedGetter;
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
            // parse, can be an indexed property
            Property property = PropertyParser.parse(propertyMap, eventAdapterService.getBeanEventTypeFactory(), false);
            if (!(property instanceof IndexedProperty))
            {
                return null;
            }
            IndexedProperty indexedProp = (IndexedProperty) property;
            Object type = nestableTypes.get(indexedProp.getPropertyNameAtomic());
            if (type == null)
            {
                return null;
            }
            if (type instanceof String)
            {
                String nestedTypeName = type.toString();
                boolean isArray = isPropertyArray(nestedTypeName);
                if (isArray) {
                    nestedTypeName = getPropertyRemoveArray(nestedTypeName);
                }
                EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedTypeName);
                if (!(innerType instanceof MapEventType))
                {
                    return null;
                }
                EventPropertyGetter typeGetter;
                if (!isArray)
                {
                    typeGetter = new MapMaptypedEntryPropertyGetter(propertyMap, innerType.getGetter(propertyNested));
                }
                else
                {
                    typeGetter = new MapArrayMaptypedEntryPropertyGetter(indexedProp.getPropertyNameAtomic(), indexedProp.getIndex(), innerType.getGetter(propertyNested));
                }
                propertyGetterCache.put(propertyName, typeGetter);
                return typeGetter;
            }
            else
            {
                if (!(type instanceof Class))
                {
                    return null;
                }
                if (!((Class) type).isArray())
                {
                    return null;
                }
                Class componentType = ((Class) type).getComponentType();
                EventType nestedEventType = eventAdapterService.addBeanType(componentType.getName(), componentType, false);

                final EventPropertyGetter nestedGetter = nestedEventType.getGetter(propertyNested);
                if (nestedGetter == null)
                {
                    return null;
                }
                // construct getter for nested property
                EventPropertyGetter indexGetter = new MapArrayPOJOBeanEntryIndexedPropertyGetter(indexedProp.getPropertyNameAtomic(), indexedProp.getIndex(), nestedGetter, eventAdapterService);
                propertyGetterCache.put(propertyName, indexGetter);
                return indexGetter;
            }
        }

        // The map contains another map, we resolve the property dynamically
        if (nestedType == Map.class)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            EventPropertyGetter getterNestedMap = prop.getGetterMap(null, eventAdapterService);
            if (getterNestedMap == null)
            {
                return null;
            }
            EventPropertyGetter mapGetter = new MapPropertyGetter(propertyMap, getterNestedMap);
            propertyGetterCache.put(propertyName, mapGetter);
            return mapGetter;
        }
        else if (nestedType instanceof Map)
        {
            Property prop = PropertyParser.parse(propertyNested, eventAdapterService.getBeanEventTypeFactory(), isRootedDynamic);
            Map nestedTypes = (Map) nestedType;
            EventPropertyGetter getterNestedMap = prop.getGetterMap(nestedTypes, eventAdapterService);
            if (getterNestedMap == null)
            {
                return null;
            }
            EventPropertyGetter mapGetter = new MapPropertyGetter(propertyMap, getterNestedMap); 
            propertyGetterCache.put(propertyName, mapGetter);
            return mapGetter;
        }
        else if (nestedType instanceof Class)
        {
            // ask the nested class to resolve the property
            Class simpleClass = (Class) nestedType;
            EventType nestedEventType = eventAdapterService.addBeanType(simpleClass.getName(), simpleClass, false);
            final EventPropertyGetter nestedGetter = nestedEventType.getGetter(propertyNested);
            if (nestedGetter == null)
            {
                return null;
            }

            // construct getter for nested property
            getter = new MapPOJOEntryPropertyGetter(propertyMap, nestedGetter, eventAdapterService);
            propertyGetterCache.put(propertyName, getter);
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
            propertyGetterCache.put(propertyName, getter);
            return getter;
        }
        else if (nestedType instanceof String)
        {
            String nestedName = nestedType.toString();
            boolean isArray = isPropertyArray(nestedName);
            if (isArray) {
                nestedName = getPropertyRemoveArray(nestedName);
            }
            EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
            if (!(innerType instanceof MapEventType))
            {
                return null;
            }
            EventPropertyGetter maptypeGetter;
            if (!isArray)
            {
                maptypeGetter = new MapMaptypedEntryPropertyGetter(propertyMap, innerType.getGetter(propertyNested));
            }
            else
            {
                maptypeGetter = new MapArrayMaptypedEntryPropertyGetter(propertyMap, 0, innerType.getGetter(propertyNested));
            }
            propertyGetterCache.put(propertyName, maptypeGetter);
            return maptypeGetter;
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
        EventType nestedType = eventAdapterService.addBeanType(result.getName(), result, false);
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
        return optionalDeepSupertypes.iterator();
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

        if (!(obj instanceof EventType))
        {
            return false;
        }

        String message = getEqualsMessage((EventType)obj);
        return message == null;
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
     * @param eventAdapterService for resolving further map event types that are property types
     */
    public void addAdditionalProperties(Map<String, Object> typeMap, EventAdapterService eventAdapterService)
    {
        // merge type graphs
        nestableTypes = GraphUtil.mergeNestableMap(typeMap, nestableTypes);

        // construct getters and types for each property (new or old)
        PropertySetDescriptor propertySet = getNestableMapProperties(typeMap, eventAdapterService);

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
     * @param otherName name of the type compared to
     * @return null if the property set is equivalent or message if not
     */
    public static String isDeepEqualsProperties(String otherName, Map<String, Object> setOne, Map<String, Object> setTwo)
    {
        // Should have the same number of properties
        if (setOne.size() != setTwo.size())
        {
            return "Type by name '" + otherName + "' expects " + setOne.size() + " properties but receives " + setTwo.size() + " properties";
        }

        // Compare property by property
        for (Map.Entry<String, Object> entry : setOne.entrySet())
        {
            String propName = entry.getKey();
            Object setTwoType = setTwo.get(entry.getKey());
            Object setOneType = entry.getValue();
            if (((setTwoType == null) && (setOneType != null)) ||
                 (setTwoType != null) && (setOneType == null))
            {
                return "Type by name '" + otherName + "' in property '" + propName + "' incompatible with null-type";
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
                    return "Type by name '" + otherName + "' in property '" + propName + "' expected " + boxedThis + " but receives " + boxedOther;
                }
            }
            else if ((setTwoType instanceof Map) && (setOneType instanceof Map))
            {
                String messageIsDeepEquals = isDeepEqualsProperties(propName, (Map<String, Object>)setOneType, (Map<String, Object>)setTwoType);
                if (messageIsDeepEquals != null)
                {
                    return messageIsDeepEquals;
                }
            }
            else if ((setTwoType instanceof EventType) && (setOneType instanceof EventType))
            {
                if (!setOneType.equals(setTwoType))
                {
                    EventType setOneEventType = (EventType) setOneType;
                    EventType setTwoEventType = (EventType) setTwoType;
                    return "Type by name '" + otherName + "' in property '" + propName + "' expected event type '" + setOneEventType.getName() + "' but receives event type '" + setTwoEventType.getName() + "'";
                }
            }
            else
            {
                String typeOne = getTypeName(setOneType);
                String typeTwo = getTypeName(setTwoType);
                return "Type by name '" + otherName + "' in property '" + propName + "' expected " + typeOne + " but receives " + typeTwo;
            }
        }

        return null;
    }

    private static String getTypeName(Object setOneType)
    {
        if (setOneType == null)
        {
            return "null";
        }
        if (setOneType instanceof Class)
        {
            return ((Class) setOneType).getName();
        }
        if (setOneType instanceof EventType)
        {
            return "event type '" + ((EventType)setOneType).getName() + "'";
        }
        return setOneType.getClass().getName();
    }

    private static void generateExceptionNestedProp(String name, Object value) throws EPException
    {
        String clazzName = (value == null) ? "null" : value.getClass().getSimpleName();
        throw new EPException("Nestable map type configuration encountered an unexpected property type of '"
            + clazzName + "' for property '" + name + "', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type");
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

    private static PropertySetDescriptor getNestableMapProperties(Map<String, Object> propertiesToAdd, EventAdapterService eventAdapterService)
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

            if (entry.getValue() instanceof EventType)
            {
                // Add EventType itself as a property
                EventType eventType = (EventType) entry.getValue();
                simplePropertyTypes.put(name, eventType.getUnderlyingType());
                propertyNameList.add(name);
                EventPropertyGetter getter = new MapEventBeanPropertyGetter(name);
                propertyGetters.put(name, getter);
                continue;
            }

            if (entry.getValue() instanceof String)
            {
                String propertyName = entry.getValue().toString();
                boolean isArray = isPropertyArray(propertyName);
                if (isArray) {
                    propertyName = getPropertyRemoveArray(propertyName);
                }

                // Add EventType itself as a property
                EventType eventType = eventAdapterService.getExistsTypeByAlias(propertyName);
                if (!(eventType instanceof MapEventType))
                {
                    throw new EPException("Nestable map type configuration encountered an unexpected property type name '"
                        + entry.getValue() + "' for property '" + name + "', expected java.lang.Class or java.util.Map or the name of a previously-declared Map type");

                }
                if (isArray)
                {
                    simplePropertyTypes.put(name, Map[].class);
                }
                else
                {
                    simplePropertyTypes.put(name, Map.class);
                }
                propertyNameList.add(name);
                EventPropertyGetter getter = new MapEventPropertyGetter(name);
                propertyGetters.put(name, getter);
                continue;
            }

            generateExceptionNestedProp(name, entry.getValue());
        }

        return new PropertySetDescriptor(propertyNameList, simplePropertyTypes, propertyGetters);
    }

    public EventTypeMetadata getMetadata()
    {
        return metadata;
    }

    /**
     * Returns true if the name indicates that the type is an array type.
     * @param name the property name
     * @return true if array type
     */
    public static boolean isPropertyArray(String name)
    {
        return name.trim().endsWith("[]");
    }

    /**
     * Returns the property name without the array type extension, if present.
     * @param name property name
     * @return property name with removed array extension name
     */
    public static String getPropertyRemoveArray(String name)
    {
        return name.replaceAll("\\[", "").replaceAll("\\]", "");
    }

    /**
     * Returns a message if the type, compared to this type, is not compatible in regards to the property numbers
     * and types.
     * @param otherType to compare to
     * @return message
     */
    public String getEqualsMessage(EventType otherType)
    {
        if (!(otherType instanceof MapEventType))
        {
            return "Type by name '" + otherType.getName() + "' is not a compatible type";
        }

        MapEventType other = (MapEventType) otherType;

        if (!other.typeName.equals(this.typeName))
        {
            return "Type by name '" + otherType.getName() + "' is not the same name";
        }

        return isDeepEqualsProperties(otherType.getName(), other.nestableTypes, this.nestableTypes);
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
