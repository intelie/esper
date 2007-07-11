using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.adapter;
using net.esper.client;
using net.esper.events;
using net.esper.filter;
using net.esper.schedule;
using net.esper.util;

namespace net.esper.core
{
    /// <summary>
    /// Service provider encapsulates the engine's services for runtime and administration interfaces.
    /// </summary>

    public class EPServiceProviderImpl : EPServiceProviderSPI
    {
        private volatile EPServiceEngine engine;
        private ConfigurationSnapshot configSnapshot;
		private string engineURI;

		/// <summary>
		/// Gets the engine URI
		/// </summary>
		
		virtual public string URI
		{
			get { return engineURI; }
		}
		
		/// <summary>
		/// Sets the configuration
		/// </summary>
		
		virtual public Configuration Configuration
		{
			set { configSnapshot = new ConfigurationSnapshot( value ) ; }
		}

        /// <summary>
        /// Get the EventAdapterService for this engine.
        /// </summary>
        /// <value></value>
        /// <returns>the EventAdapterService</returns>
		public EventAdapterService EventAdapterService
	    {
	        get { return engine.Services.EventAdapterService; }
	    }

        /// <summary>
        /// Get the SchedulingService for this engine.
        /// </summary>
        /// <value></value>
        /// <returns>the SchedulingService</returns>
	    public SchedulingService SchedulingService
	    {
	        get { return engine.Services.SchedulingService; }
	    }

        /// <summary>
        /// Returns the filter service.
        /// </summary>
        /// <value></value>
        /// <returns>filter service</returns>
	    public FilterService FilterService
	    {
	        get { return engine.Services.FilterService; }
	    }

        /// <summary>
        /// Returns the engine environment directory for engine-external
        /// resources such as adapters.
        /// </summary>
        /// <value></value>
        /// <returns>engine environment directory</returns>
	    public Directory EnvDirectory
	    {
	        get { return engine.Services.EngineDirectory; }
	    }

        /// <summary>
        /// Returns a class instance of EPRuntime.
        /// </summary>
        /// <value></value>
        /// <returns> an instance of EPRuntime
        /// </returns>
        virtual public EPRuntime EPRuntime
        {
            get { return engine.Runtime; }
        }

        /// <summary>
        /// Returns a class instance of EPAdministrator.
        /// </summary>
        /// <value></value>
        /// <returns> an instance of EPAdministrator
        /// </returns>
        virtual public EPAdministrator EPAdministrator
        {
            get { return engine.Admin; }
        }

        /// <summary> Constructor - initializes services.</summary>
        /// <param name="configuration">is the engine configuration</param>
		/// <param name="engineURI">is the engine URI or null if this is the default provider</param>
        /// <throws>  ConfigurationException is thrown to indicate a configuraton error </throws>

        public EPServiceProviderImpl(Configuration configuration, String engineURI)
        {
			this.engineURI = engineURI;
            this.configSnapshot = new ConfigurationSnapshot(configuration);
            Initialize();
        }

        /// <summary>
        /// Frees any resources associated with this runtime instance.
        /// Stops and destroys any event filters, patterns, expressions, views.
        /// </summary>
        public void Initialize()
        {
            if (engine != null)
            {
                engine.Services.TimerService.StopInternalClock(false);
                // Give the timer thread a little moment to catch up
                try
                {
                    Thread.Sleep( 100 ) ;
                }
                catch (ThreadInterruptedException)
                {
                    // No logic required here
                }
				
				engine.Services.Destroy();
            }

	        // Make EP services context factory
	        String epServicesContextFactoryClassName = configSnapshot.EPServicesContextFactoryClassName;
	        EPServicesContextFactory epServicesContextFactory;
	        if (epServicesContextFactoryClassName == null)
	        {
	            // Check system properties
	            epServicesContextFactoryClassName = Environment.GetEnvironmentVariable("ESPER_EPSERVICE_CONTEXT_FACTORY_CLASS");
	        }
	        if (epServicesContextFactoryClassName == null)
	        {
	            epServicesContextFactory = new EPServicesContextFactoryDefault();
	        }
	        else
	        {
	            Type type;
	            try
	            {
                    type = TypeHelper.ResolveType(epServicesContextFactoryClassName);
	            }
	            catch (TypeLoadException)
	            {
	                throw new ConfigurationException("Class '" + epServicesContextFactoryClassName + "' cannot be loaded");
	            }

	            Object obj;
	            try
	            {
	                obj = Activator.CreateInstance(type);
	            }
	            catch (TypeLoadException e)
	            {
	                throw new ConfigurationException("Type '" + type + "' cannot be instantiated");
	            }
	            catch (MethodAccessException e)
	            {
	                throw new ConfigurationException("Illegal access instantiating type '" + type);
	            }

	            epServicesContextFactory = (EPServicesContextFactory) obj;
	        }

	        EPServicesContext services = epServicesContextFactory.CreateServicesContext(engineURI, configSnapshot);

            // New runtime
            EPRuntimeImpl runtime = new EPRuntimeImpl(services);

            // Configure services to use the new runtime
            services.InternalEventRouter = runtime;
            services.TimerService.Callback = runtime.TimerCallback;

            // New admin
			ConfigurationOperations configOps = new ConfigurationOperationsImpl(services.EventAdapterService, services.EngineImportService);
			EPAdministratorImpl admin = new EPAdministratorImpl(services, configOps);

            // Start clocking
            services.TimerService.StartInternalClock();

            // Give the timer thread a little moment to Start up
            try
            {
                Thread.Sleep( 100 ) ;
            }
            catch (ThreadInterruptedException)
            {
                // No logic required here
            }

            // Save engine instance
            engine = new EPServiceEngine(services, runtime, admin);
        }

		/// <summary>
		/// Loads and initializes adapter loaders. 
		/// </summary>
        /// <param name="configuration">the engine configs</param>
        /// <param name="services">the engine instance services</param>
	    private void LoadAdapters(ConfigurationSnapshot configuration, EPServicesContext services)
	    {
	        IList<ConfigurationAdapterLoader> adapterLoaders = configuration.AdapterLoaders;
	        if ((adapterLoaders == null) || (adapterLoaders.Count == 0))
	        {
	            return;
	        }
	        foreach (ConfigurationAdapterLoader config in adapterLoaders)
	        {
	            String typeName = config.TypeName;
	            Type adapterLoaderClass;
	            try
	            {
	                adapterLoaderClass = TypeHelper.ResolveType(typeName);
	            }
	            catch (TypeLoadException ex)
	            {
	                throw new ConfigurationException("Failed to load adapter loader class '" + typeName + "'", ex);
	            }

	            Object adapterLoaderObj = null;
	            try
	            {
	                adapterLoaderObj = Activator.CreateInstance(adapterLoaderClass);
	            }
	            catch (MethodAccessException ex)
	            {
	                throw new ConfigurationException("Illegal access to instantiate adapter loader class '" + typeName + "' via default constructor", ex);
	            }

	            AdapterLoader adapterLoader = (AdapterLoader) adapterLoaderObj;
	            adapterLoader.Init(config.LoaderName, config.ConfigProperties, this);

	            // register adapter loader in JNDI context tree
	            try
	            {
	                services.EngineDirectory.Bind("adapter-loader/" + config.LoaderName, adapterLoader);
	            }
	            catch (DirectoryException e)
	            {
	                throw new EPException("Failed to use context to bind adapter loader", e);
	            }
	        }
		}

        private class EPServiceEngine
        {
            virtual public EPServicesContext Services
            {
                get { return services; }
            }

            virtual public EPRuntimeImpl Runtime
            {
                get { return runtime; }
            }

            virtual public EPAdministratorImpl Admin
            {
                get { return admin; }
            }

            private EPServicesContext services;
            private EPRuntimeImpl runtime;
            private EPAdministratorImpl admin;

            public EPServiceEngine(EPServicesContext services, EPRuntimeImpl runtime, EPAdministratorImpl admin)
            {
                this.services = services;
                this.runtime = runtime;
                this.admin = admin;
            }
        }
    }
}
