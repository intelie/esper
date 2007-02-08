using System;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Threading;

using net.esper.client;
using net.esper.compat;
using net.esper.eql.core;
using net.esper.eql.db;
using net.esper.events;
using net.esper.schedule;
using net.esper.util;

namespace net.esper.core
{
    /// <summary>
    /// Service provider encapsulates the engine's services for runtime and administration interfaces.
    /// </summary>

    public class EPServiceProviderImpl : EPServiceProvider
    {
        private volatile EPServiceEngine engine;
        private readonly ConfigurationSnapshot configSnapshot;

        virtual public EPRuntime EPRuntime
        {
            get { return engine.Runtime; }
        }

        virtual public EPAdministrator EPAdministrator
        {
            get { return engine.Admin; }
        }

        /// <summary> Constructor - initializes services.</summary>
        /// <param name="configuration">is the engine configuration
        /// </param>
        /// <throws>  ConfigurationException is thrown to indicate a configuraton error </throws>

        public EPServiceProviderImpl(Configuration configuration)
        {
            configSnapshot = new ConfigurationSnapshot(configuration);
            Initialize();
        }

        public virtual void Initialize()
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
            }

            // Make services that depend on snapshot config entries
            SchedulingService schedulingService = SchedulingServiceProvider.newService();
            EventAdapterService eventAdapterService = MakeEventAdapterService(configSnapshot);
            AutoImportService autoImportService = MakeAutoImportService(configSnapshot);
            DatabaseConfigService databaseConfigService = MakeDatabaseRefService(configSnapshot, schedulingService);

            // New services context
            EPServicesContext services = new EPServicesContext(schedulingService, eventAdapterService, autoImportService, databaseConfigService);

            // New runtime
            EPRuntimeImpl runtime = new EPRuntimeImpl(services);

            // Configure services to use the new runtime
            services.InternalEventRouter = runtime;
            services.TimerService.Callback = runtime;

            // New admin
            EPAdministratorImpl admin = new EPAdministratorImpl(services);

            // Start clocking
            services.TimerService.StartInternalClock();

            // Give the timer thread a little moment to Start up
            try
            {
                Thread.Sleep(new TimeSpan((long)10000 * 100));
            }
            catch (ThreadInterruptedException)
            {
                // No logic required here
            }

            // Save engine instance
            engine = new EPServiceEngine(services, runtime, admin);
        }

        private static EDictionary<String, Type> createPropertyTypes(EDictionary<String,String> properties)
        {
            EDictionary<String, Type> propertyTypes = new EHashDictionary<String, Type>();
            foreach (string property in properties.Keys)
            {
            	String typeName = properties[property];
                if ( String.Equals( typeName, "string", StringComparison.InvariantCultureIgnoreCase ) )
                {
                    typeName = typeof(String).Name;
                }

                Type type = null;
                try
                {
                	type = Type.GetType(typeName);
                }
                catch ( TypeLoadException ex )
                {
                    throw new EventAdapterException("Unable to load type '" + typeName + "', type load exception", ex);
                }

                propertyTypes[property] = type;
            }

            return propertyTypes;
        }

        private static EventAdapterService MakeEventAdapterService(ConfigurationSnapshot configSnapshot)
        {
            // Extract legacy event type definitions for each event type alias, if supplied.
            //
            // We supply this information as setup information to the event adapter service
            // to allow discovery of superclasses and interfaces during event type construction for bean events,
            // such that superclasses and interfaces can use the legacy type definitions.

            EDictionary<String, ConfigurationEventTypeLegacy> classLegacyInfo = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
            foreach (KeyValuePair<String, String> entry in configSnapshot.TypeAliases)
            {
                String aliasName = entry.Key;
                String className = entry.Value;
                ConfigurationEventTypeLegacy legacyDef = configSnapshot.LegacyAliases.Fetch(aliasName);
                if ( legacyDef == null )
                {
                    classLegacyInfo[className] = legacyDef;
                }
            }

            EventAdapterServiceImpl eventAdapterService = new EventAdapterServiceImpl(classLegacyInfo);

            // Add from the configuration the event class aliases
            EDictionary<String, String> typeAliases = configSnapshot.TypeAliases;
            foreach (KeyValuePair<String, String> entry in typeAliases)
            {
                // Add type alias
                try
                {
                    String aliasName = entry.Key;
                    eventAdapterService.AddBeanType(aliasName, entry.Value);
                }
                catch (EventAdapterException ex)
                {
                    throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
                }
            }

            // Add from the configuration the XML DOM aliases and type def
            EDictionary<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = configSnapshot.XmlDOMAliases;
            foreach (KeyValuePair<String, ConfigurationEventTypeXMLDOM> entry in xmlDOMAliases)
            {
                // Add class alias
                try
                {
                    eventAdapterService.AddXMLDOMType(entry.Key, entry.Value);
                }
                catch (EventAdapterException ex)
                {
                    throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
                }
            }

            // Add map event types
            EDictionary<String, EDictionary<string,string>> mapAliases = configSnapshot.MapAliases;
            foreach (KeyValuePair<String, EDictionary<string,string>> entry in mapAliases)
            {
                try
                {
                    EDictionary<String, Type> propertyTypes = createPropertyTypes(entry.Value);
                    eventAdapterService.AddMapType(entry.Key, propertyTypes);
                }
                catch (EventAdapterException ex)
                {
                    throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
                }
            }

            return eventAdapterService;
        }

        private static AutoImportService MakeAutoImportService(ConfigurationSnapshot configSnapshot)
        {
            AutoImportService autoImportService = null;

            // Add auto-imports
            try
            {
                autoImportService = new AutoImportServiceImpl(configSnapshot.AutoImports);
            }
            catch (ArgumentException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
            }

            return autoImportService;
        }

        private static DatabaseConfigService MakeDatabaseRefService(ConfigurationSnapshot configSnapshot, SchedulingService schedulingService)
        {
            DatabaseConfigService databaseConfigService = null;

            // Add auto-imports
            try
            {
                ScheduleBucket allStatementsBucket = schedulingService.allocateBucket();
                databaseConfigService = new DatabaseConfigServiceImpl(configSnapshot.DatabaseRefs, schedulingService, allStatementsBucket);
            }
            catch (ArgumentException ex)
            {
                throw new ConfigurationException("Error configuring engine: " + ex.Message, ex);
            }

            return databaseConfigService;
        }

        /// <summary> Snapshot of Configuration is held for re-initializing engine state
        /// from prior configuration values that may have been muted.
        /// </summary>

        public sealed class ConfigurationSnapshot
        {
            /// <summary> Returns list of automatic import packages and classes.</summary>
            /// <returns> automatic imports, or zero-length array if none
            /// </returns>

            public String[] AutoImports
            {
                get { return autoImports; }
            }

            private EDictionary<String, String> typeAliases = new EHashDictionary<String, String>();
            private EDictionary<String, ConfigurationEventTypeXMLDOM> xmlDOMAliases = new EHashDictionary<String, ConfigurationEventTypeXMLDOM>();
            private EDictionary<String, ConfigurationEventTypeLegacy> legacyAliases = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
            private String[] autoImports;
            private EDictionary<String, EDictionary<String,String>> mapAliases = new EHashDictionary<String, EDictionary<String,String>>();
            private EDictionary<String, ConfigurationDBRef> databaseRefs = new EHashDictionary<String, ConfigurationDBRef>();

            /// <summary> Ctor.
            /// <p>
            /// Copies information out of configuration performing a deep copy
            /// to preserve configs.
            /// </summary>
            /// <param name="configuration">is the client configuration holder
            /// </param>

            public ConfigurationSnapshot(Configuration configuration)
            {
            	typeAliases.PutAll( configuration.EventTypeAliases);
                xmlDOMAliases.PutAll(configuration.EventTypesXMLDOM);
                autoImports = CollectionHelper.ToArray<string>( configuration.Imports ) ;
                mapAliases.PutAll(configuration.EventTypesMapEvents);
                legacyAliases.PutAll(configuration.EventTypesLegacy);
                databaseRefs.PutAll(configuration.DatabaseReferences);
            }

            /// <summary> Returns event type alias to type name mapping.</summary>
            /// <returns> map of alias to class name
            /// </returns>

            public EDictionary<String, String> TypeAliases
            {
                get { return typeAliases; }
            }

            /// <summary> Returns map of event alias and XML DOM configs.</summary>
            /// <returns> event alias to XML DOM config mapping
            /// </returns>

            public EDictionary<String, ConfigurationEventTypeXMLDOM> XmlDOMAliases
            {
                get { return xmlDOMAliases; }
            }

            /// <summary> Returns a map of event type alias to Map-event type properties.</summary>
            /// <returns> alias to event properties mapping for Map event types
            /// </returns>

            public EDictionary<String, EDictionary<String,String>> MapAliases
            {
                get { return mapAliases; }
            }

            /// <summary> Returns the map of event type alias to legacy event type configuration.</summary>
            /// <returns> map with alias as the key and legacy type config as the value
            /// </returns>

            public EDictionary<String, ConfigurationEventTypeLegacy> LegacyAliases
            {
                get { return legacyAliases; }
            }

            /// <summary> Returns a map of database name to database configuration.</summary>
            /// <returns> database configs
            /// </returns>

            public EDictionary<String, ConfigurationDBRef> DatabaseRefs
            {
                get { return databaseRefs; }
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
