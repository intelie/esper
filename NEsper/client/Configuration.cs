// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.IO;
using System.Net;
using System.Collections.Generic;
using System.Collections.Specialized;
using System.Xml;

using net.esper.compat;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.client
{
    /// <summary> 
    /// An instance of <tt>Configuration</tt> allows the application
    /// to specify properties to be used when
    /// creating a <tt>EPServiceProvider</tt>. Usually an application will create
    /// a single <tt>Configuration</tt>, then get one or more instances of
    /// <seealso cref="EPServiceProvider" /> via <seealso cref="EPServiceProviderManager" />.
    /// The <tt>Configuration</tt> is meant
    /// only as an initialization-time object. <tt>EPServiceProvider</tt>s are
    /// immutable and do not retain any association back to the
    /// <tt>Configuration</tt>.
    /// <para>
    /// The format of an Esper XML configuration file is defined in
    /// <tt>esper-configuration-1.0.xsd</tt>.
    /// </para>
    /// </summary>
    public class Configuration : ConfigurationOperations
    {
        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);

        /// <summary> Default name of the configuration file.</summary>
        internal const String ESPER_DEFAULT_CONFIG = "esper.cfg.xml";

        /// <summary> Map of event name and fully-qualified Java class name.</summary>
        private EDictionary<String, String> eventClasses;

        /// <summary> Map of event type alias and XML DOM configuration.</summary>
        private EDictionary<String, ConfigurationEventTypeXMLDOM> eventTypesXMLDOM;

        /// <summary> Map of event type alias and Legacy-type event configuration.</summary>
        private EDictionary<String, ConfigurationEventTypeLegacy> eventTypesLegacy;

        /// <summary> The type aliases for events that result when maps are sent
        /// into the engine.
        /// </summary>

        private EDictionary<String, Properties> mapAliases;

        /// <summary> The java-style class and package name imports that
        /// will be used to resolve partial class names.
        /// </summary>

        private IList<String> imports;

        /// <summary> The java-style class and package name imports that
        /// will be used to resolve partial class names.
        /// </summary>

        private EDictionary<String, ConfigurationDBRef> databaseReferences;

        /// <summary> True until the user calls addAutoImport().</summary>
        private bool isUsingDefaultImports = true;

		/// <summary>Optional classname to use for constructing services context.</summary>
		protected String epServicesContextFactoryClassName;

		/// <summary>List of configured plug-in views.</summary>
		protected List<ConfigurationPlugInView> plugInViews;

		/// <summary>List of configured plug-in pattern objects.</summary>
		protected List<ConfigurationPlugInPatternObject> plugInPatternObjects;

		/// <summary>List of configured plug-in aggregation functions.</summary>
		protected IList<ConfigurationPlugInAggregationFunction> plugInAggregationFunctions;

		/// <summary>List of adapter loaders.</summary>
		protected IList<ConfigurationAdapterLoader> adapterLoaders;
		
        /// <summary> Constructs an empty configuration. The auto import values
        /// are set to System, System.Collections and System.Text
        /// </summary>

        public Configuration()
        {
            Reset();
        }
		
	    /// <summary>
		/// Gets or sets the service context factory type name
		/// </summary>

	    public String EPServicesContextFactoryClassName
	    {
	        get { return epServicesContextFactoryClassName; }
			set { epServicesContextFactoryClassName = value ; }
	    }

	    public void AddPlugInAggregationFunction(String functionName, String aggregationClassName)
	    {
	        ConfigurationPlugInAggregationFunction entry = new ConfigurationPlugInAggregationFunction();
	        entry.FunctionClassName = aggregationClassName;
	        entry.Name = functionName;
	        plugInAggregationFunctions.Add(entry);
	    }
	    
        /// <summary> Add a database reference with a given database name.</summary>
        /// <param name="name">is the database name
        /// </param>
        /// <param name="configurationDBRef">descriptor containing database connection and access policy information
        /// </param>
        public virtual void AddDatabaseReference(String name, ConfigurationDBRef configurationDBRef)
        {
            databaseReferences[name] = configurationDBRef;
        }

        /// <summary> Add an alias for an event type represented by Java-bean plain-old Java object events.</summary>
        /// <param name="eventTypeAlias">is the alias for the event type
        /// </param>
        /// <param name="eventTypeName">fully-qualified class name of the event type
        /// </param>

        public virtual void AddEventTypeAlias(String eventTypeAlias, String eventTypeName)
        {
            eventClasses[eventTypeAlias] = eventTypeName;
        }

        /// <summary> Add an alias for an event type represented by Java-bean plain-old Java object events.</summary>
        /// <param name="eventTypeAlias">is the alias for the event type
        /// </param>
        /// <param name="eventType">is the Java event class for which to create the alias
        /// </param>
        public virtual void AddEventTypeAlias(String eventTypeAlias, Type eventType)
        {
            AddEventTypeAlias(eventTypeAlias, eventType.FullName);
        }

        /// <summary> Add an alias for an event type that represents java.util.Map events.</summary>
        /// <param name="eventTypeAlias">is the alias for the event type
        /// </param>
        /// <param name="typeMap">maps the name of each property in the Map event to the type (as a string) of its value in the Map object
        /// </param>
        public virtual void AddEventTypeAlias(String eventTypeAlias, Properties typeMap)
        {
            mapAliases[eventTypeAlias] = typeMap;
        }
        
        /// <summary>
        /// Add an alias for an event type that represents java.util.Map events, taking a Map of
        /// event property and class name as a parameter.
     	/// <p>
     	/// This method is provided for convenience and is same in function to method
     	/// taking a Properties object that contain fully qualified class name as values.
     	/// </p>
        /// </summary>
        /// <param name="eventTypeAlias">the alias for the event type</param>
        /// <param name="typeMap">maps the name of each property in the Map event to the type of its value in the Map object</param>
	    public virtual void AddEventTypeAlias(String eventTypeAlias, IDictionary<String, Type> typeMap)
	    {
	        Properties properties = new Properties();
	        foreach( KeyValuePair<String,Type> entry in typeMap )
	        {
	        	properties[entry.Key] = entry.Value.FullName;
	        }
	        AddEventTypeAlias(eventTypeAlias, properties);
	    }

        /// <summary> Add an alias for an event type that represents org.w3c.dom.Node events.</summary>
        /// <param name="eventTypeAlias">is the alias for the event type
        /// </param>
        /// <param name="xmlDOMEventTypeDesc">descriptor containing property and mapping information for XML-DOM events
        /// </param>
        public virtual void AddEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
        {
            eventTypesXMLDOM[eventTypeAlias] = xmlDOMEventTypeDesc;
        }

        /// <summary> Add an alias for an event type that represents legacy Java type (non-JavaBean style) events.</summary>
        /// <param name="eventTypeAlias">is the alias for the event type
        /// </param>
        /// <param name="eventType">fully-qualified class name of the event type
        /// </param>
        /// <param name="legacyEventTypeDesc">descriptor containing property and mapping information for Legacy Java type events
        /// </param>
        public virtual void AddEventTypeAlias(String eventTypeAlias, String eventType, ConfigurationEventTypeLegacy legacyEventTypeDesc)
        {
            eventClasses[eventTypeAlias] = eventType;
            eventTypesLegacy[eventTypeAlias] = legacyEventTypeDesc;
        }

        /// <summary>
        /// Add a namespace. Adding will suppress the use of the default namespaces.
        /// </summary>
        /// <param name="autoImport">the import to add
        /// </param>
        public virtual void AddImport(String importName)
        {
            if (isUsingDefaultImports)
            {
                isUsingDefaultImports = false;
                imports.Clear();
            }
            imports.Add(importName);
        }

        /// <summary> Returns the mapping of event type alias to Java class name.</summary>
        /// <returns> event type aliases for Java class names
        /// </returns>
        public EDictionary<String, String> EventTypeAliases
        {
            get { return eventClasses; }
        }

        /// <summary> Returns a map keyed by event type alias name, and values being the definition for the
        /// event type of the property names and types that make up the event.
        /// </summary>
        /// <returns> map of event type alias name and definition of event properties
        /// </returns>

        public EDictionary<String, Properties> EventTypesMapEvents
        {
            get { return mapAliases; }
        }

        /// <summary> Returns the mapping of event type alias to XML DOM event type information.</summary>
        /// <returns> event type aliases mapping to XML DOM configs
        /// </returns>
        public EDictionary<String, ConfigurationEventTypeXMLDOM> EventTypesXMLDOM
        {
            get { return eventTypesXMLDOM; }
        }


        /// <summary> Returns the mapping of event type alias to legacy java event type information.</summary>
        /// <returns> event type aliases mapping to legacy java class configs
        /// </returns>
        public EDictionary<String, ConfigurationEventTypeLegacy> EventTypesLegacy
        {
            get { return eventTypesLegacy; }
        }

        /// <summary> Returns the class and package imports.</summary>
        /// <returns> imported names
        /// </returns>
        public IList<String> Imports
        {
            get { return imports; }
        }

        /// <summary> Returns a map of string database names to database configuration options.</summary>
        /// <returns> map of database configurations
        /// </returns>
        public EDictionary<String, ConfigurationDBRef> DatabaseReferences
        {
            get { return databaseReferences; }
        }
		
		/// <summary>Returns a list of configured plug-in views.</summary>
		/// <returns>list of plug-in view configs</returns>

	    public IList<ConfigurationPlugInView> PlugInViews
	    {
	        get { return plugInViews; }
	    }

		/// <summary>Returns a list of configured adapter loaders.</summary>
		/// <returns>adapter loaders</returns>

	    public IList<ConfigurationAdapterLoader> AdapterLoaders
	    {
	        get { return adapterLoaders; }
		}

		/// <summary>Returns a list of configured plug-in aggregation functions.</summary>
		/// <returns>list of configured aggregations</returns>

	    public IList<ConfigurationPlugInAggregationFunction> PlugInAggregationFunctions
	    {
	        get { return plugInAggregationFunctions; }
	    }

		/// <summary>Returns a list of configured plug-ins for pattern observers and guards.</summary>
		/// <returns>list of pattern plug-ins</returns>

	    public IList<ConfigurationPlugInPatternObject> PlugInPatternObjects
	    {
	        get { return plugInPatternObjects; }
	    }

		/// <summary>Add an input/output adapter loader.</summary>
		/// <param name="loaderName">is the name of the loader</param>
		/// <param name="typeName">is the fully-qualified classname of the loader class</param>
		/// <param name="configuration">is loader cofiguration entries</param>

	    public void AddAdapterLoader(String loaderName, String typeName, Properties configuration)
	    {
	        ConfigurationAdapterLoader adapterLoader = new ConfigurationAdapterLoader();
	        adapterLoader.LoaderName = loaderName;
	        adapterLoader.TypeName = typeName;
	        adapterLoader.ConfigProperties = configuration ;
	        adapterLoaders.Add(adapterLoader);
	    }

		/// <summary>Add a view for plug-in.</summary>
		/// <param name="_namespace">is the namespace the view should be available under</param>
		/// <param name="name">is the name of the view</param>
		/// <param name="viewFactoryClass">is the view factory class to use</param>

	    public void AddPlugInView(String _namespace, String name, String viewFactoryClass)
	    {
	        ConfigurationPlugInView configurationPlugInView = new ConfigurationPlugInView();
	        configurationPlugInView.Namespace = _namespace ;
	        configurationPlugInView.Name = name;
	        configurationPlugInView.FactoryClassName = viewFactoryClass;
	        plugInViews.Add(configurationPlugInView);
	    }

		/// <summary>Add a pattern event observer for plug-in.</summary>
		/// <param name="_namespace">is the namespace the observer should be available under</param>
		/// <param name="name">is the name of the observer</param>
		/// <param name="observerFactoryClass">is the observer factory class to use</param>

	    public void AddPlugInPatternObserver(String _namespace, String name, String observerFactoryClass)
	    {
	        ConfigurationPlugInPatternObject entry = new ConfigurationPlugInPatternObject();
	        entry.Namespace = _namespace;
	        entry.Name = name;
	        entry.FactoryClassName = observerFactoryClass;
	        entry.PatternObjectType = ConfigurationPlugInPatternObject.PatternObjectTypeEnum.OBSERVER;
	        plugInPatternObjects.Add(entry);
	    }

		/// <summary>Add a pattern guard for plug-in.</summary>
		/// <param name="_namespace">is the namespace the guard should be available under</param>
		/// <param name="name">is the name of the guard</param>
		/// <param name="guardFactoryClass">is the guard factory class to use</param>

	    public void AddPlugInPatternGuard(String _namespace, String name, String guardFactoryClass)
	    {
	        ConfigurationPlugInPatternObject entry = new ConfigurationPlugInPatternObject();
	        entry.Namespace = _namespace;
	        entry.Name = name;
	        entry.FactoryClassName = guardFactoryClass;
	        entry.PatternObjectType = ConfigurationPlugInPatternObject.PatternObjectTypeEnum.GUARD;
	        plugInPatternObjects.Add(entry);
	    }

        /// <summary> Use the configuration specified in an application
        /// resource named <tt>esper.cfg.xml</tt>.
        /// </summary>
        /// <returns> Configuration initialized from the resource
        /// </returns>
        /// <throws>  EPException thrown to indicate error reading configuration </throws>
        public virtual Configuration Configure()
        {
            Configure("/" + ESPER_DEFAULT_CONFIG);
            return this;
        }

        /// <summary> Use the configuration specified in the given application
        /// resource. The format of the resource is defined in
        /// <tt>esper-configuration-1.0.xsd</tt>.
        /// <p/>
        /// The resource is found via <tt>getConfigurationInputStream(resource)</tt>.
        /// That method can be overridden to implement an arbitrary lookup strategy.
        /// <p/>
        /// See <tt>getResourceAsStream</tt> for information on how the resource name is resolved.
        /// </summary>
        /// <param name="resource">if the file name of the resource
        /// </param>
        /// <returns> Configuration initialized from the resource
        /// </returns>
        /// <throws>  EPException thrown to indicate error reading configuration </throws>
        public virtual Configuration Configure(String resource)
        {
            log.Debug("configuring from resource: " + resource);
            Stream stream = GetConfigurationInputStream(resource);
            ConfigurationParser.DoConfigure(this, stream, resource);
            return this;
        }

        /// <summary> Get the configuration file as an <tt>InputStream</tt>. Might be overridden
        /// by subclasses to allow the configuration to be located by some arbitrary
        /// mechanism.
        ///
        /// See GetResourceAsStream for information on how the resource name is resolved.
        /// <seealso>GetResourceAsStream</seealso>
        /// </summary>
        /// <param name="resource">is the resource name
        /// </param>
        /// <returns> input stream for resource
        /// </returns>
        /// <throws>  EPException thrown to indicate error reading configuration </throws>
        internal static Stream GetConfigurationInputStream(String resource)
        {
            log.Debug("Configuration resource: " + resource);
            return GetResourceAsStream(resource);
        }


        /// <summary> Use the configuration specified by the given URL.
        /// The format of the document obtained from the URL is defined in
        /// <tt>esper-configuration-1.0.xsd</tt>.
        /// 
        /// </summary>
        /// <param name="url">URL from which you wish to load the configuration
        /// </param>
        /// <returns> A configuration configured via the file
        /// </returns>
        /// <throws>  EPException </throws>
        public virtual Configuration Configure(Uri url)
        {
            log.Debug("configuring from url: " + url.ToString());
            try
            {
                ConfigurationParser.DoConfigure(this, WebRequest.Create(url).GetResponse().GetResponseStream(), url.ToString());
                return this;
            }
            catch (IOException ioe)
            {
                throw new EPException("could not configure from URL: " + url, ioe);
            }
        }

        /// <summary> Use the configuration specified in the given application
        /// file. The format of the file is defined in
        /// <tt>esper-configuration-1.0.xsd</tt>.
        /// 
        /// </summary>
        /// <param name="configFile"><tt>File</tt> from which you wish to load the configuration
        /// </param>
        /// <returns> A configuration configured via the file
        /// </returns>
        /// <throws>  EPException </throws>
        public virtual Configuration Configure(FileInfo configFile)
        {
            log.Debug("configuring from file: " + configFile.Name);
            try
            {
                ConfigurationParser.DoConfigure(this, new FileStream(configFile.FullName, FileMode.Open, FileAccess.Read), configFile.ToString());
            }
            catch (FileNotFoundException fnfe)
            {
                throw new EPException("could not find file: " + configFile, fnfe);
            }
            return this;
        }

        /// <summary> Use the mappings and properties specified in the given XML document.
        /// The format of the file is defined in
        /// <tt>esper-configuration-1.0.xsd</tt>.
        /// 
        /// </summary>
        /// <param name="document">an XML document from which you wish to load the configuration
        /// </param>
        /// <returns> A configuration configured via the <tt>Document</tt>
        /// </returns>
        /// <throws>  EPException if there is problem in accessing the document. </throws>
        public virtual Configuration Configure(XmlDocument document)
        {
            log.Debug("configuring from XML document");
            ConfigurationParser.DoConfigure(this, document);
            return this;
        }

        /// <summary> Returns an input stream from an application resource in the classpath.
        /// 
        /// The method first removes the '/' character from the resource name if
        /// the first character is '/'.
        /// 
        /// The lookup order is as follows:
        /// 
        /// If a thread context class loader exists, use <tt>Thread.currentThread().getResourceAsStream</tt>
        /// to obtain an InputStream.
        /// 
        /// If no input stream was returned, use the <tt>Configuration.class.getResourceAsStream</tt>.
        /// to obtain an InputStream.
        /// 
        /// If no input stream was returned, use the <tt>Configuration.class.getClassLoader().getResourceAsStream</tt>.
        /// to obtain an InputStream.
        /// 
        /// If no input stream was returned, throw an Exception.
        /// 
        /// </summary>
        /// <param name="resource">to get input stream for
        /// </param>
        /// <returns> input stream for resource
        /// </returns>
        internal static Stream GetResourceAsStream(String resource)
        {
            String stripped = resource.StartsWith("/") ? resource.Substring(1) : resource;

            Stream stream = ResourceManager.GetResourceAsStream( resource ) ;
            if ( stream == null )
            {
            	stream = ResourceManager.GetResourceAsStream( stripped ) ;
            }
            if (stream == null)
            {
                throw new EPException(resource + " not found");
            }
            return stream;
        }

        /// <summary> Reset to an empty configuration.</summary>
        internal virtual void Reset()
        {
            eventClasses = new EHashDictionary<String, String>();
            mapAliases = new EHashDictionary<String, Properties>();
            eventTypesXMLDOM = new EHashDictionary<String, ConfigurationEventTypeXMLDOM>();
            eventTypesLegacy = new EHashDictionary<String, ConfigurationEventTypeLegacy>();
            databaseReferences = new EHashDictionary<String, ConfigurationDBRef>();
            imports = new List<String>();
            AddDefaultImports();
            isUsingDefaultImports = true;
	        plugInViews = new List<ConfigurationPlugInView>();
	        adapterLoaders = new List<ConfigurationAdapterLoader>();
	        plugInAggregationFunctions = new List<ConfigurationPlugInAggregationFunction>();
	        plugInPatternObjects = new List<ConfigurationPlugInPatternObject>();
        }

        /// <summary>
        /// Use these imports until the user specifies something else.
        /// </summary>
        
        private void AddDefaultImports()
        {
            imports.Add("System");
            imports.Add("System.Collections");
            imports.Add("System.Text");
        }
    }
}
