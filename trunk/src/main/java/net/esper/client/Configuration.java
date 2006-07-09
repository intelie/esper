package net.esper.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	 * The java-style class and package name imports that
	 * will be used to resolve partial class names.
	 */
	protected List<String> imports;

	/**
	 * True until the user calls addAutoImport()
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
     * Add an alias for an event type.
     * @param eventTypeAlias is the alias for the event type
     * @param javaEventClass fully-qualified class name of the event type
     */
    public void addEventTypeAlias(String eventTypeAlias, String javaEventClass)
    {
        eventClasses.put(eventTypeAlias, javaEventClass);
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
     * Returns the mapping of event type alias to event types.
     * @return event type aliases
     */
    public Map<String, String> getEventTypeAliases()
    {
        return eventClasses;
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
     * @param resource if the file name of the resource
     * @return Configuration initialized from the resource
     * @throws EPException thrown to indicate error reading configuration
     */
    public Configuration configure(String resource) throws EPException
    {
        log.debug( "configuring from resource: " + resource );
        InputStream stream = getConfigurationInputStream( resource );
        return doConfigure( stream, resource );
    }

    /**
     * Get the configuration file as an <tt>InputStream</tt>. Might be overridden
     * by subclasses to allow the configuration to be located by some arbitrary
     * mechanism.
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
			return doConfigure( url.openStream(), url.toString() );
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
			return doConfigure( new FileInputStream( configFile ), configFile.toString() );
		}
		catch (FileNotFoundException fnfe) {
			throw new EPException( "could not find file: " + configFile, fnfe );
		}
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
		return doConfigure(document);
	}

    /**
     * Use the configuration specified in the given input stream.
     *
     * @param stream	   Inputstream to be read from
     * @param resourceName The name to use in warning/error messages
     * @return A configuration configured via the stream
     * @throws EPException
     */
    protected Configuration doConfigure(InputStream stream, String resourceName) throws EPException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        Document document = null;

        try
        {
            builder = factory.newDocumentBuilder();
            document = builder.parse(stream);
        }
        catch (ParserConfigurationException ex)
        {
            throw new EPException("Could not get a DOM parser configuration: " + resourceName, ex);
        }
        catch (SAXException ex)
        {
            throw new EPException("Could not parse configuration: " + resourceName, ex);
        }
        catch (IOException ex)
        {
            throw new EPException("Could not read configuration: " + resourceName, ex);
        }
        finally {
            try {
                stream.close();
            }
            catch (IOException ioe) {
                log.warn( "could not close input stream for: " + resourceName, ioe );
            }
        }

        return doConfigure(document);
    }

    /**
     * Parse the W3C DOM document.
     * @param doc to parse
     * @return configuration
     * @throws EPException
     */
	protected Configuration doConfigure(Document doc) throws EPException
    {
        Element root = doc.getDocumentElement();
        NodeList nodes = root.getElementsByTagName("event-type");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String name = nodes.item(i).getAttributes().getNamedItem("alias").getTextContent();
            String clazz = nodes.item(i).getAttributes().getNamedItem("class").getTextContent();
            eventClasses.put(name, clazz);
        }
        
        NodeList importNodes = root.getElementsByTagName("auto-import");
        for (int i = 0; i < importNodes.getLength(); i++)
        {
            String name = importNodes.item(i).getAttributes().getNamedItem("import-name").getTextContent();
            addImport(name);
        }
        
		return this;
	}

    /**
     * Returns an input stream from an application resource in the classpath.
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
            Configuration.class.getResourceAsStream( resource );
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
        imports = new ArrayList<String>();
        addDefaultImports();
        isUsingDefaultImports = true;
    }
    
    /**
     * Use these imports until the user specifies something else
     */
    private void addDefaultImports()
    {
    	imports.add("java.lang.*");
    	imports.add("java.math.*");
    	imports.add("java.text.*");
    	imports.add("java.util.*");
    }
}

