/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.ConfigurationEventTypeLegacy;
import com.espertech.esper.client.EPException;
import com.espertech.esper.event.property.*;
import net.sf.cglib.reflect.FastClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Implementation of the EventType interface for handling JavaBean-type classes.
 */
public class BeanEventType implements EventTypeSPI
{
    private final EventTypeMetadata metadata;
    private final Class clazz;
    private final BeanEventTypeFactory beanEventTypeFactory;
    private final ConfigurationEventTypeLegacy optionalLegacyDef;
    private final String alias;

    private String[] propertyNames;
    private Map<String, SimplePropertyInfo> simpleProperties;
    private Map<String, EventPropertyDescriptor> mappedPropertyDescriptors;
    private Map<String, EventPropertyDescriptor> indexedPropertyDescriptors;
    private EventType[] superTypes;
    private FastClass fastClass;
    private Set<EventType> deepSuperTypes;
    private Configuration.PropertyResolutionStyle propertyResolutionStyle;

    private Map<String, List<SimplePropertyInfo>> simpleSmartPropertyTable;
    private Map<String, List<SimplePropertyInfo>> indexedSmartPropertyTable;
    private Map<String, List<SimplePropertyInfo>> mappedSmartPropertyTable;

    private final Map<String, EventPropertyGetter> propertyGetterCache;

    /**
     * Constructor takes a java bean class as an argument.
     * @param clazz is the class of a java bean or other POJO
     * @param beanEventTypeFactory is the chache and factory for event bean types and event wrappers
     * @param optionalLegacyDef optional configuration supplying legacy event type information
     * @param alias is the event type alias for the class
     * @param metadata event type metadata
     */
    public BeanEventType(EventTypeMetadata metadata,
                         Class clazz,
                         BeanEventTypeFactory beanEventTypeFactory,
                         ConfigurationEventTypeLegacy optionalLegacyDef,
                         String alias)
    {
        this.metadata = metadata;
        this.clazz = clazz;
        this.beanEventTypeFactory = beanEventTypeFactory;
        this.optionalLegacyDef = optionalLegacyDef;
        this.alias = alias;
        if (optionalLegacyDef != null)
        {
            this.propertyResolutionStyle = optionalLegacyDef.getPropertyResolutionStyle();
        }
        else
        {
            this.propertyResolutionStyle = beanEventTypeFactory.getDefaultPropertyResolutionStyle();
        }
        propertyGetterCache = new HashMap<String, EventPropertyGetter>();

        initialize(false);
    }

    public String getName()
    {
        return metadata.getPublicName();
    }

    public final Class getPropertyType(String propertyName)
    {
        SimplePropertyInfo simpleProp = getSimplePropertyInfo(propertyName);
        if ((simpleProp != null) && (simpleProp.getClazz() != null ))
        {
            return simpleProp.getClazz();
        }

        Property prop = PropertyParser.parse(propertyName, beanEventTypeFactory, false);
        if (prop instanceof SimpleProperty)
        {
            // there is no such property since it wasn't in simplePropertyTypes
            return null;
        }
        return prop.getPropertyType(this);
    }

    public boolean isProperty(String propertyName)
    {
        if (getPropertyType(propertyName) == null)
        {
            return false;
        }
        return true;
    }

    public final Class getUnderlyingType()
    {
        return clazz;
    }

    /**
     * Returns the property resolution style.
     * @return property resolution style
     */
    public Configuration.PropertyResolutionStyle getPropertyResolutionStyle()
    {
        return propertyResolutionStyle;
    }

    public EventPropertyGetter getGetter(String propertyName)
    {
        EventPropertyGetter cachedGetter = propertyGetterCache.get(propertyName);
        if (cachedGetter != null)
        {
            return cachedGetter; 
        }

        SimplePropertyInfo simpleProp = getSimplePropertyInfo(propertyName);
        if ((simpleProp != null) && ( simpleProp.getter != null ))
        {
            EventPropertyGetter getter = simpleProp.getGetter();
            propertyGetterCache.put(propertyName, getter);
            return getter;
        }

        Property prop = PropertyParser.parse(propertyName, beanEventTypeFactory, false);
        if (prop instanceof SimpleProperty)
        {
            // there is no such property since it wasn't in simplePropertyGetters
            return null;
        }

        EventPropertyGetter getter = prop.getGetter(this);
        propertyGetterCache.put(propertyName, getter);
        return getter;
    }

    /**
     * Looks up and returns a cached simple property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getSimpleProperty(String propertyName)
    {
        SimplePropertyInfo simpleProp = getSimplePropertyInfo(propertyName);
        if (simpleProp != null)
        {
            return simpleProp.getDescriptor();
        }
        return null;
    }

    /**
     * Looks up and returns a cached mapped property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getMappedProperty(String propertyName)
    {
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_SENSITIVE))
        {
            return mappedPropertyDescriptors.get(propertyName);
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE))
        {
            List<SimplePropertyInfo> propertyInfos = mappedSmartPropertyTable.get(propertyName.toLowerCase());
            return propertyInfos != null
                    ? propertyInfos.get(0).getDescriptor()
                    : null;
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE))
        {
            List<SimplePropertyInfo> propertyInfos = mappedSmartPropertyTable.get(propertyName.toLowerCase());
            if (propertyInfos != null)
            {
                if (propertyInfos.size() != 1 )
                {
                    throw new EPException( "Unable to determine which property to use for \"" + propertyName + "\" because more than one property matched");
                }

                return propertyInfos.get(0).getDescriptor();
            }
        }
        return null;
    }

    /**
     * Looks up and returns a cached indexed property's descriptor.
     * @param propertyName to look up
     * @return property descriptor
     */
    public final EventPropertyDescriptor getIndexedProperty(String propertyName)
    {
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_SENSITIVE))
        {
            return indexedPropertyDescriptors.get(propertyName);
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE))
        {
            List<SimplePropertyInfo> propertyInfos = indexedSmartPropertyTable.get(propertyName.toLowerCase());
            return propertyInfos != null
                    ? propertyInfos.get(0).getDescriptor()
                    : null;
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE))
        {
            List<SimplePropertyInfo> propertyInfos = indexedSmartPropertyTable.get(propertyName.toLowerCase());
            if (propertyInfos != null)
            {
                if (propertyInfos.size() != 1 )
                {
                    throw new EPException( "Unable to determine which property to use for \"" + propertyName + "\" because more than one property matched");
                }

                return propertyInfos.get(0).getDescriptor();
            }
        }
        return null;
    }

    public String[] getPropertyNames()
    {
        return propertyNames;
    }

    public EventType[] getSuperTypes()
    {
        return superTypes;
    }

    public Iterator<EventType> getDeepSuperTypes()
    {
        return deepSuperTypes.iterator();
    }

    /**
     * Returns the event type alias.
     * <p>
     * For classes for which no alias has been defined, the alias is the fully-qualified class name.
     * @return event type alias
     */
    public String getAlias()
    {
        return alias;
    }

    /**
     * Returns the fast class reference, if code generation is used for this type, else null.
     * @return fast class, or null if no code generation
     */
    public FastClass getFastClass()
    {
        return fastClass;
    }

    public String toString()
    {
        return "BeanEventType" +
               " clazz=" + clazz.getName();
    }

    private void initialize(boolean isConfigured)
    {
        PropertyListBuilder propertyListBuilder = PropertyListBuilderFactory.createBuilder(optionalLegacyDef);
        List<EventPropertyDescriptor> properties = propertyListBuilder.assessProperties(clazz);

        this.propertyNames = new String[properties.size()];
        this.simpleProperties = new HashMap<String, SimplePropertyInfo>();
        this.mappedPropertyDescriptors = new HashMap<String, EventPropertyDescriptor>();
        this.indexedPropertyDescriptors = new HashMap<String, EventPropertyDescriptor>();

        if (usesSmartResolutionStyle())
        {
            simpleSmartPropertyTable = new HashMap<String, List<SimplePropertyInfo>>();
            mappedSmartPropertyTable = new HashMap<String, List<SimplePropertyInfo>>();
            indexedSmartPropertyTable = new HashMap<String, List<SimplePropertyInfo>>();
        }

        if ((optionalLegacyDef == null) ||
            (optionalLegacyDef.getCodeGeneration() != ConfigurationEventTypeLegacy.CodeGeneration.DISABLED))
        {
            // get CGLib fast class
            fastClass = null;
            try
            {
                fastClass = FastClass.create(Thread.currentThread().getContextClassLoader(), clazz);
            }
            catch (Throwable ex)
            {
                log.warn(".initialize Unable to obtain CGLib fast class and/or method implementation for class " +
                        clazz.getName() + ", error msg is " + ex.getMessage());
                fastClass = null;
            }
        }

        int count = 0;
        for (EventPropertyDescriptor desc : properties)
        {
            String propertyName = desc.getPropertyName();
            propertyNames[count++] = desc.getListedName();

            if (desc.getPropertyType().equals(EventPropertyType.SIMPLE))
            {
                EventPropertyGetter getter;
                Class type;
                if (desc.getReadMethod() != null)
                {
                    getter = PropertyHelper.getGetter(desc.getReadMethod(), fastClass);
                    type = desc.getReadMethod().getReturnType();
                }
                else
                {
                    if (desc.getAccessorField() == null)
                    {
                        // Ignore property
                        continue;
                    }
                    getter = new ReflectionPropFieldGetter(desc.getAccessorField());
                    type = desc.getAccessorField().getType();
                }

                simpleProperties.put(propertyName, new SimplePropertyInfo(type, getter, desc));

                // Recognize that there may be properties with overlapping case-insentitive names
                if (usesSmartResolutionStyle())
                {
                    // Find the property in the smart property table
                    String smartPropertyName = propertyName.toLowerCase();
                    List<SimplePropertyInfo> propertyInfoList = simpleSmartPropertyTable.get(smartPropertyName);
                    if (propertyInfoList == null)
                    {
                        propertyInfoList = new ArrayList<SimplePropertyInfo>();
                        simpleSmartPropertyTable.put(smartPropertyName, propertyInfoList);
                    }

                    // Enter the property into the smart property list
                    SimplePropertyInfo propertyInfo = new SimplePropertyInfo(type, getter, desc);
                    propertyInfoList.add(propertyInfo);
                }
            }
            else if (desc.getPropertyType().equals(EventPropertyType.MAPPED))
            {
                mappedPropertyDescriptors.put(propertyName, desc);

                // Recognize that there may be properties with overlapping case-insentitive names
                if (usesSmartResolutionStyle())
                {
                    // Find the property in the smart property table
                    String smartPropertyName = propertyName.toLowerCase();
                    List<SimplePropertyInfo> propertyInfoList = mappedSmartPropertyTable.get(smartPropertyName);
                    if (propertyInfoList == null)
                    {
                        propertyInfoList = new ArrayList<SimplePropertyInfo>();
                        mappedSmartPropertyTable.put(smartPropertyName, propertyInfoList);
                    }

                    // Enter the property into the smart property list
                    SimplePropertyInfo propertyInfo = new SimplePropertyInfo(desc.getReturnType(), null, desc);
                    propertyInfoList.add(propertyInfo);
                }
            }
            else if (desc.getPropertyType().equals(EventPropertyType.INDEXED))
            {
                indexedPropertyDescriptors.put(propertyName, desc);

                if (usesSmartResolutionStyle())
                {
                    // Find the property in the smart property table
                    String smartPropertyName = propertyName.toLowerCase();
                    List<SimplePropertyInfo> propertyInfoList = indexedSmartPropertyTable.get(smartPropertyName);
                    if (propertyInfoList == null)
                    {
                        propertyInfoList = new ArrayList<SimplePropertyInfo>();
                        indexedSmartPropertyTable.put(smartPropertyName, propertyInfoList);
                    }

                    // Enter the property into the smart property list
                    SimplePropertyInfo propertyInfo = new SimplePropertyInfo(desc.getReturnType(), null, desc);
                    propertyInfoList.add(propertyInfo);
                }
            }
        }

        // Determine event type super types
        superTypes = getSuperTypes(clazz, beanEventTypeFactory);

        // Determine deep supertypes
        // Get Java super types (superclasses and interfaces), deep get of all in the tree
        Set<Class> supers = new HashSet<Class>();
        getSuper(clazz, supers);
        removeJavaLibInterfaces(supers);    // Remove "java." super types

        // Cache the supertypes of this event type for later use
        deepSuperTypes = new HashSet<EventType>();
        for (Class superClass : supers)
        {
            EventType superType = beanEventTypeFactory.createBeanType(superClass.getName(), superClass, isConfigured);
            deepSuperTypes.add(superType);
        }
    }

    private static EventType[] getSuperTypes(Class clazz, BeanEventTypeFactory beanEventTypeFactory)
    {
        List<Class> superclasses = new LinkedList<Class>();

        // add superclass
        Class superClass = clazz.getSuperclass();
        if (superClass != null)
        {
            superclasses.add(superClass);
        }

        // add interfaces
        Class interfaces[] = clazz.getInterfaces();
        for (int i = 0; i < interfaces.length; i++)
        {
            superclasses.add(interfaces[i]);
        }

        // Build event types, ignoring java language types
        List<EventType> superTypes = new LinkedList<EventType>();
        for (Class superclass : superclasses)
        {
            if (!superclass.getName().startsWith("java"))
            {
                EventType superType = beanEventTypeFactory.createBeanType(superclass.getName(), superclass, false);
                superTypes.add(superType);
            }
        }

        return superTypes.toArray(new EventType[superTypes.size()]);
    }

    /**
     * Add the given class's implemented interfaces and superclasses to the result set of classes.
     * @param clazz to introspect
     * @param result to add classes to
     */
    protected static void getSuper(Class clazz, Set<Class> result)
    {
        getSuperInterfaces(clazz, result);
        getSuperClasses(clazz, result);
    }

    private static void getSuperInterfaces(Class clazz, Set<Class> result)
    {
        Class interfaces[] = clazz.getInterfaces();

        for (int i = 0; i < interfaces.length; i++)
        {
            result.add(interfaces[i]);
            getSuperInterfaces(interfaces[i], result);
        }
    }

    private static void getSuperClasses(Class clazz, Set<Class> result)
    {
        Class superClass = clazz.getSuperclass();
        if (superClass == null)
        {
            return;
        }

        result.add(superClass);
        getSuper(superClass, result);
    }

    private static void removeJavaLibInterfaces(Set<Class> classes)
    {
        for (Class clazz : classes.toArray(new Class[0]))
        {
            if (clazz.getName().startsWith("java"))
            {
                classes.remove(clazz);
            }
        }
    }

    private boolean usesSmartResolutionStyle()
    {
        if ((propertyResolutionStyle.equals(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE)) ||
            (propertyResolutionStyle.equals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE)))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private SimplePropertyInfo getSimplePropertyInfo(String propertyName)
    {
        SimplePropertyInfo propertyInfo;
        List<SimplePropertyInfo> simplePropertyInfoList;

        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_SENSITIVE))
        {
            return simpleProperties.get(propertyName);
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.CASE_INSENSITIVE))
        {
            propertyInfo = simpleProperties.get(propertyName);
            if (propertyInfo != null)
            {
                return propertyInfo;
            }

            simplePropertyInfoList = simpleSmartPropertyTable.get(propertyName.toLowerCase());
            return
                simplePropertyInfoList != null
                    ? simplePropertyInfoList.get(0)
                    : null;
        }
        if (this.getPropertyResolutionStyle().equals(Configuration.PropertyResolutionStyle.DISTINCT_CASE_INSENSITIVE))
        {
            propertyInfo = simpleProperties.get(propertyName);
            if (propertyInfo != null)
            {
                return propertyInfo;
            }

            simplePropertyInfoList = simpleSmartPropertyTable.get(propertyName.toLowerCase());
            if ( simplePropertyInfoList != null )
            {
                if (simplePropertyInfoList.size() != 1 )
                {
                    throw new EPException( "Unable to determine which property to use for \"" + propertyName + "\" because more than one property matched");
                }

                return simplePropertyInfoList.get(0);
            }
        }

        return null;
    }

    /**
     * Descriptor caching the getter, class and property info.
     */
    public static class SimplePropertyInfo
    {
        private Class clazz;
        private EventPropertyGetter getter;
        private EventPropertyDescriptor descriptor;

        /**
         * Ctor.
         * @param clazz is the class
         * @param getter is the getter
         * @param descriptor is the property info
         */
        public SimplePropertyInfo(Class clazz, EventPropertyGetter getter, EventPropertyDescriptor descriptor)
        {
            this.clazz = clazz;
            this.getter = getter;
            this.descriptor = descriptor;
        }

        /**
         * Returns the return type.
         * @return return type
         */
        public Class getClazz()
        {
            return clazz;
        }

        /**
         * Returns the getter.
         * @return getter
         */
        public EventPropertyGetter getGetter()
        {
            return getter;
        }

        /**
         * Returns the property info.
         * @return property info
         */
        public EventPropertyDescriptor getDescriptor()
        {
            return descriptor;
        }
    }

    public EventTypeMetadata getMetadata()
    {
        return metadata;
    }

    private static final Log log = LogFactory.getLog(BeanEventType.class);
}
