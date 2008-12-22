/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.property;

import com.espertech.esper.event.*;
import com.espertech.esper.event.xml.SchemaElementComplex;
import com.espertech.esper.event.xml.SchemaItem;
import com.espertech.esper.event.xml.SchemaItemAttribute;
import com.espertech.esper.event.xml.SchemaElementSimple;
import com.espertech.esper.event.xml.getter.DOMNestedPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.PropertyAccessException;

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.Map;
import java.io.StringWriter;

/**
 * This class represents a nested property, each nesting level made up of a property instance that
 * can be of type indexed, mapped or simple itself.
 * <p>
 * The syntax for nested properties is as follows.
 * <pre>
 * a.n
 * a[1].n
 * a('1').n
 * </pre>
 */
public class NestedProperty implements Property
{
    private List<Property> properties;
    private EventAdapterService eventAdapterService;

    /**
     * Ctor.
     * @param properties is the list of Property instances representing each nesting level
     * @param eventAdapterService is the cache and factory for event bean types and event wrappers
     */
    public NestedProperty(List<Property> properties, EventAdapterService eventAdapterService)
    {
        this.properties = properties;
        this.eventAdapterService = eventAdapterService;
    }

    /**
     * Returns the list of property instances making up the nesting levels.
     * @return list of Property instances
     */
    public List<Property> getProperties()
    {
        return properties;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType, EventAdapterService eventAdapterService)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();

        Property lastProperty = null;
        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            lastProperty = property;
            EventPropertyGetter getter = property.getGetter(eventType, eventAdapterService);
            if (getter == null)
            {
                return null;
            }

            if (it.hasNext())
            {
                Class clazz = property.getPropertyType(eventType);
                if (clazz == null)
                {
                    // if the property is not valid, return null
                    return null;
                }
                // Map cannot be used to further nest as the type cannot be determined
                if (clazz == Map.class)
                {
                    return null;
                }
                if (clazz.isArray())
                {
                    return null;
                }
                eventType = eventAdapterService.getBeanEventTypeFactory().createBeanType(clazz.getName(), clazz, false);
            }
            getters.add(getter);
        }

        Class finalPropertyType = lastProperty.getPropertyType(eventType);
        return new NestedPropertyGetter(getters, eventAdapterService,finalPropertyType);
    }

    public Class getPropertyType(BeanEventType eventType)
    {
        Class result = null;

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            result = property.getPropertyType(eventType);

            if (result == null)
            {
                // property not found, return null
                return null;
            }

            if (it.hasNext())
            {
                // Map cannot be used to further nest as the type cannot be determined
                if (result == Map.class)
                {
                    return null;
                }

                if (result.isArray())
                {
                    return null;
                }

                eventType = eventAdapterService.getBeanEventTypeFactory().createBeanType(result.getName(), result, false);
            }
        }

        return result;
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        Map currentDictionary = optionalMapPropTypes;

        int count = 0;
        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            count++;
            Property property = it.next();
            PropertyBase base = (PropertyBase) property;
            String propertyName = base.getPropertyNameAtomic();

            Object nestedType = null;
            if (currentDictionary != null)
            {
                nestedType = currentDictionary.get(propertyName);
            }

            if (nestedType == null)
            {
                if (property instanceof DynamicProperty)
                {
                    return Object.class;
                }
                else
                {
                    return null;
                }
            }

            if (!it.hasNext())
            {
                if (nestedType instanceof Class)
                {
                    return (Class) nestedType;
                }
                if (nestedType instanceof Map)
                {
                    return Map.class;
                }
            }

            if (nestedType == Map.class)
            {
                return Object.class;
            }

            if (nestedType instanceof Class)
            {
                Class pojoClass = (Class) nestedType;
                if (!pojoClass.isArray())
                {
                    BeanEventType beanType = eventAdapterService.getBeanEventTypeFactory().createBeanType(pojoClass.getName(), pojoClass, false);
                    String remainingProps = toPropertyEPL(properties, count);
                    return beanType.getPropertyType(remainingProps);
                }
                else if (property instanceof IndexedProperty)
                {
                    Class componentType = pojoClass.getComponentType();
                    BeanEventType beanType = eventAdapterService.getBeanEventTypeFactory().createBeanType(componentType.getName(), componentType, false);
                    String remainingProps = toPropertyEPL(properties, count);
                    return beanType.getPropertyType(remainingProps);
                }
            }

            if (nestedType instanceof String)       // property type is the name of a map event type
            {
                String nestedName = nestedType.toString();
                boolean isArray = MapEventType.isPropertyArray(nestedName);
                if (isArray) {
                    nestedName = MapEventType.getPropertyRemoveArray(nestedName);
                }

                EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
                if (!(innerType instanceof MapEventType))
                {
                    return null;
                }
                
                String remainingProps = toPropertyEPL(properties, count);
                return innerType.getPropertyType(remainingProps);
            }
            else
            {
                if (!(nestedType instanceof Map))
                {
                    String message = "Nestable map type configuration encountered an unexpected value type of '"
                        + nestedType.getClass() + " for property '" + propertyName + "', expected Class, Map.class or Map<String, Object> as value type";
                    throw new PropertyAccessException(message);
                }
            }

            currentDictionary = (Map) nestedType;
        }
        throw new IllegalStateException("Unexpected end of nested property");
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes, EventAdapterService eventAdapterService)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();
        Map currentDictionary = optionalMapPropTypes;

        int count = 0;
        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            count++;
            Property property = it.next();

            // manufacture a getter for getting the item out of the map
            EventPropertyGetter getter = property.getGetterMap(currentDictionary, eventAdapterService);
            if (getter == null)
            {
                return null;
            }
            getters.add(getter);

            PropertyBase base = (PropertyBase) property;
            String propertyName = base.getPropertyNameAtomic();

            // For the next property if there is one, check how to property type is defined
            if (!it.hasNext())
            {
                continue;
            }

            if (currentDictionary != null)
            {
                // check the type that this property will return
                Object propertyReturnType = currentDictionary.get(propertyName);

                if (propertyReturnType == null)
                {
                    currentDictionary = null;
                }
                if (propertyReturnType != null)
                {
                    if (propertyReturnType instanceof Map)
                    {
                        currentDictionary = (Map) propertyReturnType;
                    }
                    else if (propertyReturnType == Map.class)
                    {
                        currentDictionary = null;
                    }
                    else if (propertyReturnType instanceof String)
                    {
                        String nestedName = propertyReturnType.toString();
                        boolean isArray = MapEventType.isPropertyArray(nestedName);
                        if (isArray) {
                            nestedName = MapEventType.getPropertyRemoveArray(nestedName);
                        }

                        EventType innerType = eventAdapterService.getExistsTypeByAlias(nestedName);
                        if (!(innerType instanceof MapEventType))
                        {
                            return null;
                        }

                        String remainingProps = toPropertyEPL(properties, count);
                        getters.add(innerType.getGetter(remainingProps));
                        break; // the single Pojo getter handles the rest
                    }
                    else
                    {
                        // treat the return type of the map property as a POJO
                        Class pojoClass = (Class) propertyReturnType;
                        if (!pojoClass.isArray())
                        {
                            BeanEventType beanType = eventAdapterService.getBeanEventTypeFactory().createBeanType(pojoClass.getName(), pojoClass, false);
                            String remainingProps = toPropertyEPL(properties, count);
                            getters.add(beanType.getGetter(remainingProps));
                            break; // the single Pojo getter handles the rest
                        }
                        else
                        {
                            Class componentType = pojoClass.getComponentType();
                            BeanEventType beanType = eventAdapterService.getBeanEventTypeFactory().createBeanType(componentType.getName(), componentType, false);
                            String remainingProps = toPropertyEPL(properties, count);
                            getters.add(beanType.getGetter(remainingProps));
                            break; // the single Pojo getter handles the rest
                        }
                    }
                }
            }
        }

        return new MapNestedPropertyGetter(getters, eventAdapterService);
    }

    public void toPropertyEPL(StringWriter writer)
    {
        String delimiter = "";
        for (Property property : properties)
        {
            writer.append(delimiter);
            property.toPropertyEPL(writer);
            delimiter = ".";
        }
    }

    public EventPropertyGetter getGetterDOM(SchemaElementComplex parentComplexProperty, EventAdapterService eventAdapterService)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();

        SchemaElementComplex complexElement = parentComplexProperty;

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            EventPropertyGetter getter = property.getGetterDOM(complexElement, eventAdapterService);
            if (getter == null)
            {
                return null;
            }

            if (it.hasNext())
            {
                SchemaItem childSchemaItem = property.getPropertyTypeSchema(complexElement, eventAdapterService);
                if (childSchemaItem == null)
                {
                    // if the property is not valid, return null
                    return null;
                }

                if ((childSchemaItem instanceof SchemaItemAttribute) || (childSchemaItem instanceof SchemaElementSimple))
                {
                    return null;
                }

                complexElement = (SchemaElementComplex) childSchemaItem;
            }
            
            getters.add(getter);
        }

        return new DOMNestedPropertyGetter(getters);
    }

    public SchemaItem getPropertyTypeSchema(SchemaElementComplex parentComplexProperty, EventAdapterService eventAdapterService)
    {
        Property lastProperty = null;
        SchemaElementComplex complexElement = parentComplexProperty;

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            lastProperty = property;
            EventPropertyGetter getter = property.getGetterDOM(complexElement, eventAdapterService);
            if (getter == null)
            {
                return null;
            }

            if (it.hasNext())
            {
                SchemaItem childSchemaItem = property.getPropertyTypeSchema(complexElement, eventAdapterService);
                if (childSchemaItem == null)
                {
                    // if the property is not valid, return null
                    return null;
                }

                if ((childSchemaItem instanceof SchemaItemAttribute) || (childSchemaItem instanceof SchemaElementSimple))
                {
                    return null;
                }

                complexElement = (SchemaElementComplex) childSchemaItem;
            }
        }

        SchemaItem finalPropertyType = lastProperty.getPropertyTypeSchema(complexElement, eventAdapterService);
        return finalPropertyType;
    }

    private static String toPropertyEPL(List<Property> property, int startFromIndex)
    {
        String delimiter = "";
        StringWriter writer = new StringWriter();
        for (int i = startFromIndex; i < property.size(); i++)
        {
            writer.append(delimiter);
            property.get(i).toPropertyEPL(writer);
            delimiter = ".";
        }
        return writer.getBuffer().toString();
    }
}
