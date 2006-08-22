package net.esper.core;

import net.esper.client.*;
import net.esper.eql.expression.AutoImportService;
import net.esper.eql.expression.AutoImportServiceImpl;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterServiceImpl;
import net.esper.util.JavaClassHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProvider
{
    private EPServicesContext services;
    private EPRuntimeImpl runtime;
    private EPAdministratorImpl admin;

    private final EventAdapterServiceImpl eventAdapterService;
    private final AutoImportService autoImportService;

    /**
     * Constructor - initializes services.
     * @param configuration is the engine configuration
     * @throws ConfigurationException is thrown to indicate a configuraton error
     */
    public EPServiceProviderImpl(Configuration configuration) throws ConfigurationException
    {
        eventAdapterService = new EventAdapterServiceImpl();

        // Add from the configuration the Java event class aliases
        Map<String, String> javaClassAliases = configuration.getEventTypeAliases();
        for (Map.Entry<String, String> entry : javaClassAliases.entrySet())
        {
            // Add Java class alias
            try
            {
                eventAdapterService.addBeanType(entry.getKey(), entry.getValue());
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
            }
        }

        // Add from the configuration the XML DOM aliases and type def
        Map<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = configuration.getEventTypesXMLDOM();
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

        // Add auto-imports
        try
        {
            autoImportService = new AutoImportServiceImpl(configuration.getImports().toArray(new String[0]));
        }
        catch (IllegalArgumentException ex)
        {
            throw new ConfigurationException("Error configuring engine: " + ex.getMessage(), ex);
        }

        // Add map event types
        Map<String, Properties> mapAliases = configuration.getEventTypesMapEvents();
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

        initialize();
    }

    public EPRuntime getEPRuntime()
    {
        return runtime;
    }

    public EPAdministrator getEPAdministrator()
    {
        return admin;
    }

    public void initialize()
    {
        if (services != null)
        {
            services.getTimerService().stopInternalClock(false);
            try
            {
                // Give the timer thread a little moment to catch up
                Thread.sleep(100);
            }
            catch (InterruptedException ex)
            {
                // No logic required here
            }
        }

        // New services
        services = new EPServicesContext(eventAdapterService, autoImportService);

        // New runtime
        runtime = new EPRuntimeImpl(services);

        // Configure services
        services.setInternalEventRouter(runtime);
        services.getTimerService().setCallback(runtime);

        // New admin
        admin = new EPAdministratorImpl(services);

        // Start clocking
        services.getTimerService().startInternalClock();
    }

    private Map<String, Class> createPropertyTypes(Properties properties)
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
