package net.esper.core;

import net.esper.client.Configuration;
import net.esper.client.ConfigurationException;
import net.esper.client.EPAdministrator;
import net.esper.client.EPRuntime;
import net.esper.event.EventAdapterService;
import net.esper.schedule.SchedulingService;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProviderSPI
{
    private volatile EPServiceEngine engine;
    private final ConfigurationSnapshot configSnapshot;
    private String engineURI;

    /**
     * Constructor - initializes services.
     * @param configuration is the engine configuration
     * @param engineURI is the engine URI or null if this is the default provider
     * @throws ConfigurationException is thrown to indicate a configuraton error
     */
    public EPServiceProviderImpl(Configuration configuration, String engineURI) throws ConfigurationException
    {
        this.engineURI = engineURI;
        configSnapshot = new ConfigurationSnapshot(configuration);
        initialize();
    }

    public String getURI()
    {
        return engineURI;
    }

    public EPRuntime getEPRuntime()
    {
        return engine.getRuntime();
    }

    public EPAdministrator getEPAdministrator()
    {
        return engine.getAdmin();
    }

    public EventAdapterService getEventAdapterService()
    {
        return engine.getServices().getEventAdapterService();
    }

    public SchedulingService getSchedulingService()
    {
        return engine.getServices().getSchedulingService();
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

            engine.getServices().destroy();
        }

        // Make EP services context factory
        String epServicesContextFactoryClassName = configSnapshot.getEPServicesContextFactoryClassName();
        EPServicesContextFactory epServicesContextFactory;
        if (epServicesContextFactoryClassName == null)
        {
            // Check system properties
            epServicesContextFactoryClassName = System.getProperty("ESPER_EPSERVICE_CONTEXT_FACTORY_CLASS");
        }
        if (epServicesContextFactoryClassName == null)
        {
            epServicesContextFactory = new EPServicesContextFactoryDefault();
        }
        else
        {
            Class clazz;
            try
            {
                clazz = Class.forName(epServicesContextFactoryClassName);
            }
            catch (ClassNotFoundException e)
            {
                throw new ConfigurationException("Class '" + epServicesContextFactoryClassName + "' cannot be loaded");
            }

            Object obj;
            try
            {
                obj = clazz.newInstance();
            }
            catch (InstantiationException e)
            {
                throw new ConfigurationException("Class '" + clazz + "' cannot be instantiated");
            }
            catch (IllegalAccessException e)
            {
                throw new ConfigurationException("Illegal access instantiating class '" + clazz);
            }

            epServicesContextFactory = (EPServicesContextFactory) obj;
        }

        EPServicesContext services = epServicesContextFactory.createServicesContext(engineURI, configSnapshot);

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

    private static class EPServiceEngine
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
}
