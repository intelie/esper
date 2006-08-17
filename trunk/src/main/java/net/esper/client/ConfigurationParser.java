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
            if (classNode != null)
            {
                String clazz = classNode.getTextContent();
                configuration.addEventTypeAlias(name, clazz);
            }
            else
            {
                handleNonJavaType(name, configuration, nodes.item(i));
            }
        }
    }

    private static void handleNonJavaType(String aliasName, Configuration configuration, Node parentNode)
    {
        ElementIterator eventTypeNodeIterator = new ElementIterator(parentNode.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element eventTypeElement = eventTypeNodeIterator.next();
            if (eventTypeElement.getNodeName().equals("xml-dom"))
            {
                handleXMLDOM(aliasName, configuration, eventTypeElement);
            }
            else if(eventTypeElement.getNodeName().equals("map")) 
            {
            	handleMap(aliasName, configuration, eventTypeElement);
            }
        }
    }
    
    private static void handleMap(String aliasName, Configuration configuration, Element eventTypeElement)
    {
		Map<String, String> propertyTypeNames = new LinkedHashMap<String, String>();
		NodeList propertyList = eventTypeElement.getElementsByTagName("property");
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

    private static String getChildTextByName(Element node, String elementName) {
        Element child = ConfigurationParser.getChildElementByName(node, elementName);
        return ConfigurationParser.getSimpleElementText(child);
    }

    private static String getSimpleElementText(Element node) {
        StringBuffer sb = new StringBuffer();
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (child instanceof Text)
                sb.append(child.getNodeValue());
        }
        return sb.toString();
    }

    private static Element getChildElementByName(Element element, String name) {

        ConfigurationParser.ElementIterator iterator = new ConfigurationParser.ElementIterator(element.getChildNodes());
        for (;iterator.hasNext();)
        {
            Element childElement = (Element) iterator.next();
            if (childElement.getNodeName().equals(name))
            {
                return childElement;
            }
        }

        String childElementNodes = ConfigurationParser.getChildElementNodeNames(element);
        String childAllNodes = ConfigurationParser.getChildNodesToString(element);
        throw new IllegalStateException("Error finding DOM element, element " + element + " does not contain element named '" + name +
                "', child element nodes are: " + childElementNodes +
                "', all child nodes are: " + childAllNodes
                );
    }

    private static String getChildElementNodeNames(Element parentElement)
    {
        StringBuffer qnames = new StringBuffer();
        String delimiter = "";

        ConfigurationParser.ElementIterator iterator = new ConfigurationParser.ElementIterator(parentElement.getChildNodes());
        for (;iterator.hasNext();)
        {
            Element childElement = (Element) iterator.next();
            qnames.append(delimiter);
            qnames.append(childElement.getNodeName());
            delimiter = ",";
        }

        return qnames.toString();
    }

    private static String getChildNodesToString(Element parentElement)
    {
        StringBuffer qnames = new StringBuffer();
        String delimiter = "";

        NodeList nodes = parentElement.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++)
        {
            Node node = nodes.item(i);
            qnames.append(delimiter);
            qnames.append("node (");
            qnames.append(i);
            qnames.append(") : \n");
            qnames.append(node.toString());
            delimiter = ",";
        }

        return qnames.toString();
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
