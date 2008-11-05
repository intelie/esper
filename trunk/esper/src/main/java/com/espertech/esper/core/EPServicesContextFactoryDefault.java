/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.client.*;
import com.espertech.esper.epl.core.EngineImportException;
import com.espertech.esper.epl.core.EngineImportService;
import com.espertech.esper.epl.core.EngineImportServiceImpl;
import com.espertech.esper.epl.core.EngineSettingsService;
import com.espertech.esper.epl.db.DatabaseConfigService;
import com.espertech.esper.epl.db.DatabaseConfigServiceImpl;
import com.espertech.esper.epl.named.NamedWindowService;
import com.espertech.esper.epl.named.NamedWindowServiceImpl;
import com.espertech.esper.epl.spec.PluggableObjectCollection;
import com.espertech.esper.epl.variable.VariableExistsException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.epl.variable.VariableServiceImpl;
import com.espertech.esper.epl.variable.VariableTypeException;
import com.espertech.esper.epl.view.OutputConditionFactory;
import com.espertech.esper.epl.view.OutputConditionFactoryDefault;
import com.espertech.esper.epl.metric.MetricReportingServiceImpl;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventAdapterServiceImpl;
import com.espertech.esper.event.vaevent.ValueAddEventServiceImpl;
import com.espertech.esper.event.vaevent.ValueAddEventService;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.filter.FilterServiceProvider;
import com.espertech.esper.plugin.PlugInEventRepresentation;
import com.espertech.esper.plugin.PlugInEventRepresentationContext;
import com.espertech.esper.schedule.ScheduleBucket;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.SchedulingServiceProvider;
import com.espertech.esper.timer.*;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.ManagedReadWriteLock;
import com.espertech.esper.util.GraphUtil;
import com.espertech.esper.util.GraphCircularDependencyException;
import com.espertech.esper.view.stream.StreamFactoryService;
import com.espertech.esper.view.stream.StreamFactoryServiceProvider;

import java.io.Serializable;
import java.net.URI;
import java.util.*;

/**
 * Factory for services context.
 */
public class EPServicesContextFactoryDefault implements EPServicesContextFactory
{
    public EPServicesContext createServicesContext(EPServiceProvider epServiceProvider, ConfigurationInformation configSnapshot)
    {
        // Make services that depend on snapshot config entries
        EventAdapterServiceImpl eventAdapterService = new EventAdapterServiceImpl();
        init(eventAdapterService, configSnapshot);

        // New read-write lock for concurrent event processing
        ManagedReadWriteLock eventProcessingRWLock = new ManagedReadWriteLock("EventProcLock", false);

        TimeSourceService timeSourceService = makeTimeSource(configSnapshot);
        SchedulingService schedulingService = SchedulingServiceProvider.newService(timeSourceService);
        EngineImportService engineImportService = makeEngineImportService(configSnapshot);
        EngineSettingsService engineSettingsService = new EngineSettingsService(configSnapshot.getEngineDefaults(), configSnapshot.getPlugInEventTypeAliasResolutionURIs());
        DatabaseConfigService databaseConfigService = makeDatabaseRefService(configSnapshot, schedulingService);

        PluggableObjectCollection plugInViews = new PluggableObjectCollection();
        plugInViews.addViews(configSnapshot.getPlugInViews());
        PluggableObjectCollection plugInPatternObj = new PluggableObjectCollection();
        plugInPatternObj.addPatternObjects(configSnapshot.getPlugInPatternObjects());

        // JNDI context for binding resources
        EngineEnvContext jndiContext = new EngineEnvContext();

        // Statement context factory
        StatementContextFactory statementContextFactory = new StatementContextFactoryDefault(plugInViews, plugInPatternObj);

        OutputConditionFactory outputConditionFactory = new OutputConditionFactoryDefault();

        long msecTimerResolution = configSnapshot.getEngineDefaults().getThreading().getInternalTimerMsecResolution();
        if (msecTimerResolution <= 0)
        {
            throw new ConfigurationException("Timer resolution configuration not set to a valid value, expecting a non-zero value");
        }
        TimerService timerService = new TimerServiceImpl(epServiceProvider.getURI(), msecTimerResolution);

        VariableService variableService = new VariableServiceImpl(configSnapshot.getEngineDefaults().getVariables().getMsecVersionRelease(), schedulingService, null);
        initVariables(variableService, configSnapshot.getVariables());

        StatementLockFactory statementLockFactory = new StatementLockFactoryImpl();
        StreamFactoryService streamFactoryService = StreamFactoryServiceProvider.newService(configSnapshot.getEngineDefaults().getViewResources().isShareViews());
        FilterService filterService = FilterServiceProvider.newService();
        NamedWindowService namedWindowService = new NamedWindowServiceImpl(statementLockFactory, variableService);

        ValueAddEventService valueAddEventService = new ValueAddEventServiceImpl();
        valueAddEventService.init(configSnapshot.getRevisionEventTypes(), configSnapshot.getVariantStreams(), eventAdapterService);

        MetricReportingServiceImpl metricsReporting = new MetricReportingServiceImpl(configSnapshot.getEngineDefaults().getMetricsReporting(), epServiceProvider.getURI());
        StatementEventTypeRef statementEventTypeRef = new StatementEventTypeRefImpl();

        // New services context
        EPServicesContext services = new EPServicesContext(epServiceProvider.getURI(), epServiceProvider.getURI(), schedulingService,
                eventAdapterService, engineImportService, engineSettingsService, databaseConfigService, plugInViews,
                statementLockFactory, eventProcessingRWLock, null, jndiContext, statementContextFactory,
                plugInPatternObj, outputConditionFactory, timerService, filterService, streamFactoryService,
                namedWindowService, variableService, timeSourceService, valueAddEventService, metricsReporting, statementEventTypeRef,
                configSnapshot);

        // Circular dependency
        StatementLifecycleSvc statementLifecycleSvc = new StatementLifecycleSvcImpl(epServiceProvider, services);
        services.setStatementLifecycleSvc(statementLifecycleSvc);

        // Observers to statement events
        statementLifecycleSvc.addObserver(metricsReporting);

        return services;
    }

    /**
     * Makes the time source provider.
     * @param configSnapshot the configuration
     * @return time source provider
     */
    protected static TimeSourceService makeTimeSource(ConfigurationInformation configSnapshot)
    {
        if (configSnapshot.getEngineDefaults().getTimeSource().getTimeSourceType() == ConfigurationEngineDefaults.TimeSourceType.NANO)
        {
            // this is a static variable to keep overhead down for getting a current time
            TimeSourceService.IS_SYSTEM_CURRENT_TIME = false;
        }
        return new TimeSourceService();
    }

    /**
     * Adds configured variables to the variable service.
     * @param variableService service to add to
     * @param variables configured variables
     */
    protected static void initVariables(VariableService variableService, Map<String, ConfigurationVariable> variables)
    {
        for (Map.Entry<String, ConfigurationVariable> entry : variables.entrySet())
        {
            try
            {
                variableService.createNewVariable(entry.getKey(), entry.getValue().getType(), entry.getValue().getInitializationValue(), null);
            }
            catch (VariableExistsException e)
            {
                throw new ConfigurationException("Error configuring variables: " + e.getMessage(), e);
            }
            catch (VariableTypeException e)
            {
                throw new ConfigurationException("Error configuring variables: " + e.getMessage(), e);
            }
        }
    }

    /**
     * Initialize event adapter service for config snapshot.
     * @param eventAdapterService is events adapter
     * @param configSnapshot is the config snapshot
     */
    protected static void init(EventAdapterService eventAdapterService, ConfigurationInformation configSnapshot)
    {
        // Extract legacy event type definitions for each event type alias, if supplied.
        //
        // We supply this information as setup information to the event adapter service
        // to allow discovery of superclasses and interfaces during event type construction for bean events,
        // such that superclasses and interfaces can use the legacy type definitions.
        Map<String, ConfigurationEventTypeLegacy> classLegacyInfo = new HashMap<String, ConfigurationEventTypeLegacy>();
        for (Map.Entry<String, String> entry : configSnapshot.getEventTypeAliases().entrySet())
        {
            String aliasName = entry.getKey();
            String className = entry.getValue();
            ConfigurationEventTypeLegacy legacyDef = configSnapshot.getEventTypesLegacy().get(aliasName);
            if (legacyDef != null)
            {
                classLegacyInfo.put(className, legacyDef);
            }
        }
        eventAdapterService.setClassLegacyConfigs(classLegacyInfo);
        eventAdapterService.setDefaultPropertyResolutionStyle(configSnapshot.getEngineDefaults().getEventMeta().getClassPropertyResolutionStyle());
        for (String javaPackage : configSnapshot.getEventTypeAutoAliasPackages())
        {
            eventAdapterService.addAutoAliasPackage(javaPackage);
        }

        // Add from the configuration the Java event class aliases
        Map<String, String> javaClassAliases = configSnapshot.getEventTypeAliases();
        for (Map.Entry<String, String> entry : javaClassAliases.entrySet())
        {
            // Add Java class alias
            try
            {
                String aliasName = entry.getKey();
                eventAdapterService.addBeanType(aliasName, entry.getValue(), false);
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
            }
        }

        // Add from the configuration the XML DOM aliases and type def
        Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = configSnapshot.getEventTypesXMLDOM();
        for (Map.Entry<String, ConfigurationEventTypeXMLDOM> entry : xmlDOMAliases.entrySet())
        {
            // Add Java class alias
            try
            {
                eventAdapterService.addXMLDOMType(entry.getKey(), entry.getValue());
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
            }
        }

        // Add maps in dependency order such that supertypes are added before subtypes
        Set<String> dependentMapOrder;
        try
        {
            dependentMapOrder = GraphUtil.getTopDownOrder(configSnapshot.getMapSuperTypes());
        }
        catch (GraphCircularDependencyException e)
        {
            throw new ConfigurationException("Error configuring engine, dependency graph between map aliases is circular: " + e.getMessage(), e);
        }

        Map<String, Properties> mapAliases = configSnapshot.getEventTypesMapEvents();
        Map<String, Map<String, Object>> nestableMapAliases = configSnapshot.getEventTypesNestableMapEvents();
        dependentMapOrder.addAll(mapAliases.keySet());
        dependentMapOrder.addAll(nestableMapAliases.keySet());
        try
        {
            for (String mapName : dependentMapOrder)
            {
                Set<String> superTypes = configSnapshot.getMapSuperTypes().get(mapName);
                Properties propertiesUnnested = mapAliases.get(mapName);
                if (propertiesUnnested != null)
                {
                    Map<String, Class> propertyTypes = createPropertyTypes(propertiesUnnested);
                    eventAdapterService.addMapType(mapName, propertyTypes, superTypes);
                }

                Map<String, Object> propertiesNestable = nestableMapAliases.get(mapName);
                if (propertiesNestable != null)
                {
                    eventAdapterService.addNestableMapType(mapName, propertiesNestable, superTypes, true, false, false);
                }
            }
        }
        catch (EventAdapterException ex)
        {
            throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
        }


        // Add plug-in event representations
        Map<URI, ConfigurationPlugInEventRepresentation> plugInReps = configSnapshot.getPlugInEventRepresentation();
        for (Map.Entry<URI, ConfigurationPlugInEventRepresentation> entry : plugInReps.entrySet())
        {
            String className = entry.getValue().getEventRepresentationClassName();
            Class eventRepClass;
            try
            {
                ClassLoader cl = Thread.currentThread().getContextClassLoader();
                eventRepClass = Class.forName(className, true, cl);
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("Failed to load plug-in event representation class '" + className + "'", ex);
            }

            Object pluginEventRepObj;
            try
            {
                pluginEventRepObj = eventRepClass.newInstance();
            }
            catch (InstantiationException ex)
            {
                throw new ConfigurationException("Failed to instantiate plug-in event representation class '" + className + "' via default constructor", ex);
            }
            catch (IllegalAccessException ex)
            {
                throw new ConfigurationException("Illegal access to instantiate plug-in event representation class '" + className + "' via default constructor", ex);
            }

            if (!(pluginEventRepObj instanceof PlugInEventRepresentation))
            {
                throw new ConfigurationException("Plug-in event representation class '" + className + "' does not implement the required interface " + PlugInEventRepresentation.class.getName());
            }

            URI eventRepURI = entry.getKey();
            PlugInEventRepresentation pluginEventRep = (PlugInEventRepresentation) pluginEventRepObj;
            Serializable initializer = entry.getValue().getInitializer();
            PlugInEventRepresentationContext context = new PlugInEventRepresentationContext(eventAdapterService, eventRepURI, initializer);

            try
            {
                pluginEventRep.init(context);
                eventAdapterService.addEventRepresentation(eventRepURI, pluginEventRep);
            }
            catch (Throwable t)
            {
                throw new ConfigurationException("Plug-in event representation class '" + className + "' and URI '" + eventRepURI + "' did not initialize correctly : " + t.getMessage(), t);
            }
        }

        // Add plug-in event type aliases
        Map<String, ConfigurationPlugInEventType> plugInAliases = configSnapshot.getPlugInEventTypes();
        for (Map.Entry<String, ConfigurationPlugInEventType> entry : plugInAliases.entrySet())
        {
            String alias = entry.getKey();
            ConfigurationPlugInEventType config = entry.getValue();
            eventAdapterService.addPlugInEventType(alias, config.getEventRepresentationResolutionURIs(), config.getInitializer());
        }
    }

    /**
     * Constructs the auto import service.
     * @param configSnapshot config info
     * @return service
     */
    protected static EngineImportService makeEngineImportService(ConfigurationInformation configSnapshot)
    {
        EngineImportServiceImpl engineImportService = new EngineImportServiceImpl();
        engineImportService.addMethodRefs(configSnapshot.getMethodInvocationReferences());

        // Add auto-imports
        try
        {
            for (String importName : configSnapshot.getImports())
            {
                engineImportService.addImport(importName);
            }

            for (ConfigurationPlugInAggregationFunction config : configSnapshot.getPlugInAggregationFunctions())
            {
                engineImportService.addAggregation(config.getName(), config.getFunctionClassName());
            }
        }
        catch (EngineImportException ex)
        {
            throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
        }

        return engineImportService;
    }

    /**
     * Creates the database config service.
     * @param configSnapshot is the config snapshot
     * @param schedulingService is the timer stuff
     * @return database config svc
     */
    protected static DatabaseConfigService makeDatabaseRefService(ConfigurationInformation configSnapshot,
                                                          SchedulingService schedulingService)
    {
        DatabaseConfigService databaseConfigService = null;

        // Add auto-imports
        try
        {
            ScheduleBucket allStatementsBucket = schedulingService.allocateBucket();
            databaseConfigService = new DatabaseConfigServiceImpl(configSnapshot.getDatabaseReferences(), schedulingService, allStatementsBucket);
        }
        catch (IllegalArgumentException ex)
        {
            throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
        }

        return databaseConfigService;
    }

    private static Map<String, Class> createPropertyTypes(Properties properties)
    {
        Map<String, Class> propertyTypes = new HashMap<String, Class>();
        for(Map.Entry entry : properties.entrySet())
        {
            String property = (String) entry.getKey();
            String className = (String) entry.getValue();
            Class clazz = JavaClassHelper.getClassForSimpleName(className);
            propertyTypes.put((String) property, clazz);
        }
        return propertyTypes;
    }
}
