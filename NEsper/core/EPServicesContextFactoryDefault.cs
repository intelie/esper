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
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.events;
using net.esper.pattern;
using net.esper.schedule;
using net.esper.util;
using net.esper.view;

using Properties = net.esper.compat.EDataDictionary;

namespace net.esper.core
{
	/// <summary>Factory for services context.</summary>
	public class EPServicesContextFactoryDefault : EPServicesContextFactory
	{
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

	        // JNDI context for binding resources
	        EngineEnvContext jndiContext = new EngineEnvContext();

	        // Statement context factory
	        StatementContextFactory statementContextFactory = new StatementContextFactoryDefault();

	        // New services context
	        EPServicesContext services = new EPServicesContext(engineURI, engineURI, schedulingService,
	                eventAdapterService, engineImportService, databaseConfigService, viewResolutionService,
	                new StatementLockFactoryImpl(), eventProcessingRWLock, null, jndiContext, statementContextFactory,
	                patternObjectResolutionService);

	        // Circular dependency
	        StatementLifecycleSvc statementLifecycleSvc = new StatementLifecycleSvcImpl(services);
	        servicesStatementLifecycleSvc = statementLifecycleSvc;

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
	        IDictionary<String, ConfigurationEventTypeLegacy> classLegacyInfo = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
	        foreach (Map.Entry<String, String> entry in configSnapshot.JavaClassAliases.EntrySet())
	        {
	            String aliasName = entry.Key;
	            String className = entry.Value;
	            ConfigurationEventTypeLegacy legacyDef = configSnapshot.LegacyAliases.Get(aliasName);
	            if (legacyDef != null)
	            {
	                classLegacyInfo.Put(className, legacyDef);
	            }
	        }
	        eventAdapterServiceClassLegacyConfigs = classLegacyInfo;

	        // Add from the configuration the Java event class aliases
	        IDictionary<String, String> javaClassAliases = configSnapshot.JavaClassAliases;
	        foreach (Map.Entry<String, String> entry in javaClassAliases.EntrySet())
	        {
	            // Add Java class alias
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
	        foreach (Map.Entry<String, ConfigurationEventTypeXMLDOM> entry in xmlDOMAliases.EntrySet())
	        {
	            // Add Java class alias
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
	        foreach (Map.Entry<String, Properties> entry in mapAliases.EntrySet())
	        {
	            try
	            {
	                IDictionary<String, Class> propertyTypes = CreatePropertyTypes(entry.Value);
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
	        catch (IllegalArgumentException ex)
	        {
	            throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
	        }

	        return databaseConfigService;
	    }

	    private static IDictionary<String, Class> CreatePropertyTypes(Properties properties)
	    {
	        IDictionary<String, Class> propertyTypes = new EHashDictionary<String, Class>();
	        foreach (Object property in properties.KeySet())
	        {
	            String className = (String) properties.Get(property);

	            if ("string".Equals(className))
	            {
	                className = typeof(String).FullName;
	            }

	            // use the boxed type for primitives
	            String boxedClassName = TypeHelper.GetBoxedClassName(className);

	            Class clazz = null;
	            try
	            {
	                clazz = Class.ForName(boxedClassName);
	            }
	            catch (ClassNotFoundException ex)
	            {
	                throw new EventAdapterException("Unable to load class '" + boxedClassName + "', class not found", ex);
	            }

	            propertyTypes.Put((String) property, clazz);
	        }
	        return propertyTypes;
	    }
	}
} // End of namespace
