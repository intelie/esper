///////////////////////////////////////////////////////////////////////////////////////
// Copyright (C) 2007 Esper Team. All rights reserved.                                /
// http://esper.codehaus.org                                                          /
// ---------------------------------------------------------------------------------- /
// The software in this package is published under the terms of the GPL license       /
// a copy of which has been included with this distribution in the license.txt file.  /
///////////////////////////////////////////////////////////////////////////////////////

using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.compat;

namespace net.esper.core
{
	/// <summary>
	/// Snapshot of Configuration is held for re-initializing engine state
	/// from prior configuration values that may have been muted.
	/// </summary>
	public class ConfigurationSnapshot
	{
	    private readonly EDictionary<String, String> typeAliases = new HashDictionary<String, String>();
        private readonly EDictionary<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new HashDictionary<String, ConfigurationEventTypeXMLDOM>();
        private readonly EDictionary<String, ConfigurationEventTypeLegacy> legacyAliases = new HashDictionary<String, ConfigurationEventTypeLegacy>();
        private readonly String[] autoImports;
        private readonly EDictionary<String, Properties> mapAliases = new HashDictionary<String, Properties>();
        private readonly EDictionary<String, ConfigurationDBRef> databaseRefs = new HashDictionary<String, ConfigurationDBRef>();
        private readonly String epServicesContextFactoryClassName;
        private readonly List<ConfigurationPlugInView> plugInViews = new List<ConfigurationPlugInView>();
        private readonly List<ConfigurationAdapterLoader> adapterLoaders = new List<ConfigurationAdapterLoader>();
        private readonly List<ConfigurationPlugInAggregationFunction> plugInAggregation = new List<ConfigurationPlugInAggregationFunction>();
        private readonly List<ConfigurationPlugInPatternObject> plugInPatternObjects = new List<ConfigurationPlugInPatternObject>();
	    private readonly PropertyResolutionStyle defaultPropertyResolutionStyle;

	    /// <summary>
	    /// Ctor.
	    /// <para>
	    /// Copies information out of configuration performing a deep copy
	    /// to preserve configs.
	    /// </para>
	    /// </summary>
	    /// <param name="configuration">is the client configuration holder</param>
	    public ConfigurationSnapshot(Configuration configuration)
	    {
	        typeAliases.PutAll(configuration.EventTypeAliases);
	        xmlDOMAliases.PutAll(configuration.EventTypesXMLDOM);
	        autoImports = CollectionHelper.ToArray(configuration.Imports);
	        mapAliases.PutAll(configuration.EventTypesMapEvents);
	        legacyAliases.PutAll(configuration.EventTypesLegacy);
	        databaseRefs.PutAll(configuration.DatabaseReferences);
	        epServicesContextFactoryClassName = configuration.EPServicesContextFactoryClassName;
	        plugInViews.AddRange(configuration.PlugInViews);
            adapterLoaders.AddRange(configuration.AdapterLoaders);
            plugInAggregation.AddRange(configuration.PlugInAggregationFunctions);
            plugInPatternObjects.AddRange(configuration.PlugInPatternObjects);
	        defaultPropertyResolutionStyle = configuration.DefaultPropertyResolutionStyle;
	    }

        /// <summary>
        /// Gets or sets the property resolution style.
        /// </summary>
        /// <value>The property resolution style.</value>

        public PropertyResolutionStyle DefaultPropertyResolutionStyle
        {
            get { return defaultPropertyResolutionStyle; }
        }

	    /// <summary>Gets event type alias to type name mapping.</summary>
	    public IDictionary<String, String> TypeAliases
	    {
	        get { return typeAliases; }
	    }

	    /// <summary>Returns map of event alias and XML DOM configs.</summary>
	    /// <returns>event alias to XML DOM config mapping</returns>
	    public IDictionary<String, ConfigurationEventTypeXMLDOM> XmlDOMAliases
	    {
	        get { return xmlDOMAliases; }
	    }

	    /// <summary>Returns list of automatic import packages and classes.</summary>
	    /// <returns>automatic imports, or zero-length array if none</returns>
	    public String[] AutoImports
	    {
	        get { return autoImports; }
	    }

	    /// <summary>Returns a map of event type alias to Map-event type properties.</summary>
	    /// <returns>alias to event properties mapping for Map event types</returns>
	    public EDictionary<String, Properties> MapAliases
	    {
	        get { return mapAliases; }
	    }

	    /// <summary>
	    /// Returns the map of event type alias to legacy event type configuration.
	    /// </summary>
	    /// <returns>map with alias as the key and legacy type config as the value</returns>
	    public EDictionary<String, ConfigurationEventTypeLegacy> LegacyAliases
	    {
	        get { return legacyAliases; }
	    }

	    /// <summary>Returns a map of database name to database configuration.</summary>
	    /// <returns>database configs</returns>
	    public EDictionary<String, ConfigurationDBRef> DatabaseRefs
	    {
	        get { return databaseRefs; }
	    }

	    /// <summary>Returns the services context factory class name</summary>
	    /// <returns>class name</returns>
	    public String EPServicesContextFactoryClassName
	    {
	        get { return epServicesContextFactoryClassName; }
	    }

	    /// <summary>Returns a list of configured plug-in views.</summary>
	    /// <returns>configs for views</returns>
	    public IList<ConfigurationPlugInView> PlugInViews
	    {
	        get { return plugInViews; }
	    }

	    /// <summary>
	    /// Returns a list of adapter loaders configured for the engine instance.
	    /// </summary>
	    /// <returns>list of loader</returns>
	    public IList<ConfigurationAdapterLoader> AdapterLoaders
	    {
	        get { return adapterLoaders; }
	    }

	    /// <summary>Returns a list of configured aggregation functions.</summary>
	    /// <returns>aggregation function configs</returns>
	    public IList<ConfigurationPlugInAggregationFunction> PlugInAggregation
	    {
	        get { return plugInAggregation; }
	    }

	    /// <summary>Returns the list of configured pattern objects plugged-in.</summary>
	    /// <returns>list of pattern objects</returns>
	    public IList<ConfigurationPlugInPatternObject> PlugInPatternObjects
	    {
	        get { return plugInPatternObjects; }
	    }
	}
}

