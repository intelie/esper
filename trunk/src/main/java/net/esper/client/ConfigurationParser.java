package net.esper.client;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import java.util.*;
import java.io.*;

class ConfigurationParser {

    /**
     * Use the configuration specified in the given input stream.
     * @param configuration is the configuration object to populate
     * @param stream	   Inputstream to be read from
     * @param resourceName The name to use in warning/error messages
     * @throws net.esper.client.EPException
     */
    protected static void doConfigure(Configuration configuration, InputStream stream, String resourceName) throws EPException
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
                ConfigurationParser.log.warn( "could not close input stream for: " + resourceName, ioe );
            }
        }

        doConfigure(configuration, document);
    }

    /**
     * Parse the W3C DOM document.
     * @param configuration is the configuration object to populate
     * @param doc to parse
     * @throws net.esper.client.EPException
     */
    protected static void doConfigure(Configuration configuration, Document doc) throws EPException
    {
        Element root = doc.getDocumentElement();

        handleEventTypes(configuration, root);
        handleAutoImports(configuration, root);
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
                QName xpathConstantType = null;
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
                        propertyName + "' and type '" + propertyType + "'");
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

        ConfigurationEventTypeLegacy legacyDesc = new ConfigurationEventTypeLegacy();
        legacyDesc.setAccessorStyle(ConfigurationEventTypeLegacy.AccessorStyle.valueOf(accessorStyle.toUpperCase()));
        legacyDesc.setCodeGeneration(ConfigurationEventTypeLegacy.CodeGeneration.valueOf(codeGeneration.toUpperCase()));
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
