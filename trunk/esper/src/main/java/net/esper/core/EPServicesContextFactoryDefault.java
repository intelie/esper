package net.esper.core;

import net.esper.client.ConfigurationEventTypeLegacy;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.ConfigurationException;
import net.esper.client.ConfigurationPlugInAggregationFunction;
import net.esper.eql.core.EngineImportService;
import net.esper.eql.core.EngineImportServiceImpl;
import net.esper.eql.core.EngineImportException;
import net.esper.eql.core.EngineSettingsService;
import net.esper.eql.db.DatabaseConfigService;
import net.esper.eql.db.DatabaseConfigServiceImpl;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterServiceBase;
import net.esper.event.EventAdapterServiceImpl;
import net.esper.schedule.ScheduleBucket;
import net.esper.schedule.SchedulingService;
import net.esper.schedule.SchedulingServiceProvider;
import net.esper.util.JavaClassHelper;
import net.esper.util.ManagedReadWriteLock;
import net.esper.view.ViewResolutionService;
import net.esper.view.ViewResolutionServiceImpl;
import net.esper.pattern.PatternObjectResolutionService;
import net.esper.pattern.PatternObjectResolutionServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Factory for services context.
 */
public class EPServicesContextFactoryDefault implements EPServicesContextFactory
{
    public EPServicesContext createServicesContext(String engineURI, ConfigurationSnapshot configSnapshot)
    {
        // Make services that depend on snapshot config entries
        EventAdapterServiceImpl eventAdapterService = new EventAdapterServiceImpl();
        init(eventAdapterService, configSnapshot);

        // New read-write lock for concurrent event processing
        ManagedReadWriteLock eventProcessingRWLock = new ManagedReadWriteLock("EventProcLock");

        SchedulingService schedulingService = SchedulingServiceProvider.newService();
        EngineImportService engineImportService = makeEngineImportService(configSnapshot);
        EngineSettingsService engineSettingsService = new EngineSettingsService(configSnapshot.getEngineDefaults());
        DatabaseConfigService databaseConfigService = makeDatabaseRefService(configSnapshot, schedulingService);
        ViewResolutionService viewResolutionService = new ViewResolutionServiceImpl(configSnapshot.getPlugInViews());
        PatternObjectResolutionService patternObjectResolutionService = new PatternObjectResolutionServiceImpl(configSnapshot.getPlugInPatternObjects());

        // JNDI context for binding resources
        EngineEnvContext jndiContext = new EngineEnvContext();

        // Statement context factory
        StatementContextFactory statementContextFactory = new StatementContextFactoryDefault();

        // New services context
        EPServicesContext services = new EPServicesContext(engineURI, engineURI, schedulingService,
                eventAdapterService, engineImportService, engineSettingsService, databaseConfigService, viewResolutionService,
                new StatementLockFactoryImpl(), eventProcessingRWLock, null, jndiContext, statementContextFactory,
                patternObjectResolutionService);

        // Circular dependency
        StatementLifecycleSvc statementLifecycleSvc = new StatementLifecycleSvcImpl(services);
        services.setStatementLifecycleSvc(statementLifecycleSvc);

        return services;
    }

    /**
     * Initialize event adapter service for config snapshot.
     * @param eventAdapterService is events adapter
     * @param configSnapshot is the config snapshot
     */
    protected static void init(EventAdapterServiceBase eventAdapterService, ConfigurationSnapshot configSnapshot)
    {
        // Extract legacy event type definitions for each event type alias, if supplied.
        //
        // We supply this information as setup information to the event adapter service
        // to allow discovery of superclasses and interfaces during event type construction for bean events,
        // such that superclasses and interfaces can use the legacy type definitions.
        Map<String, ConfigurationEventTypeLegacy> classLegacyInfo = new HashMap<String, ConfigurationEventTypeLegacy>();
        for (Map.Entry<String, String> entry : configSnapshot.getJavaClassAliases().entrySet())
        {
            String aliasName = entry.getKey();
            String className = entry.getValue();
            ConfigurationEventTypeLegacy legacyDef = configSnapshot.getLegacyAliases().get(aliasName);
            if (legacyDef != null)
            {
                classLegacyInfo.put(className, legacyDef);
            }
        }
        eventAdapterService.setClassLegacyConfigs(classLegacyInfo);

        // Add from the configuration the Java event class aliases
        Map<String, String> javaClassAliases = configSnapshot.getJavaClassAliases();
        for (Map.Entry<String, String> entry : javaClassAliases.entrySet())
        {
            // Add Java class alias
            try
            {
                String aliasName = entry.getKey();
                eventAdapterService.addBeanType(aliasName, entry.getValue());
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
            }
        }

        // Add from the configuration the XML DOM aliases and type def
        Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = configSnapshot.getXmlDOMAliases();
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
        Map<String, Properties> mapAliases = configSnapshot.getMapAliases();
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
    protected static EngineImportService makeEngineImportService(ConfigurationSnapshot configSnapshot)
    {
        EngineImportService engineImportService = new EngineImportServiceImpl();

        // Add auto-imports
        try
        {
            for (String importName : configSnapshot.getAutoImports())
            {
                engineImportService.addImport(importName);
            }

            for (ConfigurationPlugInAggregationFunction config : configSnapshot.getPlugInAggregation())
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
    protected static DatabaseConfigService makeDatabaseRefService(ConfigurationSnapshot configSnapshot,
                                                          SchedulingService schedulingService)
    {
        DatabaseConfigService databaseConfigService = null;

        // Add auto-imports
        try
        {
            ScheduleBucket allStatementsBucket = schedulingService.allocateBucket();
            databaseConfigService = new DatabaseConfigServiceImpl(configSnapshot.getDatabaseRefs(), schedulingService, allStatementsBucket);
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

            if ("string".equals(className))
            {
                className = String.class.getName();
            }

            // use the boxed type for primitives
            String boxedClassName = JavaClassHelper.getBoxedClassName(className);

            Class clazz = null;
            try
            {
                clazz = Class.forName(boxedClassName);
            }
            catch (ClassNotFoundException ex)
            {
                throw new EventAdapterException("Unable to load class '" + boxedClassName + "', class not found", ex);
            }

            propertyTypes.put((String) property, clazz);
        }
        return propertyTypes;
    }
}
