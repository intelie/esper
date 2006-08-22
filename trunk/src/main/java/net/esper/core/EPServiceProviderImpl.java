package net.esper.core;

import java.util.Map;

import net.esper.adapter.EPAdapterManager;
import net.esper.client.Configuration;
import net.esper.client.ConfigurationEventTypeXMLDOM;
import net.esper.client.ConfigurationException;
import net.esper.client.EPAdministrator;
import net.esper.client.EPRuntime;
import net.esper.client.EPServiceProvider;
import net.esper.eql.expression.AutoImportService;
import net.esper.eql.expression.AutoImportServiceImpl;
import net.esper.event.EventAdapterException;
import net.esper.event.EventAdapterServiceImpl;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProvider
{
    private EPServicesContext services;
    private EPRuntimeImpl runtime;
    private EPAdministratorImpl admin;
    private EPAdapterManager adapters;

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
                throw new ConfigurationException("Error configuring engine:" + ex.getMessage(), ex);
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
                throw new ConfigurationException("Error configuring engine:" + ex.getMessage(), ex);
            }
        }
        
        // Add aliases for map events
        Map<String, Map<String, Class>> mapAliases = configuration.getMapAliases();
        for(String eventTypeAlias : mapAliases.keySet())
        {
        	try
        	{
        		eventAdapterService.addMapType(eventTypeAlias, mapAliases.get(eventTypeAlias));
        	} 
        	catch (EventAdapterException ex)
        	{
        		throw new ConfigurationException("Error configuring engine:" + ex.getMessage(), ex);
        	}
        }

        // Add auto-imports
        try
        {
        	autoImportService = new AutoImportServiceImpl(configuration.getImports().toArray(new String[0]));
        }
        catch (IllegalArgumentException ex)
        {
        	throw new ConfigurationException("Error configuring engine:" + ex.getMessage(), ex);
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
    
    public EPAdapterManager getEPAdapters()
    {
    	return adapters;
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
        
        // New adapter manager
        adapters = new EPAdapterManager(runtime, eventAdapterService, services.getSchedulingService());

        // Configure services
        services.setInternalEventRouter(runtime);
        services.getTimerService().setCallback(runtime);

        // New admin
        admin = new EPAdministratorImpl(services);

        // Start clocking
        services.getTimerService().startInternalClock();
    }
}
