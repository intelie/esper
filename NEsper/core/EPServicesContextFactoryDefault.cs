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
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.events;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;
using net.esper.view;

namespace net.esper.core
{
	/// <summary>Factory for services context.</summary>
	public class EPServicesContextFactoryDefault : EPServicesContextFactory
	{
        /// <summary>
        /// Creates the services context.
        /// </summary>
        /// <param name="engineURI">The engine URI.</param>
        /// <param name="configSnapshot">The config snapshot.</param>
        /// <returns></returns>
	    public EPServicesContext CreateServicesContext(String engineURI, ConfigurationSnapshot configSnapshot)
	    {
	        // Make services that depend on snapshot config entries
	        EventAdapterServiceImpl eventAdapterService = new EventAdapterServiceImpl();
	        Init(eventAdapterService, configSnapshot);

	        // New read-write lock for concurrent event processing
	        ManagedReadWriteLock eventProcessingRWLock = new ManagedReadWriteLock("EventProcLock");

	        SchedulingService schedulingService = SchedulingServiceProvider.NewService();
	        EngineImportService engineImportService = MakeEngineImportService(configSnapshot);
	        DatabaseConfigService databaseConfigService = MakeDatabaseRefService(configSnapshot, schedulingService);
	        ViewResolutionService viewResolutionService = new ViewResolutionServiceImpl(configSnapshot.PlugInViews);
	        PatternObjectResolutionService patternObjectResolutionService = new PatternObjectResolutionServiceImpl(configSnapshot.PlugInPatternObjects);

	        // Directory for binding resources
	        Directory resourceDirectory = new SimpleServiceDirectory();

	        // Statement context factory
	        StatementContextFactory statementContextFactory = new StatementContextFactoryDefault();

	        // New services context
	        EPServicesContext services = new EPServicesContext(engineURI, engineURI, schedulingService,
	                eventAdapterService, engineImportService, databaseConfigService, viewResolutionService,
	                new StatementLockFactoryImpl(), eventProcessingRWLock, null, resourceDirectory, statementContextFactory,
	                patternObjectResolutionService);

	        // Circular dependency
	        StatementLifecycleSvc statementLifecycleSvc = new StatementLifecycleSvcImpl(services);
	        services.StatementLifecycleSvc = statementLifecycleSvc;

	        return services;
	    }

	    /// <summary>Initialize event adapter service for config snapshot.</summary>
	    /// <param name="eventAdapterService">is events adapter</param>
	    /// <param name="configSnapshot">is the config snapshot</param>
	    protected static void Init(EventAdapterServiceBase eventAdapterService, ConfigurationSnapshot configSnapshot)
	    {
	        // Extract legacy event type definitions for each event type alias, if supplied.
	        //
	        // We supply this information as setup information to the event adapter service
	        // to allow discovery of superclasses and interfaces during event type construction for bean events,
	        // such that superclasses and interfaces can use the legacy type definitions.
	        EDictionary<String, ConfigurationEventTypeLegacy> classLegacyInfo = new HashDictionary<String, ConfigurationEventTypeLegacy>();
	        foreach (KeyValuePair<String, String> entry in configSnapshot.TypeAliases)
	        {
	            String aliasName = entry.Key;
	            String className = entry.Value;
	            ConfigurationEventTypeLegacy legacyDef = configSnapshot.LegacyAliases.Fetch(aliasName);
	            if (legacyDef != null)
	            {
	                classLegacyInfo[className] = legacyDef;
	            }
	        }
	        eventAdapterService.TypeLegacyConfigs = classLegacyInfo;

	        // Add from the configuration the event class aliases
	        IDictionary<String, String> typeAliases = configSnapshot.TypeAliases;
	        foreach (KeyValuePair<String, String> entry in typeAliases)
	        {
	            // Add type alias
	            try
	            {
	                String aliasName = entry.Key;
	                eventAdapterService.AddBeanType(aliasName, entry.Value);
	            }
	            catch (EventAdapterException ex)
	            {
	                throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	            }
	        }

	        // Add from the configuration the XML DOM aliases and type def
	        IDictionary<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = configSnapshot.XmlDOMAliases;
	        foreach (KeyValuePair<String, ConfigurationEventTypeXMLDOM> entry in xmlDOMAliases)
	        {
	            // Add type alias
	            try
	            {
	                eventAdapterService.AddXMLDOMType(entry.Key, entry.Value);
	            }
	            catch (EventAdapterException ex)
	            {
	                throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	            }
	        }

	        // Add map event types
	        IDictionary<String, Properties> mapAliases = configSnapshot.MapAliases;
	        foreach (KeyValuePair<String, Properties> entry in mapAliases)
	        {
	            try
	            {
	                EDictionary<String, Type> propertyTypes = CreatePropertyTypes(entry.Value);
	                eventAdapterService.AddMapType(entry.Key, propertyTypes);
	            }
	            catch (EventAdapterException ex)
	            {
	                throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	            }
	        }
	    }

	    /// <summary>Constructs the auto import service.</summary>
	    /// <param name="configSnapshot">config info</param>
	    /// <returns>service</returns>
	    protected static EngineImportService MakeEngineImportService(ConfigurationSnapshot configSnapshot)
	    {
	        EngineImportService engineImportService = new EngineImportServiceImpl();

	        // Add auto-imports
	        try
	        {
	            foreach (String importName in configSnapshot.AutoImports)
	            {
	                engineImportService.AddImport(importName);
	            }

	            foreach (ConfigurationPlugInAggregationFunction config in configSnapshot.PlugInAggregation)
	            {
	                engineImportService.AddAggregation(config.Name, config.FunctionClassName);
	            }
	        }
	        catch (EngineImportException ex)
	        {
	            throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	        }

	        return engineImportService;
	    }

	    /// <summary>Creates the database config service.</summary>
	    /// <param name="configSnapshot">is the config snapshot</param>
	    /// <param name="schedulingService">is the timer stuff</param>
	    /// <returns>database config svc</returns>
	    protected static DatabaseConfigService MakeDatabaseRefService(ConfigurationSnapshot configSnapshot,
	                                                          SchedulingService schedulingService)
	    {
	        DatabaseConfigService databaseConfigService = null;

	        // Add auto-imports
	        try
	        {
	            ScheduleBucket allStatementsBucket = schedulingService.AllocateBucket();
	            databaseConfigService = new DatabaseConfigServiceImpl(configSnapshot.DatabaseRefs, schedulingService, allStatementsBucket);
	        }
	        catch (ArgumentException ex)
	        {
	            throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	        }

	        return databaseConfigService;
	    }

	    private static EDictionary<String, Type> CreatePropertyTypes(Properties properties)
	    {
	        EDictionary<string, Type> propertyTypes = new HashDictionary<string, Type>();
	        foreach (string property in properties.Keys)
	        {
	            string typeName = properties.Fetch(property);
	            if (typeName == "string")
	            {
	                typeName = typeof(string).FullName;
	            }

	            // use the boxed type for primitives
	            string boxedTypeName = TypeHelper.GetBoxedTypeName(typeName);

	            Type type = null;
	            try
	            {
	            	type = Type.GetType(boxedTypeName);
	            }
                catch (TypeLoadException ex)
	            {
	                throw new EventAdapterException("Unable to load type '" + boxedTypeName + "', class not found", ex);
	            }

	            propertyTypes[(String) property] = type;
	        }
	        return propertyTypes;
	    }
	}
} // End of namespace
