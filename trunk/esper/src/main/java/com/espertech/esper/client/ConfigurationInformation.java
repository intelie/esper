/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import java.util.*;

/**
 * Provides configurations for an engine instance.
 */
public interface ConfigurationInformation
{
    /**
     * Returns the service context factory class name
     * @return class name
     */
    public String getEPServicesContextFactoryClassName();

    /**
     * Returns the mapping of event type alias to Java class name.
     * @return event type aliases for Java class names
     */
    public Map<String, String> getEventTypeAliases();

    /**
     * Returns a map keyed by event type alias name, and values being the definition for the
     * event type of the property names and types that make up the event.
     * @return map of event type alias name and definition of event properties
     */
    public Map<String, Properties> getEventTypesMapEvents();

    /**
     * Returns a map keyed by event type alias name, and values being the definition for the
     * event type of the property names and types that make up the event,
     * for nestable, strongly-typed Map-based event representations. 
     * @return map of event type alias name and definition of event properties
     */
    public Map<String, Map<String, Object>> getEventTypesNestableMapEvents();

    /**
     * Returns the mapping of event type alias to XML DOM event type information.
     * @return event type aliases mapping to XML DOM configs
     */
    public Map<String, ConfigurationEventTypeXMLDOM> getEventTypesXMLDOM();

    /**
     * Returns the mapping of event type alias to legacy java event type information.
     * @return event type aliases mapping to legacy java class configs
     */
    public Map<String, ConfigurationEventTypeLegacy> getEventTypesLegacy();

    /**
     * Returns the class and package imports.
     * @return imported names
     */
	public List<String> getImports();

    /**
     * Returns a map of string database names to database configuration options.
     * @return map of database configurations
     */
    public Map<String, ConfigurationDBRef> getDatabaseReferences();

    /**
     * Returns a list of configured plug-in views.
     * @return list of plug-in view configs
     */
    public List<ConfigurationPlugInView> getPlugInViews();

    /**
     * Returns a list of configured plugin loaders.
     * @return adapter loaders
     */
    public List<ConfigurationPluginLoader> getPluginLoaders();

    /**
     * Returns a list of configured plug-in aggregation functions.
     * @return list of configured aggregations
     */
    public List<ConfigurationPlugInAggregationFunction> getPlugInAggregationFunctions();

    /**
     * Returns a list of configured plug-ins for pattern observers and guards.
     * @return list of pattern plug-ins
     */
    public List<ConfigurationPlugInPatternObject> getPlugInPatternObjects();

    /**
     * Returns engine default settings.
     * @return engine defaults
     */
    public ConfigurationEngineDefaults getEngineDefaults();

    /**
     * Returns the variables by name as key and type plus initialization value as value
     * @return map of variable name and variable configuration
     */
    public Map<String, ConfigurationVariable> getVariables();

    /**
     * Returns a map of class name and cache configurations, for use in
     * method invocations in the from-clause of methods provided by the class.
     * @return map of fully-qualified or simple class name and cache configuration
     */
    public Map<String, ConfigurationMethodRef> getMethodInvocationReferences();

    /**
     * Returns a set of Java package names that Java event classes reside in.
     * <p>
     * This setting allows an application to place all it's events into one or more Java packages
     * and then declare these packages via this method. The engine
     * attempts to resolve an event type alias to a Java class residing in each declared package.
     * <p>
     * For example, in the statement "select * from MyEvent" the engine attempts to load class "javaPackageName.MyEvent"
     * and if successful, uses that class as the event type.
     * @return set of Java package names to look for events types when encountering a new event type alias
     */
    public Set<String> getEventTypeAutoAliasPackages();    
}
