/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.core;

import com.espertech.esper.adapter.AdapterLoader;
import com.espertech.esper.client.*;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.SchedulingService;
import com.espertech.esper.filter.FilterService;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.util.SerializableObjectCopier;

import javax.naming.Context;
import javax.naming.NamingException;
import java.util.List;
import java.io.IOException;

/**
 * Service provider encapsulates the engine's services for runtime and administration interfaces.
 */
public class EPServiceProviderImpl implements EPServiceProviderSPI
{
    private volatile EPServiceEngine engine;
    private ConfigurationInformation configSnapshot;
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
        configSnapshot = takeSnapshot(configuration);
        initialize();
    }

    /**
     * Sets engine configuration information for use in the next initialize.
     * @param configuration is the engine configs
     */
    public void setConfiguration(Configuration configuration)
    {
        configSnapshot = takeSnapshot(configuration);
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

    public ExtensionServicesContext getExtensionServicesContext()
    {
        return engine.getServices().getExtensionServicesContext();
    }

    public StatementLifecycleSvc getStatementLifecycleSvc()
    {
        return engine.getServices().getStatementLifecycleSvc();
    }

    public Context getContext()
    {
        return engine.getServices().getEngineEnvContext();
    }

    public void destroy()
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

            engine.getRuntime().destroy();
            engine.getAdmin().destroy();
            engine.getServices().destroy();

            engine.getServices().initialize();
        }

        engine = null;
    }

    public boolean isDestroyed()
    {
        return engine == null;
    }

    public void initialize()
    {
        // This setting applies to all engines in a given VM
        ExecutionPathDebugLog.setDebugEnabled(configSnapshot.getEngineDefaults().getLogging().isEnableExecutionDebug());

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

        EPServicesContext services = epServicesContextFactory.createServicesContext(this, configSnapshot);

        // New runtime
        EPRuntimeImpl runtime = new EPRuntimeImpl(services);

        // Configure services to use the new runtime
        services.setInternalEventRouter(runtime);
        services.getTimerService().setCallback(runtime);

        // Statement lifycycle init
        services.getStatementLifecycleSvc().init();

        // New admin
        ConfigurationOperations configOps = new ConfigurationOperationsImpl(services.getEventAdapterService(), services.getEngineImportService(), services.getVariableService());
        EPAdministratorImpl admin = new EPAdministratorImpl(services, configOps);

        // Start clocking
        if (configSnapshot.getEngineDefaults().getThreading().isInternalTimerEnabled())
        {
            services.getTimerService().startInternalClock();
        }

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

        // Initialize extension services
        if (services.getExtensionServicesContext() != null)
        {
            services.getExtensionServicesContext().init();
        }
    }

    /**
     * Loads and initializes adapter loaders.
     * @param configuration is the engine configs
     * @param services is the engine instance services
     */
    private void loadAdapters(ConfigurationInformation configuration, EPServicesContext services)
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

            Object adapterLoaderObj;
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

    private ConfigurationInformation takeSnapshot(Configuration configuration)
    {
        try
        {
            return (ConfigurationInformation) SerializableObjectCopier.copy(configuration);
        }
        catch (IOException e)
        {
            throw new ConfigurationException("Failed to snapshot configuration instance through serialization : " + e.getMessage(), e);
        }
        catch (ClassNotFoundException e)
        {
            throw new ConfigurationException("Failed to snapshot configuration instance through serialization : " + e.getMessage(), e);
        }
    }
}
