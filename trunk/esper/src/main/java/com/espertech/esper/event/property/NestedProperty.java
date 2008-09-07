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
    private BeanEventTypeFactory beanEventTypeFactory;

    /**
     * Ctor.
     * @param properties is the list of Property instances representing each nesting level
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     */
    public NestedProperty(List<Property> properties, BeanEventTypeFactory beanEventTypeFactory)
    {
        this.properties = properties;
        this.beanEventTypeFactory = beanEventTypeFactory;
    }

    /**
     * Returns the list of property instances making up the nesting levels.
     * @return list of Property instances
     */
    public List<Property> getProperties()
    {
        return properties;
    }

    public EventPropertyGetter getGetter(BeanEventType eventType)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();

        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            Property property = it.next();
            EventPropertyGetter getter = property.getGetter(eventType);
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
                eventType = beanEventTypeFactory.createBeanType(clazz.getName(), clazz);
            }
            getters.add(getter);
        }

        return new NestedPropertyGetter(getters, beanEventTypeFactory);
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

                eventType = beanEventTypeFactory.createBeanType(result.getName(), result);
            }
        }

        return result;
    }

    public Class getPropertyTypeMap(Map optionalMapPropTypes)
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
                BeanEventType beanType = beanEventTypeFactory.createBeanType(pojoClass.getName(), pojoClass);
                String remainingProps = toPropertyEPL(properties, count);
                return beanType.getPropertyType(remainingProps);
            }

            if (!(nestedType instanceof Map))
            {
                String message = "Nestable map type configuration encountered an unexpected value type of '"
                    + nestedType.getClass() + " for property '" + propertyName + "', expected Class, Map.class or Map<String, Object> as value type";
                throw new PropertyAccessException(message);
            }

            currentDictionary = (Map) nestedType;
        }
        throw new IllegalStateException("Unexpected end of nested property");
    }

    public EventPropertyGetter getGetterMap(Map optionalMapPropTypes)
    {
        List<EventPropertyGetter> getters = new LinkedList<EventPropertyGetter>();
        Map currentDictionary = optionalMapPropTypes;

        int count = 0;
        for (Iterator<Property> it = properties.iterator(); it.hasNext();)
        {
            count++;
            Property property = it.next();

            // manufacture a getter for getting the item out of the map
            EventPropertyGetter getter = property.getGetterMap(currentDictionary);
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
                    else
                    {
                        // treat the return type of the map property as a POJO
                        Class pojoClass = (Class) propertyReturnType;
                        BeanEventType beanType = beanEventTypeFactory.createBeanType(pojoClass.getName(), pojoClass);
                        String remainingProps = toPropertyEPL(properties, count);
                        getters.add(beanType.getGetter(remainingProps));
                        break; // the single Pojo getter handles the rest
                    }
                }
            }
        }

        return new MapNestedPropertyGetter(getters, beanEventTypeFactory);
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
