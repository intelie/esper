/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathConstants;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Properties;

/**
 * Parser for configuration XML.
 */
class ConfigurationParser {

    /**
     * Use the configuration specified in the given input stream.
     * @param configuration is the configuration object to populate
     * @param stream	   Inputstream to be read from
     * @param resourceName The name to use in warning/error messages
     * @throws EPException is thrown when the configuration could not be parsed
     */
    protected static void doConfigure(Configuration configuration, InputStream stream, String resourceName) throws EPException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;

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
                ConfigurationParser.log.warn( "could not close input stream for: " + resourceName, ioe );
            }
        }

        doConfigure(configuration, document);
    }

    /**
     * Parse the W3C DOM document.
     * @param configuration is the configuration object to populate
     * @param doc to parse
     * @throws EPException to indicate parse errors
     */
    protected static void doConfigure(Configuration configuration, Document doc) throws EPException
    {
        Element root = doc.getDocumentElement();

        handleEventTypes(configuration, root);
        handleAutoImports(configuration, root);
        handleDatabaseRefs(configuration, root);
        handlePlugInView(configuration, root);
        handlePlugInAggregation(configuration, root);
        handlePlugInPatternObjects(configuration, root);
        handleAdapterLoaders(configuration, root);
        handleEngineSettings(configuration, root);
    }

    private static void handleEventTypes(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("event-type");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String name = nodes.item(i).getAttributes().getNamedItem("alias").getTextContent();
            Node classNode = nodes.item(i).getAttributes().getNamedItem("class");

            String optionalClassName = null;
            if (classNode != null)
            {
                optionalClassName = classNode.getTextContent();
                configuration.addEventTypeAlias(name, optionalClassName);
            }
            handleSubElement(name, optionalClassName, configuration, nodes.item(i));
        }
    }

    private static void handleSubElement(String aliasName, String optionalClassName, Configuration configuration, Node parentNode)
    {
        ElementIterator eventTypeNodeIterator = new ElementIterator(parentNode.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element eventTypeElement = eventTypeNodeIterator.next();
            String nodeName = eventTypeElement.getNodeName();
            if (nodeName.equals("xml-dom"))
            {
                handleXMLDOM(aliasName, configuration, eventTypeElement);
            }
            else if(nodeName.equals("java-util-map"))
            {
            	handleMap(aliasName, configuration, eventTypeElement);
            }
            else if (nodeName.equals("legacy-type"))
            {
                handleLegacy(aliasName, optionalClassName, configuration, eventTypeElement);
            }
        }
    }
    
    private static void handleMap(String aliasName, Configuration configuration, Element eventTypeElement)
    {
		Properties propertyTypeNames = new Properties();
		NodeList propertyList = eventTypeElement.getElementsByTagName("map-property");
		for (int i = 0; i < propertyList.getLength(); i++)
	    {
	        String name = propertyList.item(i).getAttributes().getNamedItem("name").getTextContent();
	        String clazz = propertyList.item(i).getAttributes().getNamedItem("class").getTextContent();
	        propertyTypeNames.put(name, clazz);
	    }
    	configuration.addEventTypeAlias(aliasName, propertyTypeNames);
    }

    private static void handleXMLDOM(String aliasName, Configuration configuration, Element xmldomElement)
    {
        String rootElementName = xmldomElement.getAttributes().getNamedItem("root-element-name").getTextContent();
        String rootElementNamespace = getOptionalAttribute(xmldomElement, "root-element-namespace");
        String schemaResource = getOptionalAttribute(xmldomElement, "schema-resource");
        String defaultNamespace = getOptionalAttribute(xmldomElement, "default-namespace");

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName(rootElementName);
        xmlDOMEventTypeDesc.setSchemaResource(schemaResource);
        xmlDOMEventTypeDesc.setRootElementNamespace(rootElementNamespace);
        xmlDOMEventTypeDesc.setDefaultNamespace(defaultNamespace);
        configuration.addEventTypeAlias(aliasName, xmlDOMEventTypeDesc);

        ElementIterator propertyNodeIterator = new ElementIterator(xmldomElement.getChildNodes());
        while (propertyNodeIterator.hasNext())
        {
            Element propertyElement = propertyNodeIterator.next();
            if (propertyElement.getNodeName().equals("namespace-prefix"))
            {
                String prefix = propertyElement.getAttributes().getNamedItem("prefix").getTextContent();
                String namespace = propertyElement.getAttributes().getNamedItem("namespace").getTextContent();
                xmlDOMEventTypeDesc.addNamespacePrefix(prefix, namespace);
            }
            if (propertyElement.getNodeName().equals("xpath-property"))
            {
                String propertyName = propertyElement.getAttributes().getNamedItem("property-name").getTextContent();
                String xPath = propertyElement.getAttributes().getNamedItem("xpath").getTextContent();
                String propertyType = propertyElement.getAttributes().getNamedItem("type").getTextContent();
                QName xpathConstantType;
                if (propertyType.toUpperCase().equals("NUMBER"))
                {
                    xpathConstantType = XPathConstants.NUMBER;
                }
                else if (propertyType.toUpperCase().equals("STRING"))
                {
                    xpathConstantType = XPathConstants.STRING;
                }
                else if (propertyType.toUpperCase().equals("BOOLEAN"))
                {
                    xpathConstantType = XPathConstants.BOOLEAN;
                }
                else
                {
                    throw new IllegalArgumentException("Invalid xpath property type for property '" +
                        propertyName + "' and type '" + propertyType + '\'');
                }
                xmlDOMEventTypeDesc.addXPathProperty(propertyName, xPath, xpathConstantType);
            }
        }
    }

    private static void handleLegacy(String aliasName, String className, Configuration configuration, Element xmldomElement)
    {
        // Class name is required for legacy classes
        if (className == null)
        {
            throw new ConfigurationException("Required class name not supplied for legacy type definition");
        }
        
        String accessorStyle = xmldomElement.getAttributes().getNamedItem("accessor-style").getTextContent();
        String codeGeneration = xmldomElement.getAttributes().getNamedItem("code-generation").getTextContent();
        String propertyResolution = xmldomElement.getAttributes().getNamedItem("property-resolution-style").getTextContent();

        ConfigurationEventTypeLegacy legacyDesc = new ConfigurationEventTypeLegacy();
        if (accessorStyle != null)
        {
            legacyDesc.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.valueOf(accessorStyle.toUpperCase()));
        }
        if (codeGeneration != null)
        {
            legacyDesc.setCodeGeneration(ConfigurationEventTypeLegacy.CodeGeneration.valueOf(codeGeneration.toUpperCase()));
        }
        if (propertyResolution != null)
        {
            legacyDesc.setPropertyResolutionStyle(Configuration.PropertyResolutionStyle.valueOf(propertyResolution.toUpperCase()));
        }
        configuration.addEventTypeAlias(aliasName, className, legacyDesc);

        ElementIterator propertyNodeIterator = new ElementIterator(xmldomElement.getChildNodes());
        while (propertyNodeIterator.hasNext())
        {
            Element propertyElement = propertyNodeIterator.next();
            if (propertyElement.getNodeName().equals("method-property"))
            {
                String name = propertyElement.getAttributes().getNamedItem("name").getTextContent();
                String method = propertyElement.getAttributes().getNamedItem("accessor-method").getTextContent();
                legacyDesc.addMethodProperty(name, method);
            }
            else if (propertyElement.getNodeName().equals("field-property"))
            {
                String name = propertyElement.getAttributes().getNamedItem("name").getTextContent();
                String field = propertyElement.getAttributes().getNamedItem("accessor-field").getTextContent();
                legacyDesc.addFieldProperty(name, field);
            }
            else
            {
                throw new ConfigurationException("Invalid node " + propertyElement.getNodeName()
                        + " encountered while parsing legacy type definition");
            }
        }
    }

    private static void handleAutoImports(Configuration configuration, Element parentNode)
    {
        NodeList importNodes = parentNode.getElementsByTagName("auto-import");
        for (int i = 0; i < importNodes.getLength(); i++)
        {
            String name = importNodes.item(i).getAttributes().getNamedItem("import-name").getTextContent();
            configuration.addImport(name);
        }
    }

    private static void handleDatabaseRefs(Configuration configuration, Element parentNode)
    {
        NodeList dbRefNodes = parentNode.getElementsByTagName("database-reference");
        for (int i = 0; i < dbRefNodes.getLength(); i++)
        {
            String name = dbRefNodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            ConfigurationDBRef configDBRef = new ConfigurationDBRef();
            configuration.addDatabaseReference(name, configDBRef);

            ElementIterator nodeIterator = new ElementIterator(dbRefNodes.item(i).getChildNodes());
            while (nodeIterator.hasNext())
            {
                Element subElement = nodeIterator.next();
                if (subElement.getNodeName().equals("datasource-connection"))
                {
                    String lookup = subElement.getAttributes().getNamedItem("context-lookup-name").getTextContent();
                    Properties properties = handleProperties(subElement, "env-property");
                    configDBRef.setDataSourceConnection(lookup, properties);
                }
                else if (subElement.getNodeName().equals("drivermanager-connection"))
                {
                    String className = subElement.getAttributes().getNamedItem("class-name").getTextContent();
                    String url = subElement.getAttributes().getNamedItem("url").getTextContent();
                    String userName = subElement.getAttributes().getNamedItem("user").getTextContent();
                    String password = subElement.getAttributes().getNamedItem("password").getTextContent();
                    Properties properties = handleProperties(subElement, "connection-arg");
                    configDBRef.setDriverManagerConnection(className, url, userName, password, properties);
                }
                else if (subElement.getNodeName().equals("connection-lifecycle"))
                {
                    String value = subElement.getAttributes().getNamedItem("value").getTextContent();
                    configDBRef.setConnectionLifecycleEnum(ConfigurationDBRef.ConnectionLifecycleEnum.valueOf(value.toUpperCase()));
                }
                else if (subElement.getNodeName().equals("connection-settings"))
                {
                    if (subElement.getAttributes().getNamedItem("auto-commit") != null)
                    {
                        String autoCommit = subElement.getAttributes().getNamedItem("auto-commit").getTextContent();
                        configDBRef.setConnectionAutoCommit(Boolean.parseBoolean(autoCommit));
                    }
                    if (subElement.getAttributes().getNamedItem("transaction-isolation") != null)
                    {
                        String transactionIsolation = subElement.getAttributes().getNamedItem("transaction-isolation").getTextContent();
                        configDBRef.setConnectionTransactionIsolation(Integer.parseInt(transactionIsolation));
                    }
                    if (subElement.getAttributes().getNamedItem("catalog") != null)
                    {
                        String catalog = subElement.getAttributes().getNamedItem("catalog").getTextContent();
                        configDBRef.setConnectionCatalog(catalog);
                    }
                    if (subElement.getAttributes().getNamedItem("read-only") != null)
                    {
                        String readOnly = subElement.getAttributes().getNamedItem("read-only").getTextContent();
                        configDBRef.setConnectionReadOnly(Boolean.parseBoolean(readOnly));
                    }
                }
                else if (subElement.getNodeName().equals("expiry-time-cache"))
                {
                    String maxAge = subElement.getAttributes().getNamedItem("max-age-seconds").getTextContent();
                    String purgeInterval = subElement.getAttributes().getNamedItem("purge-interval-seconds").getTextContent();
                    configDBRef.setExpiryTimeCache(Double.parseDouble(maxAge), Double.parseDouble(purgeInterval));
                }
                else if (subElement.getNodeName().equals("lru-cache"))
                {
                    String size = subElement.getAttributes().getNamedItem("size").getTextContent();
                    configDBRef.setLRUCache(Integer.parseInt(size));
                }
            }
        }
    }

    private static void handlePlugInView(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("plugin-view");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String namespace = nodes.item(i).getAttributes().getNamedItem("namespace").getTextContent();
            String name = nodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            String factoryClassName = nodes.item(i).getAttributes().getNamedItem("factory-class").getTextContent();
            configuration.addPlugInView(namespace, name, factoryClassName);
        }
    }

    private static void handlePlugInAggregation(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("plugin-aggregation-function");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String name = nodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            String functionClassName = nodes.item(i).getAttributes().getNamedItem("function-class").getTextContent();
            configuration.addPlugInAggregationFunction(name, functionClassName);
        }
    }

    private static void handlePlugInPatternObjects(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("plugin-pattern-guard");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String namespace = nodes.item(i).getAttributes().getNamedItem("namespace").getTextContent();
            String name = nodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            String factoryClassName = nodes.item(i).getAttributes().getNamedItem("factory-class").getTextContent();
            configuration.addPlugInPatternGuard(namespace, name, factoryClassName);
        }

        nodes = parentElement.getElementsByTagName("plugin-pattern-observer");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String namespace = nodes.item(i).getAttributes().getNamedItem("namespace").getTextContent();
            String name = nodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            String factoryClassName = nodes.item(i).getAttributes().getNamedItem("factory-class").getTextContent();
            configuration.addPlugInPatternObserver(namespace, name, factoryClassName);
        }
    }

    private static void handleAdapterLoaders(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("adapter-loader");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String loaderName = nodes.item(i).getAttributes().getNamedItem("name").getTextContent();
            String className = nodes.item(i).getAttributes().getNamedItem("class-name").getTextContent();
            Properties properties = new Properties();
            ElementIterator nodeIterator = new ElementIterator(nodes.item(i).getChildNodes());
            while (nodeIterator.hasNext())
            {
                Element subElement = nodeIterator.next();
                if (subElement.getNodeName().equals("init-arg"))
                {
                    String name = subElement.getAttributes().getNamedItem("name").getTextContent();
                    String value = subElement.getAttributes().getNamedItem("value").getTextContent();
                    properties.put(name, value);
                }
            }
            configuration.addAdapterLoader(loaderName, className, properties);
        }
    }

    private static void handleEngineSettings(Configuration configuration, Element parentElement)
    {
        NodeList nodes = parentElement.getElementsByTagName("engine-settings");
        for (int i = 0; i < nodes.getLength(); i++)
        {
            ElementIterator nodeIterator = new ElementIterator(nodes.item(i).getChildNodes());
            while (nodeIterator.hasNext())
            {
                Element subElement = nodeIterator.next();
                if (subElement.getNodeName().equals("defaults"))
                {
                    handleEngineSettingsDefaults(configuration, subElement);
                }
            }
        }
    }

    private static void handleEngineSettingsDefaults(Configuration configuration, Element parentElement)
    {
        ElementIterator nodeIterator = new ElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("threading"))
            {
                handleDefaultsThreading(configuration, subElement);
            }
            if (subElement.getNodeName().equals("event-meta"))
            {
                handleDefaultsEventMeta(configuration, subElement);
            }
            if (subElement.getNodeName().equals("view-resources"))
            {
                handleDefaultsViewResources(configuration, subElement);
            }
        }
    }

    private static void handleDefaultsThreading(Configuration configuration, Element parentElement)
    {
        ElementIterator nodeIterator = new ElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("listener-dispatch"))
            {
                String preserveOrderText = subElement.getAttributes().getNamedItem("preserve-order").getTextContent();
                Boolean preserveOrder = Boolean.parseBoolean(preserveOrderText);
                String timeoutMSecText = subElement.getAttributes().getNamedItem("timeout-msec").getTextContent();
                Long timeoutMSec = Long.parseLong(timeoutMSecText);
                configuration.getEngineDefaults().getThreading().setListenerDispatchPreserveOrder(preserveOrder);
                configuration.getEngineDefaults().getThreading().setListenerDispatchTimeout(timeoutMSec);
            }
            if (subElement.getNodeName().equals("insert-into-dispatch"))
            {
                String preserveOrderText = subElement.getAttributes().getNamedItem("preserve-order").getTextContent();
                Boolean preserveOrder = Boolean.parseBoolean(preserveOrderText);
                configuration.getEngineDefaults().getThreading().setInsertIntoDispatchPreserveOrder(preserveOrder);
            }
            if (subElement.getNodeName().equals("internal-timer"))
            {
                String enabledText = subElement.getAttributes().getNamedItem("enabled").getTextContent();
                Boolean enabled = Boolean.parseBoolean(enabledText);
                String msecResolutionText = subElement.getAttributes().getNamedItem("msec-resolution").getTextContent();
                Long msecResolution = Long.parseLong(msecResolutionText);
                configuration.getEngineDefaults().getThreading().setInternalTimerEnabled(enabled);
                configuration.getEngineDefaults().getThreading().setInternalTimerMsecResolution(msecResolution);
            }
        }
    }

    private static void handleDefaultsViewResources(Configuration configuration, Element parentElement)
    {
        ElementIterator nodeIterator = new ElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("share-views"))
            {
                String valueText = subElement.getAttributes().getNamedItem("value").getTextContent();
                Boolean value = Boolean.parseBoolean(valueText);
                configuration.getEngineDefaults().getViewResources().setShareViews(value);
            }
        }
    }

    private static void handleDefaultsEventMeta(Configuration configuration, Element parentElement)
    {
        ElementIterator nodeIterator = new ElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("class-property-resolution"))
            {
                String styleText = subElement.getAttributes().getNamedItem("style").getTextContent();
                Configuration.PropertyResolutionStyle value = Configuration.PropertyResolutionStyle.valueOf(styleText.toUpperCase());
                configuration.getEngineDefaults().getEventMeta().setClassPropertyResolutionStyle(value);
            }
        }
    }

    private static Properties handleProperties(Element element, String propElementName)
    {
        Properties properties = new Properties();
        ElementIterator nodeIterator = new ElementIterator(element.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals(propElementName))
            {
                String name = subElement.getAttributes().getNamedItem("name").getTextContent();
                String value = subElement.getAttributes().getNamedItem("value").getTextContent();
                properties.put(name, value);
            }
        }
        return properties;
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
            ConfigurationParser.class.getResourceAsStream( resource );
        }
        if ( stream == null ) {
            stream = ConfigurationParser.class.getClassLoader().getResourceAsStream( stripped );
        }
        if ( stream == null ) {
            throw new EPException( resource + " not found" );
        }
        return stream;
    }

    private static String getOptionalAttribute(Node node, String key)
    {
        Node valueNode = node.getAttributes().getNamedItem(key);
        if (valueNode != null)
        {
            return valueNode.getTextContent();
        }
        return null;
    }

    private static class ElementIterator implements Iterator
    {
        private int index;
        private NodeList nodeList;

        public ElementIterator(NodeList nodeList) {
            this.nodeList = nodeList;
        }

        public boolean hasNext() {
            positionNext();
            return index < nodeList.getLength();
        }

        public Element next() {
            if (index >= nodeList.getLength())
            {
                throw new NoSuchElementException();
            }
            Element result = (Element) nodeList.item(index);
            index++;
            return result;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        private void positionNext()
        {
            while (index < nodeList.getLength())
            {
                Node node = nodeList.item(index);
                if (node instanceof Element)
                {
                    break;
                }
                index++;
            }
        }
    }

    private static Log log = LogFactory.getLog(ConfigurationParser.class);
}
