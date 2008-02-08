/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import com.espertech.esper.util.DOMElementIterator;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.event.EventAdapterException;
import com.espertech.esper.client.soda.StreamSelector;
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
        Document document = getDocument(stream, resourceName);
        doConfigure(configuration, document);
    }

    protected static Document getDocument(InputStream stream, String resourceName) throws EPException
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

        return document;
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

        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(root.getChildNodes());
        while (eventTypeNodeIterator.hasNext())
        {
            Element element = eventTypeNodeIterator.next();
            String nodeName = element.getNodeName();
            if (nodeName.equals("event-type-auto-alias"))
            {
                handleEventTypeAutoAliases(configuration, element);
            }
            else if (nodeName.equals("event-type"))
            {
                handleEventTypes(configuration, element);
            }
            else if(nodeName.equals("auto-import"))
            {
            	handleAutoImports(configuration, element);
            }
            else if(nodeName.equals("method-reference"))
            {
            	handleMethodReference(configuration, element);
            }
            else if (nodeName.equals("database-reference"))
            {
                handleDatabaseRefs(configuration, element);
            }
            else if (nodeName.equals("plugin-view"))
            {
                handlePlugInView(configuration, element);
            }
            else if (nodeName.equals("plugin-aggregation-function"))
            {
                handlePlugInAggregation(configuration, element);
            }
            else if (nodeName.equals("plugin-pattern-guard"))
            {
                handlePlugInPatternGuard(configuration, element);
            }
            else if (nodeName.equals("plugin-pattern-observer"))
            {
                handlePlugInPatternObserver(configuration, element);
            }
            else if (nodeName.equals("variable"))
            {
                handleVariable(configuration, element);
            }
            else if (nodeName.equals("adapter-loader"))
            {
                handleAdapterLoaders(configuration, element);
            }
            else if (nodeName.equals("engine-settings"))
            {
                handleEngineSettings(configuration, element);
            }
        }
    }

    private static void handleEventTypeAutoAliases(Configuration configuration, Element element)
    {
        String name = element.getAttributes().getNamedItem("package-name").getTextContent();
        configuration.addEventTypeAutoAlias(name);
    }

    private static void handleEventTypes(Configuration configuration, Element element)
    {
        String name = element.getAttributes().getNamedItem("alias").getTextContent();
        Node classNode = element.getAttributes().getNamedItem("class");

        String optionalClassName = null;
        if (classNode != null)
        {
            optionalClassName = classNode.getTextContent();
            configuration.addEventTypeAlias(name, optionalClassName);
        }

        handleEventTypeDef(name, optionalClassName, configuration, element);
    }

    private static void handleEventTypeDef(String aliasName, String optionalClassName, Configuration configuration, Node parentNode)
    {
        DOMElementIterator eventTypeNodeIterator = new DOMElementIterator(parentNode.getChildNodes());
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
        String resolvePropertiesAbsoluteStr = getOptionalAttribute(xmldomElement, "resolve-properties-absolute");

        ConfigurationEventTypeXMLDOM xmlDOMEventTypeDesc = new ConfigurationEventTypeXMLDOM();
        xmlDOMEventTypeDesc.setRootElementName(rootElementName);
        xmlDOMEventTypeDesc.setSchemaResource(schemaResource);
        xmlDOMEventTypeDesc.setRootElementNamespace(rootElementNamespace);
        xmlDOMEventTypeDesc.setDefaultNamespace(defaultNamespace);
        if (resolvePropertiesAbsoluteStr != null)
        {
            xmlDOMEventTypeDesc.setResolvePropertiesAbsolute(Boolean.parseBoolean(resolvePropertiesAbsoluteStr));
        }
        configuration.addEventTypeAlias(aliasName, xmlDOMEventTypeDesc);

        DOMElementIterator propertyNodeIterator = new DOMElementIterator(xmldomElement.getChildNodes());
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

        DOMElementIterator propertyNodeIterator = new DOMElementIterator(xmldomElement.getChildNodes());
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

    private static void handleAutoImports(Configuration configuration, Element element)
    {
        String name = element.getAttributes().getNamedItem("import-name").getTextContent();
        configuration.addImport(name);
    }

    private static void handleDatabaseRefs(Configuration configuration, Element element)
    {
        String name = element.getAttributes().getNamedItem("name").getTextContent();
        ConfigurationDBRef configDBRef = new ConfigurationDBRef();
        configuration.addDatabaseReference(name, configDBRef);

        DOMElementIterator nodeIterator = new DOMElementIterator(element.getChildNodes());
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
            else if (subElement.getNodeName().equals("column-change-case"))
            {
                String value = subElement.getAttributes().getNamedItem("value").getTextContent();
                ConfigurationDBRef.ColumnChangeCaseEnum parsed = ConfigurationDBRef.ColumnChangeCaseEnum.valueOf(value.toUpperCase());
                configDBRef.setColumnChangeCase(parsed);
            }
            else if (subElement.getNodeName().equals("metadata-origin"))
            {
                String value = subElement.getAttributes().getNamedItem("value").getTextContent();
                ConfigurationDBRef.MetadataOriginEnum parsed = ConfigurationDBRef.MetadataOriginEnum.valueOf(value.toUpperCase());
                configDBRef.setMetadataOrigin(parsed);
            }
            else if (subElement.getNodeName().equals("sql-types-mapping"))
            {
                String sqlType = subElement.getAttributes().getNamedItem("sql-type").getTextContent();
                String javaType = subElement.getAttributes().getNamedItem("java-type").getTextContent();
                Integer sqlTypeInt;
                try
                {
                    sqlTypeInt = Integer.parseInt(sqlType);
                }
                catch (NumberFormatException ex)
                {
                    throw new ConfigurationException("Error converting sql type '" + sqlType + "' to integer java.sql.Types constant");
                }
                configDBRef.addSqlTypesBinding(sqlTypeInt, javaType);
            }
            else if (subElement.getNodeName().equals("expiry-time-cache"))
            {
                String maxAge = subElement.getAttributes().getNamedItem("max-age-seconds").getTextContent();
                String purgeInterval = subElement.getAttributes().getNamedItem("purge-interval-seconds").getTextContent();
                ConfigurationCacheReferenceType refTypeEnum = ConfigurationCacheReferenceType.getDefault();
                if (subElement.getAttributes().getNamedItem("ref-type") != null)
                {
                    String refType = subElement.getAttributes().getNamedItem("ref-type").getTextContent();
                    refTypeEnum = ConfigurationCacheReferenceType.valueOf(refType.toUpperCase());
                }
                configDBRef.setExpiryTimeCache(Double.parseDouble(maxAge), Double.parseDouble(purgeInterval), refTypeEnum);
            }
            else if (subElement.getNodeName().equals("lru-cache"))
            {
                String size = subElement.getAttributes().getNamedItem("size").getTextContent();
                configDBRef.setLRUCache(Integer.parseInt(size));
            }
        }
    }

    private static void handleMethodReference(Configuration configuration, Element element)
    {
        String className = element.getAttributes().getNamedItem("class-name").getTextContent();
        ConfigurationMethodRef configMethodRef = new ConfigurationMethodRef();
        configuration.addMethodRef(className, configMethodRef);

        DOMElementIterator nodeIterator = new DOMElementIterator(element.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("expiry-time-cache"))
            {
                String maxAge = subElement.getAttributes().getNamedItem("max-age-seconds").getTextContent();
                String purgeInterval = subElement.getAttributes().getNamedItem("purge-interval-seconds").getTextContent();
                ConfigurationCacheReferenceType refTypeEnum = ConfigurationCacheReferenceType.getDefault();
                if (subElement.getAttributes().getNamedItem("ref-type") != null)
                {
                    String refType = subElement.getAttributes().getNamedItem("ref-type").getTextContent();
                    refTypeEnum = ConfigurationCacheReferenceType.valueOf(refType.toUpperCase());
                }
                configMethodRef.setExpiryTimeCache(Double.parseDouble(maxAge), Double.parseDouble(purgeInterval), refTypeEnum);
            }
            else if (subElement.getNodeName().equals("lru-cache"))
            {
                String size = subElement.getAttributes().getNamedItem("size").getTextContent();
                configMethodRef.setLRUCache(Integer.parseInt(size));
            }
        }
    }

    private static void handlePlugInView(Configuration configuration, Element element)
    {
        String namespace = element.getAttributes().getNamedItem("namespace").getTextContent();
        String name = element.getAttributes().getNamedItem("name").getTextContent();
        String factoryClassName = element.getAttributes().getNamedItem("factory-class").getTextContent();
        configuration.addPlugInView(namespace, name, factoryClassName);
    }

    private static void handlePlugInAggregation(Configuration configuration, Element element)
    {
        String name = element.getAttributes().getNamedItem("name").getTextContent();
        String functionClassName = element.getAttributes().getNamedItem("function-class").getTextContent();
        configuration.addPlugInAggregationFunction(name, functionClassName);
    }

    private static void handlePlugInPatternGuard(Configuration configuration, Element element)
    {
        String namespace = element.getAttributes().getNamedItem("namespace").getTextContent();
        String name = element.getAttributes().getNamedItem("name").getTextContent();
        String factoryClassName = element.getAttributes().getNamedItem("factory-class").getTextContent();
        configuration.addPlugInPatternGuard(namespace, name, factoryClassName);
    }

    private static void handlePlugInPatternObserver(Configuration configuration, Element element)
    {
        String namespace = element.getAttributes().getNamedItem("namespace").getTextContent();
        String name = element.getAttributes().getNamedItem("name").getTextContent();
        String factoryClassName = element.getAttributes().getNamedItem("factory-class").getTextContent();
        configuration.addPlugInPatternObserver(namespace, name, factoryClassName);
    }

    private static void handleVariable(Configuration configuration, Element element)
    {
        String variableName = element.getAttributes().getNamedItem("name").getTextContent();
        String type = element.getAttributes().getNamedItem("type").getTextContent();

        Class variableType;
        try
        {
            variableType = JavaClassHelper.getClassForSimpleName(type);
        }
        catch (EventAdapterException ex)
        {
            throw new ConfigurationException("Invalid variable type for variable '" + variableName + "': " + ex.getMessage());
        }

        Node initValueNode = element.getAttributes().getNamedItem("initialization-value");
        String initValue = null;
        if (initValueNode != null)
        {
            initValue = initValueNode.getTextContent();
        }

        configuration.addVariable(variableName, variableType, initValue);
    }

    private static void handleAdapterLoaders(Configuration configuration, Element element)
    {
        String loaderName = element.getAttributes().getNamedItem("name").getTextContent();
        String className = element.getAttributes().getNamedItem("class-name").getTextContent();
        Properties properties = new Properties();
        DOMElementIterator nodeIterator = new DOMElementIterator(element.getChildNodes());
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

    private static void handleEngineSettings(Configuration configuration, Element element)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(element.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("defaults"))
            {
                handleEngineSettingsDefaults(configuration, subElement);
            }
        }
    }

    private static void handleEngineSettingsDefaults(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
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
            if (subElement.getNodeName().equals("logging"))
            {
                handleDefaultsLogging(configuration, subElement);
            }
            if (subElement.getNodeName().equals("variables"))
            {
                handleDefaultsVariables(configuration, subElement);
            }
            if (subElement.getNodeName().equals("stream-selection"))
            {
                handleDefaultsStreamSelection(configuration, subElement);
            }
        }
    }

    private static void handleDefaultsThreading(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("listener-dispatch"))
            {
                String preserveOrderText = subElement.getAttributes().getNamedItem("preserve-order").getTextContent();
                Boolean preserveOrder = Boolean.parseBoolean(preserveOrderText);
                configuration.getEngineDefaults().getThreading().setListenerDispatchPreserveOrder(preserveOrder);

                if (subElement.getAttributes().getNamedItem("timeout-msec") != null)
                {
                    String timeoutMSecText = subElement.getAttributes().getNamedItem("timeout-msec").getTextContent();
                    Long timeoutMSec = Long.parseLong(timeoutMSecText);
                    configuration.getEngineDefaults().getThreading().setListenerDispatchTimeout(timeoutMSec);
                }

                if (subElement.getAttributes().getNamedItem("locking") != null)
                {
                    String value = subElement.getAttributes().getNamedItem("locking").getTextContent();
                    configuration.getEngineDefaults().getThreading().setListenerDispatchLocking(
                            ConfigurationEngineDefaults.Threading.Locking.valueOf(value.toUpperCase()));
                }
            }
            if (subElement.getNodeName().equals("insert-into-dispatch"))
            {
                String preserveOrderText = subElement.getAttributes().getNamedItem("preserve-order").getTextContent();
                Boolean preserveOrder = Boolean.parseBoolean(preserveOrderText);
                configuration.getEngineDefaults().getThreading().setInsertIntoDispatchPreserveOrder(preserveOrder);

                if (subElement.getAttributes().getNamedItem("timeout-msec") != null)
                {
                    String timeoutMSecText = subElement.getAttributes().getNamedItem("timeout-msec").getTextContent();
                    Long timeoutMSec = Long.parseLong(timeoutMSecText);
                    configuration.getEngineDefaults().getThreading().setInsertIntoDispatchTimeout(timeoutMSec);
                }

                if (subElement.getAttributes().getNamedItem("locking") != null)
                {
                    String value = subElement.getAttributes().getNamedItem("locking").getTextContent();
                    configuration.getEngineDefaults().getThreading().setInsertIntoDispatchLocking(
                            ConfigurationEngineDefaults.Threading.Locking.valueOf(value.toUpperCase()));
                }
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
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("share-views"))
            {
                String valueText = subElement.getAttributes().getNamedItem("enabled").getTextContent();
                Boolean value = Boolean.parseBoolean(valueText);
                configuration.getEngineDefaults().getViewResources().setShareViews(value);
            }
        }
    }

    private static void handleDefaultsLogging(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("execution-path"))
            {
                String valueText = subElement.getAttributes().getNamedItem("enabled").getTextContent();
                Boolean value = Boolean.parseBoolean(valueText);
                configuration.getEngineDefaults().getLogging().setEnableExecutionDebug(value);
            }
        }
    }

    private static void handleDefaultsVariables(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("msec-version-release"))
            {
                String valueText = subElement.getAttributes().getNamedItem("value").getTextContent();
                Long value = Long.parseLong(valueText);
                configuration.getEngineDefaults().getVariables().setMsecVersionRelease(value);
            }
        }
    }

    private static void handleDefaultsStreamSelection(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
        while (nodeIterator.hasNext())
        {
            Element subElement = nodeIterator.next();
            if (subElement.getNodeName().equals("stream-selector"))
            {
                String valueText = subElement.getAttributes().getNamedItem("value").getTextContent();
                if (valueText == null)
                {
                    throw new ConfigurationException("No value attribute supplied for stream-selector element");
                }
                StreamSelector defaultSelector;
                if (valueText.toUpperCase().trim().equals("ISTREAM"))
                {
                    defaultSelector = StreamSelector.ISTREAM_ONLY;
                }
                else if (valueText.toUpperCase().trim().equals("RSTREAM"))
                {
                    defaultSelector = StreamSelector.RSTREAM_ONLY;
                }
                else if (valueText.toUpperCase().trim().equals("IRSTREAM"))
                {
                    defaultSelector = StreamSelector.RSTREAM_ISTREAM_BOTH;
                }
                else
                {
                    throw new ConfigurationException("Value attribute for stream-selector element invalid, " +
                            "expected on of the following keywords: istream, irstream, rstream");
                }
                configuration.getEngineDefaults().getStreamSelection().setDefaultStreamSelector(defaultSelector);
            }
        }
    }

    private static void handleDefaultsEventMeta(Configuration configuration, Element parentElement)
    {
        DOMElementIterator nodeIterator = new DOMElementIterator(parentElement.getChildNodes());
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
        DOMElementIterator nodeIterator = new DOMElementIterator(element.getChildNodes());
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

    private static Log log = LogFactory.getLog(ConfigurationParser.class);
}
