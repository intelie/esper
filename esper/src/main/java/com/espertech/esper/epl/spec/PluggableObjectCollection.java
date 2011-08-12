/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

import com.espertech.esper.client.ConfigurationException;
import com.espertech.esper.client.ConfigurationPlugInPatternObject;
import com.espertech.esper.client.ConfigurationPlugInView;
import com.espertech.esper.client.ConfigurationPlugInVirtualDataWindow;
import com.espertech.esper.collection.Pair;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Repository for pluggable objects of different types that follow a "namespace:name" notation.
 */
public class PluggableObjectCollection
{
    // Map of namespace, name and class plus type
    private Map<String, Map<String, Pair<Class, PluggableObjectEntry>>> pluggables;

    /**
     * Ctor.
     */
    public PluggableObjectCollection()
    {
        pluggables = new HashMap<String, Map<String, Pair<Class, PluggableObjectEntry>>>();
    }

    /**
     * Add a plug-in view.
     * @param configurationPlugInViews is a list of configured plug-in view objects.
     * @throws ConfigurationException if the configured views don't resolve
     */
    public void addViews(List<ConfigurationPlugInView> configurationPlugInViews, List<ConfigurationPlugInVirtualDataWindow> configurationPlugInVirtualDW) throws ConfigurationException
    {
        initViews(configurationPlugInViews);
        initVirtualDW(configurationPlugInVirtualDW);
    }

    /**
     * Add a plug-in pattern object.
     * @param configPattern is a list of configured plug-in pattern objects.
     * @throws ConfigurationException if the configured patterns don't resolve
     */
    public void addPatternObjects(List<ConfigurationPlugInPatternObject> configPattern) throws ConfigurationException
    {
        initPatterns(configPattern);
    }

    /**
     * Add the plug-in objects for another collection.
     * @param other is the collection to add
     */
    public void addObjects(PluggableObjectCollection other)
    {
        for (Map.Entry<String, Map<String, Pair<Class, PluggableObjectEntry>>> entry : other.getPluggables().entrySet())
        {
            Map<String, Pair<Class, PluggableObjectEntry>> namespaceMap = pluggables.get(entry.getKey());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Pair<Class, PluggableObjectEntry>>();
                pluggables.put(entry.getKey(), namespaceMap);
            }

            for (String name : entry.getValue().keySet())
            {
                if (namespaceMap.containsKey(name))
                {
                    throw new ConfigurationException("Duplicate object detected in namespace '" + entry.getKey() +
                                "' by name '" + name + "'");
                }
            }

            namespaceMap.putAll(entry.getValue());
        }
    }

    /**
     * Add a single object to the collection.
     * @param namespace is the object's namespace
     * @param name is the object's name
     * @param clazz is the class the object resolves to
     * @param type is the object type
     */
    public void addObject(String namespace, String name, Class clazz, PluggableObjectType type)
    {
        addObject(namespace, name, clazz, type, null);
    }

    /**
     * Add a single object to the collection also adding additional configuration.
     * @param namespace is the object's namespace
     * @param name is the object's name
     * @param clazz is the class the object resolves to
     * @param type is the object type
     */
    public void addObject(String namespace, String name, Class clazz, PluggableObjectType type, Serializable configuration)
    {
        Map<String, Pair<Class, PluggableObjectEntry>> namespaceMap = pluggables.get(namespace);
        if (namespaceMap == null)
        {
            namespaceMap = new HashMap<String, Pair<Class, PluggableObjectEntry>>();
            pluggables.put(namespace, namespaceMap);
        }
        namespaceMap.put(name, new Pair<Class, PluggableObjectEntry>(clazz, new PluggableObjectEntry(type, configuration)));
    }

    /**
     * Returns the underlying nested map of namespace keys and name-to-object maps.
     * @return pluggable object collected
     */
    public Map<String, Map<String, Pair<Class, PluggableObjectEntry>>> getPluggables()
    {
        return pluggables;
    }

    private void initViews(List<ConfigurationPlugInView> configurationPlugInViews)
    {
        if (configurationPlugInViews == null) {
            return;
        }

        for (ConfigurationPlugInView entry : configurationPlugInViews) {
            handleAddPluggableObject(entry.getFactoryClassName(), entry.getNamespace(), entry.getName(), PluggableObjectType.VIEW, null);
        }
    }

    private void initVirtualDW(List<ConfigurationPlugInVirtualDataWindow> configurationPlugInVirtualDataWindows)
    {
        if (configurationPlugInVirtualDataWindows == null) {
            return;
        }

        for (ConfigurationPlugInVirtualDataWindow entry : configurationPlugInVirtualDataWindows) {
            handleAddPluggableObject(entry.getFactoryClassName(), entry.getNamespace(), entry.getName(), PluggableObjectType.VIRTUALDW, entry.getConfig());
        }
    }

    private void handleAddPluggableObject(String factoryClassName, String namespace, String name, PluggableObjectType type, Serializable optionalCustomConfig) {

        if (factoryClassName == null)
        {
            throw new ConfigurationException("Factory class name has not been supplied for object '" + name + "'");
        }
        if (namespace == null)
        {
            throw new ConfigurationException("Namespace name has not been supplied for object '" + name + "'");
        }
        if (name == null)
        {
            throw new ConfigurationException("Name has not been supplied for object in namespace '" + namespace + "'");
        }

        Class clazz;
        try
        {
            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            clazz = Class.forName(factoryClassName, true, cl);
        }
        catch (ClassNotFoundException ex)
        {
            throw new ConfigurationException("View factory class " + factoryClassName + " could not be loaded");
        }

        Map<String, Pair<Class, PluggableObjectEntry>> namespaceMap = pluggables.get(namespace);
        if (namespaceMap == null)
        {
            namespaceMap = new HashMap<String, Pair<Class, PluggableObjectEntry>>();
            pluggables.put(namespace, namespaceMap);
        }
        namespaceMap.put(name, new Pair<Class, PluggableObjectEntry>(clazz, new PluggableObjectEntry(type, optionalCustomConfig)));
    }

    private void initPatterns(List<ConfigurationPlugInPatternObject> configEntries) throws ConfigurationException
    {
        if (configEntries == null)
        {
            return;
        }

        for (ConfigurationPlugInPatternObject entry : configEntries)
        {
            if (entry.getPatternObjectType() == null) {
                throw new ConfigurationException("Pattern object type has not been supplied for object '" + entry.getName() + "'");
            }

            PluggableObjectType typeEnum;
            if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.GUARD) {
                typeEnum =  PluggableObjectType.PATTERN_GUARD;
            }
            else if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER) {
                typeEnum =  PluggableObjectType.PATTERN_OBSERVER;
            }
            else {
                throw new IllegalArgumentException("Pattern object type '" + entry.getPatternObjectType() + "' not known");
            }

            handleAddPluggableObject(entry.getFactoryClassName(), entry.getNamespace(), entry.getName(), typeEnum, null);
        }
    }

}
