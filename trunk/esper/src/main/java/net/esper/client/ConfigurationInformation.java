/**************************************************************************************
 * Copyright (C) 2007 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

import java.util.*;
import java.io.*;
import java.net.URL;

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
     * Returns a list of configured adapter loaders.
     * @return adapter loaders
     */
    public List<ConfigurationAdapterLoader> getAdapterLoaders();

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
}
