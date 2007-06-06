/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.core;

import net.esper.client.*;

import java.util.*;

/**
 * Snapshot of Configuration is held for re-initializing engine state
 * from prior configuration values that may have been muted.
 */
public class ConfigurationSnapshot
{
    private Map<String, String> javaClassAliases = new HashMap<String, String>();
    private Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new HashMap<String, ConfigurationEventTypeXMLDOM>();
    private Map<String, ConfigurationEventTypeLegacy> legacyAliases = new HashMap<String, ConfigurationEventTypeLegacy>();
    private String[] autoImports;
    private Map<String, Properties> mapAliases = new HashMap<String, Properties>();
    private Map<String, ConfigurationDBRef> databaseRefs = new HashMap<String, ConfigurationDBRef>();
    private String epServicesContextFactoryClassName;
    private List<ConfigurationPlugInView> plugInViews = new LinkedList<ConfigurationPlugInView>();
    private List<ConfigurationAdapterLoader> adapterLoaders = new LinkedList<ConfigurationAdapterLoader>();
    private List<ConfigurationPlugInAggregationFunction> plugInAggregation = new LinkedList<ConfigurationPlugInAggregationFunction>();
    private List<ConfigurationPlugInPatternObject> plugInPatternObjects = new LinkedList<ConfigurationPlugInPatternObject>();
    private ConfigurationEngineDefaults engineDefaults;

    /**
     * Ctor.
     * <p>
     * Copies information out of configuration performing a deep copy
     * to preserve configs.
     * @param configuration is the client configuration holder
     */
    public ConfigurationSnapshot(Configuration configuration)
    {
        javaClassAliases.putAll(configuration.getEventTypeAliases());
        xmlDOMAliases.putAll(configuration.getEventTypesXMLDOM());
        autoImports = configuration.getImports().toArray(new String[0]);
        mapAliases.putAll(configuration.getEventTypesMapEvents());
        legacyAliases.putAll(configuration.getEventTypesLegacy());
        databaseRefs.putAll(configuration.getDatabaseReferences());
        epServicesContextFactoryClassName = configuration.getEPServicesContextFactoryClassName();
        plugInViews.addAll(configuration.getPlugInViews());
        adapterLoaders.addAll(configuration.getAdapterLoaders());
        plugInAggregation.addAll(configuration.getPlugInAggregationFunctions());
        plugInPatternObjects.addAll(configuration.getPlugInPatternObjects());
        engineDefaults = configuration.getEngineDefaults();
    }

    /**
     * Returns event type alias to Java class name mapping.
     * @return map of alias to class name
     */
    public Map<String, String> getJavaClassAliases()
    {
        return javaClassAliases;
    }

    /**
     * Returns map of event alias and XML DOM configs.
     * @return event alias to XML DOM config mapping
     */
    public Map<String, ConfigurationEventTypeXMLDOM> getXmlDOMAliases()
    {
        return xmlDOMAliases;
    }

    /**
     * Returns list of automatic import packages and classes.
     * @return automatic imports, or zero-length array if none
     */
    public String[] getAutoImports()
    {
        return autoImports;
    }

    /**
     * Returns a map of event type alias to Map-event type properties.
     * @return alias to event properties mapping for Map event types
     */
    public Map<String, Properties> getMapAliases()
    {
        return mapAliases;
    }

    /**
     * Returns the map of event type alias to legacy event type configuration.
     * @return map with alias as the key and legacy type config as the value
     */
    public Map<String, ConfigurationEventTypeLegacy> getLegacyAliases()
    {
        return legacyAliases;
    }

    /**
     * Returns a map of database name to database configuration.
     * @return database configs
     */
    public Map<String, ConfigurationDBRef> getDatabaseRefs()
    {
        return databaseRefs;
    }

    /**
     * Returns the services context factory class name
     * @return class name
     */
    public String getEPServicesContextFactoryClassName()
    {
        return epServicesContextFactoryClassName;
    }

    /**
     * Returns a list of configured plug-in views.
     * @return configs for views
     */
    public List<ConfigurationPlugInView> getPlugInViews()
    {
        return plugInViews;
    }

    /**
     * Returns a list of adapter loaders configured for the engine instance.
     * @return list of loader
     */
    public List<ConfigurationAdapterLoader> getAdapterLoaders()
    {
        return adapterLoaders;
    }

    /**
     * Returns a list of configured aggregation functions.
     * @return aggregation function configs 
     */
    public List<ConfigurationPlugInAggregationFunction> getPlugInAggregation()
    {
        return plugInAggregation;
    }

    /**
     * Returns the list of configured pattern objects plugged-in.
     * @return list of pattern objects
     */
    public List<ConfigurationPlugInPatternObject> getPlugInPatternObjects()
    {
        return plugInPatternObjects;
    }

    public ConfigurationEngineDefaults getEngineDefaults()
    {
        return engineDefaults;
    }
}


