/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;

/**
 * An instance of <tt>Configuration</tt> allows the application
 * to specify properties to be used when
 * creating a <tt>EPServiceProvider</tt>. Usually an application will create
 * a single <tt>Configuration</tt>, then get one or more instances of
 * {@link EPServiceProvider} via {@link EPServiceProviderManager}.
 * The <tt>Configuration</tt> is meant
 * only as an initialization-time object. <tt>EPServiceProvider</tt>s are
 * immutable and do not retain any association back to the
 * <tt>Configuration</tt>.
 * <br>
 * The format of an Esper XML configuration file is defined in
 * <tt>esper-configuration-1.0.xsd</tt>.
 */
public class Configuration {

	private static Log log = LogFactory.getLog( Configuration.class );

    /**
     * Default name of the configuration file.
     */
    protected static final String ESPER_DEFAULT_CONFIG = "esper.cfg.xml";

    /**
     * Map of event name and fully-qualified Java class name.
     */
	protected Map<String, String> eventClasses;

    /**
     * Map of event type alias and XML DOM configuration.
     */
	protected Map<String, ConfigurationEventTypeXMLDOM> eventTypesXMLDOM;

    /**
     * Map of event type alias and Legacy-type event configuration.
     */
	protected Map<String, ConfigurationEventTypeLegacy> eventTypesLegacy;

	/**
	 * The type aliases for events that result when maps are sent
	 * into the engine.
	 */
	protected Map<String, Properties> mapAliases;
	
	/**
	 * The java-style class and package name imports that
	 * will be used to resolve partial class names.
	 */
	protected List<String> imports;

	/**
	 * True until the user calls addAutoImport().
	 */
	private boolean isUsingDefaultImports = true;

    /**
     * Constructs an empty configuration. The auto import values
     * are set by default to java.lang, java.math, java.text and
     * java.util.
     */
    public Configuration()
    {
        reset();
    }

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClassName fully-qualified class name of the event type
     */
    public void addEventTypeAlias(String eventTypeAlias, String javaEventClassName)
    {
        eventClasses.put(eventTypeAlias, javaEventClassName);
    }

    /**
     * Add an alias for an event type represented by Java-bean plain-old Java object events.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClass is the Java event class for which to create the alias
     */
    public void addEventTypeAlias(String eventTypeAlias, Class javaEventClass)
    {
        addEventTypeAlias(eventTypeAlias, javaEventClass.getName());
    }

    /**
     * Add an alias for an event type that represents java.util.Map events.
     * @param eventTypeAlias is the alias for the event type
     * @param typeMap maps the name of each property in the Map event to the type (as a string) of its value in the Map object
     */
    public void addEventTypeAlias(String eventTypeAlias, Properties typeMap)
    {
    	mapAliases.put(eventTypeAlias, typeMap);
    }
    
    /**
     * Add an alias for an event type that represents org.w3c.dom.Node events.
     * @param eventTypeAlias is the alias for the event type
     * @param xmlDOMEventTypeDesc descriptor containing property and mapping information for XML-DOM events
     */
    public void addEventTypeAlias(String eventTypeAlias, ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc)
    {
        eventTypesXMLDOM.put(eventTypeAlias, xmlDOMEventTypeDesc);
    }

    /**
     * Add an alias for an event type that represents legacy Java type (non-JavaBean style) events.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClass fully-qualified class name of the event type
     * @param legacyEventTypeDesc descriptor containing property and mapping information for Legacy Java type events
     */
    public void addEventTypeAlias(String eventTypeAlias, String javaEventClass, ConfigurationEventTypeLegacy legacyEventTypeDesc)
    {
        eventClasses.put(eventTypeAlias, javaEventClass);
        eventTypesLegacy.put(eventTypeAlias, legacyEventTypeDesc);
    }

    /**
     * Add an import (a class or package). Adding will suppress the use of the default imports.
     * @param autoImport - the import to add
     */
    public void addImport(String autoImport)
    {
		if(isUsingDefaultImports)
		{
			isUsingDefaultImports = false;
			imports.clear();
		}
    	imports.add(autoImport);
    }

    /**
     * Returns the mapping of event type alias to Java class name.
     * @return event type aliases for Java class names
     */
    public Map<String, String> getEventTypeAliases()
    {
        return eventClasses;
    }

    /**
     * Returns a map keyed by event type alias name, and values being the definition for the
     * event type of the property names and types that make up the event.
     * @return map of event type alias name and definition of event properties
     */
    public Map<String, Properties> getEventTypesMapEvents()
    {
    	return mapAliases;
    }
    
    /**
     * Returns the mapping of event type alias to XML DOM event type information.
     * @return event type aliases mapping to XML DOM configs
     */
    public Map<String, ConfigurationEventTypeXMLDOM> getEventTypesXMLDOM()
    {
        return eventTypesXMLDOM;
    }

    /**
     * Returns the mapping of event type alias to legacy java event type information.
     * @return event type aliases mapping to legacy java class configs
     */
    public Map<String, ConfigurationEventTypeLegacy> getEventTypesLegacy()
    {
        return eventTypesLegacy;
    }

    /**
     * Returns the class and package imports.
     * @return imported names
     */
	public List<String> getImports()
	{
		return imports;
	}

	/**
	 * Use the configuration specified in an application
	 * resource named <tt>esper.cfg.xml</tt>.
     * @return Configuration initialized from the resource
     * @throws EPException thrown to indicate error reading configuration
     */
	public Configuration configure() throws EPException
    {
		configure("/" + ESPER_DEFAULT_CONFIG);
		return this;
	}

    /**
     * Use the configuration specified in the given application
     * resource. The format of the resource is defined in
     * <tt>esper-configuration-1.0.xsd</tt>.
     * <p/>
     * The resource is found via <tt>getConfigurationInputStream(resource)</tt>.
     * That method can be overridden to implement an arbitrary lookup strategy.
     * <p/>
     * See <tt>getResourceAsStream</tt> for information on how the resource name is resolved.
     * @param resource if the file name of the resource
     * @return Configuration initialized from the resource
     * @throws EPException thrown to indicate error reading configuration
     */
    public Configuration configure(String resource) throws EPException
    {
        log.debug( "configuring from resource: " + resource );
        InputStream stream = getConfigurationInputStream( resource );
        ConfigurationParser.doConfigure(this, stream, resource );
        return this;
    }

    /**
     * Get the configuration file as an <tt>InputStream</tt>. Might be overridden
     * by subclasses to allow the configuration to be located by some arbitrary
     * mechanism.
     * <p>
     * See <tt>getResourceAsStream</tt> for information on how the resource name is resolved.
     * @param resource is the resource name
     * @return input stream for resource
     * @throws EPException thrown to indicate error reading configuration
     */
    protected InputStream getConfigurationInputStream(String resource) throws EPException
    {
        log.debug( "Configuration resource: " + resource );
        return getResourceAsStream(resource);
    }


	/**
	 * Use the configuration specified by the given URL.
	 * The format of the document obtained from the URL is defined in
	 * <tt>esper-configuration-1.0.xsd</tt>.
	 *
	 * @param url URL from which you wish to load the configuration
	 * @return A configuration configured via the file
	 * @throws EPException
	 */
	public Configuration configure(URL url) throws EPException
    {
		log.debug( "configuring from url: " + url.toString() );
		try {
            ConfigurationParser.doConfigure(this, url.openStream(), url.toString());
            return this;
		}
		catch (IOException ioe) {
			throw new EPException("could not configure from URL: " + url, ioe );
		}
	}

	/**
	 * Use the configuration specified in the given application
	 * file. The format of the file is defined in
	 * <tt>esper-configuration-1.0.xsd</tt>.
	 *
	 * @param configFile <tt>File</tt> from which you wish to load the configuration
	 * @return A configuration configured via the file
	 * @throws EPException
	 */
	public Configuration configure(File configFile) throws EPException
    {
		log.debug( "configuring from file: " + configFile.getName() );
		try {
            ConfigurationParser.doConfigure(this, new FileInputStream(configFile), configFile.toString());
		}
		catch (FileNotFoundException fnfe) {
			throw new EPException( "could not find file: " + configFile, fnfe );
		}
        return this;
    }


	/**
	 * Use the mappings and properties specified in the given XML document.
	 * The format of the file is defined in
	 * <tt>esper-configuration-1.0.xsd</tt>.
	 *
	 * @param document an XML document from which you wish to load the configuration
	 * @return A configuration configured via the <tt>Document</tt>
	 * @throws EPException if there is problem in accessing the document.
	 */
	public Configuration configure(Document document) throws EPException
    {
		log.debug( "configuring from XML document" );
		ConfigurationParser.doConfigure(this, document);
        return this;
    }

    /**
     * Returns an input stream from an application resource in the classpath.
     * <p>
     * The method first removes the '/' character from the resource name if
     * the first character is '/'.
     * <p>
     * The lookup order is as follows:
     * <p>
     * If a thread context class loader exists, use <tt>Thread.currentThread().getResourceAsStream</tt>
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, use the <tt>Configuration.class.getResourceAsStream</tt>.
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, use the <tt>Configuration.class.getClassLoader().getResourceAsStream</tt>.
     * to obtain an InputStream.
     * <p>
     * If no input stream was returned, throw an Exception.
     *
     * @param resource to get input stream for
     * @return input stream for resource
     */
    protected static InputStream getResourceAsStream(String resource)
    {
        String stripped = resource.startsWith("/") ?
                resource.substring(1) : resource;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        if (classLoader!=null) {
            stream = classLoader.getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            stream = Configuration.class.getResourceAsStream( resource );
        }
        if ( stream == null ) {
            stream = Configuration.class.getClassLoader().getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            throw new EPException( resource + " not found" );
        }
        return stream;
    }

    /**
     * Reset to an empty configuration.
     */
    protected void reset()
    {
        eventClasses = new HashMap<String, String>();
        mapAliases = new HashMap<String, Properties>();
        eventTypesXMLDOM = new HashMap<String, ConfigurationEventTypeXMLDOM>();
        eventTypesLegacy = new HashMap<String, ConfigurationEventTypeLegacy>();
        imports = new ArrayList<String>();
        addDefaultImports();
        isUsingDefaultImports = true;
    }

    /**
     * Use these imports until the user specifies something else.
     */
    private void addDefaultImports()
    {
    	imports.add("java.lang.*");
    	imports.add("java.math.*");
    	imports.add("java.text.*");
    	imports.add("java.util.*");
    }
}

