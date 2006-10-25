package net.esper.core;

import net.esper.client.*;
import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.AutoImportServiceImpl;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterServiceImpl;
import net.esper.event.EventAdapterService;
import net.esper.util.JavaClassHelper;
import net.esper.client.logstate.LogEntryHandler;
import net.esper.client.logstate.LogEntry;
import net.esper.client.logstate.LogEntryType;
import net.esper.client.logstate.LogKey;
import net.esper.persist.LogContextNode;
import net.esper.persist.LogServiceImpl;
import net.esper.persist.LogServiceUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProvider
{
    private volatile EPServiceEngine engine;
    private ConfigurationSnapshot configSnapshot;
    private final String optionalURI;

    public void startTransaction()
    {
        engine.getServices().getLogService().getLogEntryHandler().startTransaction();
    }

    public void prepareCommit()
    {
        engine.getServices().getLogService().flush();
    }

    public void commit()
    {
        engine.getServices().getLogService().getLogEntryHandler().commit();
    }

    public void replayLogs(LogEntry[] logEntries)
    {
        if (logEntries.length == 0)
        {
            log.info(".replayLogs No logs to replay");
            return;
        }

        log.info(".replayLogs Unflattening logs=...\n" + LogEntry.print(logEntries));

        // re-create all statements
        for (int i = 0; i < logEntries.length; i++)
        {
            if (logEntries[i].getType() != LogEntryType.STATEMENT)
            {
                continue;
            }
            String eql = (String) logEntries[i].getState();
            log.info(".replayLogs Playing in eql=" + eql);
            engine.getAdmin().createEQL(eql);
        }

        // then map the remainder of the tree to each statement's tree
        log.info(".replayLogs Restoring state");
        LogContextNode engineParentNode = engine.getServices().getLogContext().getRootNode();
        Map<LogKey, LogContextNode> engineMap = LogServiceUtil.getDeepNodeMap(engineParentNode);
        Map<LogKey, Serializable> restoreMap = LogServiceUtil.getStateMap(logEntries);

        // Restore each entry
        for (LogKey key : restoreMap.keySet())
        {
            LogContextNode engineNode = engineMap.get(key);
            if (engineNode == null)
            {
                throw new IllegalStateException("Could not find matching engine node " + key);
            }
            Serializable state = restoreMap.get(key);
            log.info(".replayLogs Restoring key=" + key + " value=" + state);

            engineNode.setState(state);
        }
    }

    /**
     * Constructor - initializes services.
     * @param configuration is the engine configuration
     * @throws ConfigurationException is thrown to indicate a configuraton error
     */
    public EPServiceProviderImpl(String optionalURI, Configuration configuration) throws ConfigurationException
    {
        this.optionalURI = optionalURI;
        configSnapshot = new ConfigurationSnapshot(configuration);
        initialize();
    }

    public void setConfiguration(Configuration configuration)
    {
        configSnapshot = new ConfigurationSnapshot(configuration);
    }

    public EPRuntime getEPRuntime()
    {
        return engine.getRuntime();
    }

    public EPAdministrator getEPAdministrator()
    {
        return engine.getAdmin();
    }

    public void initialize()
    {
        if (engine != null)
        {
            engine.getServices().getTimerService().stopInternalClock(false);
            // Give the timer thread a little moment to catch up
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ex)
            {
                // No logic required here
            }
        }

        // Make services that depend on snapshot config entries
        EventAdapterService eventAdapterService = makeEventAdapterService(configSnapshot);
        AutoImportService autoImportService = makeAutoImportService(configSnapshot);

        // New services context
        LogEntryHandler logHandler = configSnapshot.getLogEntryHandler();
        EPServicesContext services = new EPServicesContext(optionalURI, eventAdapterService, autoImportService, logHandler);

        // New runtime
        EPRuntimeImpl runtime = new EPRuntimeImpl(services);

        // Configure services to use the new runtime
        services.setInternalEventRouter(runtime);
        services.getTimerService().setCallback(runtime);

        // New admin
        EPAdministratorImpl admin = new EPAdministratorImpl(services);

        // Start clocking
        services.getTimerService().startInternalClock();

        // Give the timer thread a little moment to start up
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
            // No logic required here
        }

        // Save engine instance
        engine = new EPServiceEngine(services, runtime, admin);
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

    private static EventAdapterService makeEventAdapterService(ConfigurationSnapshot configSnapshot)
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

        EventAdapterServiceImpl eventAdapterService = new EventAdapterServiceImpl(classLegacyInfo);

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

        return eventAdapterService;
    }

    private static AutoImportService makeAutoImportService(ConfigurationSnapshot configSnapshot)
    {
        AutoImportService autoImportService = null;

        // Add auto-imports
        try
        {
            autoImportService = new AutoImportServiceImpl(configSnapshot.getAutoImports());
        }
        catch (IllegalArgumentException ex)
        {
            throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
        }

        return autoImportService;
    }

    /**
     * Snapshot of Configuration is held for re-initializing engine state
     * from prior configuration values that may have been muted.
     */
    public final class ConfigurationSnapshot
    {
        private Map<String, String> javaClassAliases = new HashMap<String, String>();
        private Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new HashMap<String, ConfigurationEventTypeXMLDOM>();
        private Map<String, ConfigurationEventTypeLegacy> legacyAliases = new HashMap<String, ConfigurationEventTypeLegacy>();
        private String[] autoImports;
        private Map<String, Properties> mapAliases = new HashMap<String, Properties>();
        private LogEntryHandler logEntryHandler;

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
            this.logEntryHandler = configuration.getLogEntryHandler();
        }

        public LogEntryHandler getLogEntryHandler()
        {
            return logEntryHandler;
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
    }

    private class EPServiceEngine
    {
        private EPServicesContext services;
        private EPRuntimeImpl runtime;
        private EPAdministratorImpl admin;

        public EPServiceEngine(EPServicesContext services, EPRuntimeImpl runtime, EPAdministratorImpl admin)
        {
            this.services = services;
            this.runtime = runtime;
            this.admin = admin;
        }

        public EPServicesContext getServices()
        {
            return services;
        }

        public EPRuntimeImpl getRuntime()
        {
            return runtime;
        }

        public EPAdministratorImpl getAdmin()
        {
            return admin;
        }
    }

    private static Log log = LogFactory.getLog(EPServiceProviderImpl.class);
}
