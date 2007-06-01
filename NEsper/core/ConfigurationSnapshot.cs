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

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.core
{
	/// <summary>
	/// Snapshot of Configuration is held for re-initializing engine state
	/// from prior configuration values that may have been muted.
	/// </summary>
	public class ConfigurationSnapshot
	{
	    private IDictionary<String, String> typeAliases = new EHashDictionary<String, String>();
	    private IDictionary<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new EHashDictionary<String, ConfigurationEventTypeXMLDOM>();
	    private IDictionary<String, ConfigurationEventTypeLegacy> legacyAliases = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
	    private String[] autoImports;
	    private IDictionary<String, Properties> mapAliases = new EHashDictionary<String, Properties>();
	    private IDictionary<String, ConfigurationDBRef> databaseRefs = new EHashDictionary<String, ConfigurationDBRef>();
	    private String epServicesContextFactoryClassName;
	    private List<ConfigurationPlugInView> plugInViews = new LinkedList<ConfigurationPlugInView>();
	    private List<ConfigurationAdapterLoader> adapterLoaders = new LinkedList<ConfigurationAdapterLoader>();
	    private List<ConfigurationPlugInAggregationFunction> plugInAggregation = new LinkedList<ConfigurationPlugInAggregationFunction>();
	    private List<ConfigurationPlugInPatternObject> plugInPatternObjects = new LinkedList<ConfigurationPlugInPatternObject>();

	    /// <summary>
	    /// Ctor.
	    /// <p>
	    /// Copies information out of configuration performing a deep copy
	    /// to preserve configs.
	    /// </summary>
	    /// <param name="configuration">is the client configuration holder</param>
	    public ConfigurationSnapshot(Configuration configuration)
	    {
	        typeAliases.PutAll(configuration.EventTypeAliases);
	        xmlDOMAliases.PutAll(configuration.EventTypesXMLDOM);
	        autoImports = configuration.Imports.ToArray(new String[0]);
	        mapAliases.PutAll(configuration.EventTypesMapEvents);
	        legacyAliases.PutAll(configuration.EventTypesLegacy);
	        databaseRefs.PutAll(configuration.DatabaseReferences);
	        epServicesContextFactoryClassName = configuration.EPServicesContextFactoryClassName;
	        plugInViews.AddAll(configuration.PlugInViews);
	        adapterLoaders.AddAll(configuration.AdapterLoaders);
	        plugInAggregation.AddAll(configuration.PlugInAggregationFunctions);
	        plugInPatternObjects.AddAll(configuration.PlugInPatternObjects);
	    }

	    /// <summary>Gets event type alias to type name mapping.</summary>
	    public IDictionary<String, String> TypeAliases()
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
	    public IDictionary<String, Properties> MapAliases
	    {
	        get { return mapAliases; }
	    }

	    /// <summary>
	    /// Returns the map of event type alias to legacy event type configuration.
	    /// </summary>
	    /// <returns>map with alias as the key and legacy type config as the value</returns>
	    public IDictionary<String, ConfigurationEventTypeLegacy> LegacyAliases
	    {
	        get { return legacyAliases; }
	    }

	    /// <summary>Returns a map of database name to database configuration.</summary>
	    /// <returns>database configs</returns>
	    public IDictionary<String, ConfigurationDBRef> DatabaseRefs
	    {
	        get { return databaseRefs; }
	    }

	    /// <summary>Returns the services context factory class name</summary>
	    /// <returns>class name</returns>
	    public String GetEPServicesContextFactoryClassName
	    {
	        get { return epServicesContextFactoryClassName; }
	    }

	    /// <summary>Returns a list of configured plug-in views.</summary>
	    /// <returns>configs for views</returns>
	    public List<ConfigurationPlugInView> PlugInViews
	    {
	        get { return plugInViews; }
	    }

	    /// <summary>
	    /// Returns a list of adapter loaders configured for the engine instance.
	    /// </summary>
	    /// <returns>list of loader</returns>
	    public List<ConfigurationAdapterLoader> GetAdapterLoaders
	    {
	        get { return adapterLoaders; }
	    }

	    /// <summary>Returns a list of configured aggregation functions.</summary>
	    /// <returns>aggregation function configs</returns>
	    public List<ConfigurationPlugInAggregationFunction> PlugInAggregation
	    {
	        get { return plugInAggregation; }
	    }

	    /// <summary>Returns the list of configured pattern objects plugged-in.</summary>
	    /// <returns>list of pattern objects</returns>
	    public List<ConfigurationPlugInPatternObject> PlugInPatternObjects
	    {
	        get { return plugInPatternObjects; }
	    }
	}
}

