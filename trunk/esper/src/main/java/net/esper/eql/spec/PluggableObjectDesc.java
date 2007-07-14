package net.esper.eql.spec;

import net.esper.collection.Pair;
import net.esper.client.ConfigurationPlugInView;
import net.esper.client.ConfigurationException;
import net.esper.client.ConfigurationPlugInPatternObject;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class PluggableObjectDesc
{
    // Map of namespace, name and class plus type
    private Map<String, Map<String, Pair<Class, PluggableObjectType>>> pluggables;

    public PluggableObjectDesc()
    {
        pluggables = new HashMap<String, Map<String, Pair<Class, PluggableObjectType>>>();
    }

    public void addViews(List<ConfigurationPlugInView> configurationPlugInViews) throws ConfigurationException
    {
        initViews(configurationPlugInViews);
    }

    public void addPatternObjects(List<ConfigurationPlugInPatternObject> configPattern) throws ConfigurationException
    {
        initPatterns(configPattern);
    }

    public void addObjects(PluggableObjectDesc other)
    {
        for (Map.Entry<String, Map<String, Pair<Class, PluggableObjectType>>> entry : other.getPluggables().entrySet())
        {
            Map<String, Pair<Class, PluggableObjectType>> namespaceMap = pluggables.get(entry.getKey());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Pair<Class, PluggableObjectType>>();
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

    public void addObject(String namespace, String name, Class clazz, PluggableObjectType type)
    {
        Map<String, Pair<Class, PluggableObjectType>> namespaceMap = pluggables.get(namespace);
        if (namespaceMap == null)
        {
            namespaceMap = new HashMap<String, Pair<Class, PluggableObjectType>>();
            pluggables.put(namespace, namespaceMap);
        }
        namespaceMap.put(name, new Pair<Class, PluggableObjectType>(clazz, type));
    }

    public Map<String, Map<String, Pair<Class, PluggableObjectType>>> getPluggables()
    {
        return pluggables;
    }

    private void initViews(List<ConfigurationPlugInView> configurationPlugInViews)
    {
        if (configurationPlugInViews == null)
        {
            return;
        }

        for (ConfigurationPlugInView entry : configurationPlugInViews)
        {
            if (entry.getFactoryClassName() == null)
            {
                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getNamespace() == null)
            {
                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getName() == null)
            {
                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.getNamespace() + "'");
            }

            Class clazz;
            try
            {
                clazz = Class.forName(entry.getFactoryClassName());
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("View factory class " + entry.getFactoryClassName() + " could not be loaded");
            }

            Map<String, Pair<Class, PluggableObjectType>> namespaceMap = pluggables.get(entry.getNamespace());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Pair<Class, PluggableObjectType>>();
                pluggables.put(entry.getNamespace(), namespaceMap);
            }
            namespaceMap.put(entry.getName(), new Pair<Class, PluggableObjectType>(clazz, PluggableObjectType.VIEW));
        }
    }

    private void initPatterns(List<ConfigurationPlugInPatternObject> configEntries) throws ConfigurationException
    {
        if (configEntries == null)
        {
            return;
        }

        for (ConfigurationPlugInPatternObject entry : configEntries)
        {
            if (entry.getFactoryClassName() == null)
            {
                throw new ConfigurationException("Factory class name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getNamespace() == null)
            {
                throw new ConfigurationException("Namespace name has not been supplied for object '" + entry.getName() + "'");
            }
            if (entry.getName() == null)
            {
                throw new ConfigurationException("Name has not been supplied for object in namespace '" + entry.getNamespace() + "'");
            }
            if (entry.getPatternObjectType() == null)
            {
                throw new ConfigurationException("Pattern object type has not been supplied for object '" + entry.getName() + "'");
            }

            Class clazz;
            try
            {
                clazz = Class.forName(entry.getFactoryClassName());
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("Pattern object factory class " + entry.getFactoryClassName() + " could not be loaded");
            }

            Map<String, Pair<Class, PluggableObjectType>> namespaceMap = pluggables.get(entry.getNamespace());
            if (namespaceMap == null)
            {
                namespaceMap = new HashMap<String, Pair<Class, PluggableObjectType>>();
                pluggables.put(entry.getNamespace(), namespaceMap);
            }

            PluggableObjectType typeEnum;
            if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.GUARD)
            {
                typeEnum =  PluggableObjectType.PATTERN_GUARD;
            }
            else if (entry.getPatternObjectType() == ConfigurationPlugInPatternObject.PatternObjectType.OBSERVER)
            {
                typeEnum =  PluggableObjectType.PATTERN_OBSERVER;
            }
            else
            {
                throw new IllegalArgumentException("Pattern object type '" + entry.getPatternObjectType() + "' not known");
            }
            namespaceMap.put(entry.getName(), new Pair<Class, PluggableObjectType>(clazz, typeEnum));
        }
    }

}
