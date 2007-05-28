/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.core;

import net.esper.adapter.AdapterLoader;
import net.esper.client.*;
import net.esper.event.EventAdapterService;
import net.esper.schedule.SchedulingService;
import net.esper.filter.FilterService;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.List;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProviderSPI
{
    private volatile EPServiceEngine engine;
    private ConfigurationSnapshot configSnapshot;
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

    /**
     * Sets engine configuration information for use in the next initialize.
     * @param configuration is the engine configs
     */
    public void setConfiguration(Configuration configuration)
    {
        configSnapshot = new ConfigurationSnapshot(configuration);
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

    public FilterService getFilterService()
    {
        return engine.getServices().getFilterService();
    }

    public Context getEnvContext()
    {
        return engine.getServices().getEngineEnvContext();
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

        // Statement lifycycle init
        services.getStatementLifecycleSvc().init();

        // New admin
        ConfigurationOperations configOps = new ConfigurationOperationsImpl(services.getEventAdapterService(), services.getEngineImportService());
        EPAdministratorImpl admin = new EPAdministratorImpl(services, configOps);

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

        // Load and initialize adapter loader classes
        loadAdapters(configSnapshot, services);
    }

    /**
     * Loads and initializes adapter loaders.
     * @param configuration is the engine configs
     * @param services is the engine instance services
     */
    private void loadAdapters(ConfigurationSnapshot configuration, EPServicesContext services)
    {
        List<ConfigurationAdapterLoader> adapterLoaders = configuration.getAdapterLoaders();
        if ((adapterLoaders == null) || (adapterLoaders.size() == 0))
        {
            return;
        }
        for (ConfigurationAdapterLoader config : adapterLoaders)
        {
            String className = config.getClassName();
            Class adapterLoaderClass;
            try
            {
                adapterLoaderClass = Class.forName(className);
            }
            catch (ClassNotFoundException ex)
            {
                throw new ConfigurationException("Failed to load adapter loader class '" + className + "'", ex);
            }

            Object adapterLoaderObj = null;
            try
            {
                adapterLoaderObj = adapterLoaderClass.newInstance();
            }
            catch (InstantiationException ex)
            {
                throw new ConfigurationException("Failed to instantiate adapter loader class '" + className + "' via default constructor", ex);
            }
            catch (IllegalAccessException ex)
            {
                throw new ConfigurationException("Illegal access to instantiate adapter loader class '" + className + "' via default constructor", ex);
            }

            AdapterLoader adapterLoader = (AdapterLoader) adapterLoaderObj;
            adapterLoader.init(config.getLoaderName(), config.getConfigProperties(), this);

            // register adapter loader in JNDI context tree
            try
            {
                services.getEngineEnvContext().bind("adapter-loader/" + config.getLoaderName(), adapterLoader);
            }
            catch (NamingException e)
            {
                throw new EPException("Failed to use context to bind adapter loader", e);
            }
        }
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
