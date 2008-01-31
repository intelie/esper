package com.espertech.esper.core;

import com.espertech.esper.client.*;
import com.espertech.esper.eql.core.*;
import com.espertech.esper.eql.db.DatabaseConfigService;
import com.espertech.esper.eql.db.DatabaseConfigServiceImpl;
import com.espertech.esper.eql.spec.PluggableObjectCollection;
import com.espertech.esper.eql.view.OutputConditionFactory;
import com.espertech.esper.eql.view.OutputConditionFactoryDefault;
import com.espertech.esper.eql.named.NamedWindowServiceImpl;
import com.espertech.esper.eql.named.NamedWindowService;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.eql.variable.VariableExistsException;
import com.espertech.esper.eql.variable.VariableTypeException;
import com.espertech.esper.eql.variable.VariableServiceImpl;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.event.EventAdapterServiceImpl;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.ScheduleBucket;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.schedule.SchedulingServiceProvider;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.util.ManagedReadWriteLock;
import com.espertech.esper.timer.TimerService;
import com.espertech.esper.timer.TimerServiceImpl;
import com.espertech.esper.filter.FilterServiceProvider;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.view.stream.StreamFactoryServiceProvider;
import com.espertech.esper.view.stream.StreamFactoryService;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

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

        SchedulingService schedulingService = SchedulingServiceProvider.newService();
        EngineImportService engineImportService = makeEngineImportService(configSnapshot);
        EngineSettingsService engineSettingsService = new EngineSettingsService(configSnapshot.getEngineDefaults());
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
        TimerService timerService = new TimerServiceImpl(msecTimerResolution);

        VariableService variableService = new VariableServiceImpl(configSnapshot.getEngineDefaults().getVariables().getMsecVersionRelease(), schedulingService, null);
        initVariables(variableService, configSnapshot.getVariables());

        StatementLockFactory statementLockFactory = new StatementLockFactoryImpl();
        StreamFactoryService streamFactoryService = StreamFactoryServiceProvider.newService(configSnapshot.getEngineDefaults().getViewResources().isShareViews());
        FilterService filterService = FilterServiceProvider.newService();
        NamedWindowService namedWindowService = new NamedWindowServiceImpl(statementLockFactory, variableService);

        // New services context
        EPServicesContext services = new EPServicesContext(epServiceProvider.getURI(), schedulingService,
                eventAdapterService, engineImportService, engineSettingsService, databaseConfigService, plugInViews,
                statementLockFactory, eventProcessingRWLock, null, jndiContext, statementContextFactory,
                plugInPatternObj, outputConditionFactory, timerService, filterService, streamFactoryService,
                namedWindowService, variableService);

        // Circular dependency
        StatementLifecycleSvc statementLifecycleSvc = new StatementLifecycleSvcImpl(epServiceProvider, services);
        services.setStatementLifecycleSvc(statementLifecycleSvc);

        return services;
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

        // Add map event types
        Map<String, Properties> mapAliases = configSnapshot.getEventTypesMapEvents();
        for(Map.Entry<String, Properties> entry : mapAliases.entrySet())
        {
            try
            {
                Map<String, Class> propertyTypes = createPropertyTypes(entry.getValue());
                eventAdapterService.addMapType(entry.getKey(), propertyTypes);
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
            }
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
        for(Object property : properties.keySet())
        {
            String className = (String) properties.get(property);
            Class clazz = JavaClassHelper.getClassForSimpleName(className);
            propertyTypes.put((String) property, clazz);
        }
        return propertyTypes;
    }
}
