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
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.util.*;
import java.beans.*;
import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This class offers utililty methods around introspection and CGLIB interaction.
 */
public class PropertyHelper
{
    /**
     * Return getter for the given method and CGLIB FastClass.
     * @param method to return getter for
     * @param fastClass is the CGLIB fast classs to make FastMethod for
     * @return property getter
     */
    public static EventPropertyGetter getGetter(Method method, FastClass fastClass)
    {
        // Get CGLib fast method handle
        FastMethod fastMethod = null;
        try
        {
            if (fastClass != null)
            {
                fastMethod = fastClass.getMethod(method);
            }
        }
        catch (Throwable ex)
        {
            log.warn(".getAccessors Unable to obtain CGLib fast method implementation, msg=" + ex.getMessage());
        }

        // Construct the appropriate property getter CGLib or reflect
        EventPropertyGetter getter;
        if (fastMethod != null)
        {
            getter = new CGLibPropertyGetter(fastMethod);
        }
        else
        {
            getter = new ReflectionPropMethodGetter(method);
        }

        return getter;
    }

    /**
     * Introspects the given class and returns event property descriptors for each property found
     * in the class itself, it's superclasses and all interfaces this class and the superclasses implements.
     * @param clazz is the Class to introspect
     * @return list of properties
     */
    public static List<EventPropertyDescriptor> getProperties(Class clazz)
    {
        // Determine all interfaces implemented and the interface's parent interfaces if any
        Set<Class> propertyOrigClasses = new HashSet<Class>();
        getImplementedInterfaceParents(clazz, propertyOrigClasses);

        // Add class itself
        propertyOrigClasses.add(clazz);

        // Get the set of property names for all classes
        return getPropertiesForClasses(propertyOrigClasses);
    }

    private static void getImplementedInterfaceParents(Class clazz, Set<Class> classesResult)
    {
        Class[] interfaces = clazz.getInterfaces();

        if (interfaces == null)
        {
            return;
        }

        for (int i = 0; i < interfaces.length; i++)
        {
            classesResult.add(interfaces[i]);
            getImplementedInterfaceParents(interfaces[i], classesResult);
        }
    }

    private static List<EventPropertyDescriptor> getPropertiesForClasses(Set<Class> propertyClasses)
    {
    	List<EventPropertyDescriptor> result = new LinkedList<EventPropertyDescriptor>();

        for (Class clazz : propertyClasses)
        {
        	addIntrospectProperties(clazz, result);
            addMappedProperties(clazz, result);
        }

        removeDuplicateProperties(result);
        removeJavaProperties(result);

        return result;
    }

    /**
     * Remove Java language specific properties from the given list of property descriptors.
     * @param properties is the list of property descriptors
     */
    protected static void removeJavaProperties(List<EventPropertyDescriptor> properties)
    {
        List<EventPropertyDescriptor> toRemove = new LinkedList<EventPropertyDescriptor>();

        // add removed entries to separate list
        for (EventPropertyDescriptor desc : properties)
        {
            if ((desc.getPropertyName().equals("class")) ||
                (desc.getPropertyName().equals("getClass")) ||
                (desc.getPropertyName().equals("toString")) ||
                (desc.getPropertyName().equals("hashCode")))
            {
                toRemove.add(desc);
            }
        }

        // remove
        for (EventPropertyDescriptor desc : toRemove)
        {
            properties.remove(desc);
        }
    }

    /**
     * Removed duplicate properties using the property name to find unique properties.
     * @param properties is a list of property descriptors
     */
    protected static void removeDuplicateProperties(List<EventPropertyDescriptor> properties)
    {
        LinkedHashMap<String, EventPropertyDescriptor> set = new LinkedHashMap<String, EventPropertyDescriptor>();
        List<EventPropertyDescriptor> toRemove = new LinkedList<EventPropertyDescriptor>();

        // add duplicates to separate list
        for (EventPropertyDescriptor desc : properties)
        {
            if (set.containsKey(desc.getPropertyName()))
            {
                toRemove.add(desc);
                continue;
            }
            set.put(desc.getPropertyName(), desc);
        }

        // remove duplicates
        for (EventPropertyDescriptor desc : toRemove)
        {
            properties.remove(desc);
        }
    }

    /**
     * Adds to the given list of property descriptors the properties of the given class
     * using the Introspector to introspect properties. This also finds array and indexed properties.
     * @param clazz to introspect
     * @param result is the list to add to
     */
    protected static void addIntrospectProperties(Class clazz, List<EventPropertyDescriptor> result)
    {
        PropertyDescriptor properties[] = introspect(clazz);
        for (int i = 0; i < properties.length; i++)
        {
            PropertyDescriptor property = properties[i];
        	String propertyName = property.getName();
            String listedName = propertyName;
        	Method readMethod = property.getReadMethod();

        	EventPropertyType type = EventPropertyType.SIMPLE;
        	if (property instanceof IndexedPropertyDescriptor)
        	{
                readMethod = ((IndexedPropertyDescriptor) property).getIndexedReadMethod();
        		type = EventPropertyType.INDEXED;
                listedName = propertyName + "[]";     // indexed properties add [] to name
        	}

    		result.add(new EventPropertyDescriptor(propertyName, listedName, readMethod, type));
        }
    }

    /**
     * Adds to the given list of property descriptors the mapped properties, ie.
     * properties that have a getter method taking a single String value as a parameter.
     * @param clazz to introspect
     * @param result is the list to add to
     */
    protected static void addMappedProperties(Class clazz, List<EventPropertyDescriptor> result)
    {
        Set<String> uniquePropertyNames = new HashSet<String>();
    	Method[] methods = clazz.getMethods();

    	for (int i = 0; i < methods.length; i++)
    	{
    		String methodName = methods[i].getName();
    		if (!methodName.startsWith("get"))
    		{
    			continue;
    		}

    		String inferredName = methodName.substring(3, methodName.length());
    		if (inferredName.length() == 0)
    		{
    			continue;
    		}

    		Class<?> parameterTypes[] = methods[i].getParameterTypes();
    		if (parameterTypes.length != 1)
    		{
    			continue;
    		}

    		if (parameterTypes[0] != String.class)
    		{
    			continue;
    		}

    		String newInferredName = null;
            // Leave uppercase inferred names such as URL
    		if (inferredName.length() >= 2)
            {
                if ((Character.isUpperCase(inferredName.charAt(0))) &&
    			    (Character.isUpperCase(inferredName.charAt(1))))
                {
                    newInferredName = inferredName;
                }
            }
    		// camelCase the inferred name
            if (newInferredName == null)
    		{
    			newInferredName = Character.toString(Character.toLowerCase(inferredName.charAt(0)));
    			if (inferredName.length() > 1)
    			{
    				newInferredName += inferredName.substring(1, inferredName.length());
    			}
    		}
    		inferredName = newInferredName;

            // if the property inferred name already exists, don't supply it
            if (uniquePropertyNames.contains(inferredName))
            {
                continue;
            }

            String listedName = inferredName + "()";    // mapped proerties add () to name

    		result.add(new EventPropertyDescriptor(inferredName, listedName, methods[i], EventPropertyType.MAPPED));
            uniquePropertyNames.add(inferredName);
    	}
    }

    /**
     * Using the Java Introspector class the method returns the property descriptors obtained through introspection.
     * @param clazz to introspect
     * @return array of property descriptors
     */
    protected static PropertyDescriptor[] introspect(Class clazz)
    {
        BeanInfo beanInfo = null;

        try
        {
            beanInfo = Introspector.getBeanInfo(clazz);
        }
        catch (IntrospectionException e)
        {
            return (new PropertyDescriptor[0]);
        }

        return beanInfo.getPropertyDescriptors();
    }

    private static final Log log = LogFactory.getLog(PropertyHelper.class);
}
