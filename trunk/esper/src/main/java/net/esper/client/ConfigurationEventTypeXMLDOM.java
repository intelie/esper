/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.HashMap;

/**
 * Configuration object for enabling the engine to process events represented as XML DOM document nodes.
 * <p>
 * Use this class to configure the engine for processing of XML DOM objects that represent events
 * and contain all the data for event properties used by statements.
 * <p>
 * Minimally required is the root element name which allows the engine to map the document
 * to the event type that has been named in an EQL or pattern statement.
 * <p>
 * Event properties that are results of XPath expressions can be made known to the engine via this class.
 * For XPath expressions that must refer to namespace prefixes those prefixes and their
 * namespace name must be supplied to the engine. A default namespace can be supplied as well.
 * <p>
 * By supplying a schema resource the engine can interrogate the schema, allowing the engine to
 * verify event properties and return event properties in the type defined by the schema.
 * When a schema resource is supplied, the optional root element namespace defines the namespace in case the
 * root element name occurs in multiple namespaces.
 * <p>
 */
public class ConfigurationEventTypeXMLDOM
{
    private String rootElementName;

    // Root element namespace.
    // Used to find root element in schema. Useful and required in the case where the root element exists in
    // multiple namespaces.
    private String rootElementNamespace;

    // Default name space.
    // For XPath expression evaluation.
    private String defaultNamespace;
    
    private String schemaResource;
    private Map<String, XPathPropertyDesc> xPathProperties;
    private Map<String, String> namespacePrefixes;

    /**
     * Ctor.
     */
    @SuppressWarnings({"CollectionWithoutInitialCapacity"})
    public ConfigurationEventTypeXMLDOM()
    {
        xPathProperties = new HashMap<String, XPathPropertyDesc>();
        namespacePrefixes = new HashMap<String, String>();
    }

    /**
     * Returns the root element name.
     * @return root element name
     */
    public String getRootElementName()
    {
        return rootElementName;
    }

    /**
     * Sets the root element name.
     * @param rootElementName is the name of the root element
     */
    public void setRootElementName(String rootElementName)
    {
        this.rootElementName = rootElementName;
    }

    /**
     * Returns the root element namespace.
     * @return root element namespace
     */
    public String getRootElementNamespace()
    {
        return rootElementNamespace;
    }

    /**
     * Sets the root element namespace.
     * @param rootElementNamespace is the namespace for the root element
     */
    public void setRootElementNamespace(String rootElementNamespace)
    {
        this.rootElementNamespace = rootElementNamespace;
    }

    /**
     * Returns the default namespace.
     * @return default namespace
     */
    public String getDefaultNamespace()
    {
        return defaultNamespace;
    }

    /**
     * Sets the default namespace.
     * @param defaultNamespace is the default namespace
     */
    public void setDefaultNamespace(String defaultNamespace)
    {
        this.defaultNamespace = defaultNamespace;
    }

    /**
     * Returns the schema resource.
     * @return schema resource
     */
    public String getSchemaResource()
    {
        return schemaResource;
    }

    /**
     * Sets the schema resource.
     * @param schemaResource is the schema resource
     */
    public void setSchemaResource(String schemaResource)
    {
        this.schemaResource = schemaResource;
    }

    /**
     * Returns a map of property name and descriptor for XPath-expression properties.
     * @return XPath property information
     */
    public Map<String, XPathPropertyDesc> getXPathProperties()
    {
        return xPathProperties;
    }

    /**
     * Adds an event property for which the engine uses the supplied XPath expression against
     * a DOM document node to resolve a property value.
     * @param name of the event property
     * @param xpath is an arbitrary xpath expression
     * @param type is a constant obtained from javax.xml.xpath.XPathConstants. Typical values are
     * XPathConstants.NUMBER, STRING and BOOLEAN.
     */
    public void addXPathProperty(String name, String xpath, QName type)
    {
        XPathPropertyDesc desc = new XPathPropertyDesc(name, xpath, type);
        xPathProperties.put(name, desc);
    }

    /**
     * Returns the namespace prefixes in a map of prefix as key and namespace name as value.
     * @return namespace prefixes
     */
    public Map<String, String> getNamespacePrefixes()
    {
        return namespacePrefixes;
    }

    /**
     * Add a prefix and namespace name for use in XPath expressions refering to that prefix.
     * @param prefix is the prefix of the namespace
     * @param namespace is the namespace name
     */
    public void addNamespacePrefix(String prefix, String namespace)
    {
        namespacePrefixes.put(prefix, namespace);
    }

    /**
     * Descriptor class for event properties that are resolved via XPath-expression.
     */
    public static class XPathPropertyDesc
    {
        private String name;
        private String xpath;
        private QName type;

        /**
         * Ctor.
         * @param name is the event property name
         * @param xpath is an arbitrary XPath expression
         * @param type is a javax.xml.xpath.XPathConstants constant
         */
        public XPathPropertyDesc(String name, String xpath, QName type)
        {
            this.name = name;
            this.xpath = xpath;
            this.type = type;
        }

        /**
         * Returns the event property name.
         * @return event property name
         */
        public String getName()
        {
            return name;
        }

        /**
         * Returns the XPath expression.
         * @return XPath expression
         */
        public String getXpath()
        {
            return xpath;
        }

        /**
         * Returns the javax.xml.xpath.XPathConstants constant representing the event property type.
         * @return type infomation
         */
        public QName getType()
        {
            return type;
        }
    }

    public boolean equals(Object otherObj)
    {
        if (!(otherObj instanceof ConfigurationEventTypeXMLDOM))
        {
            return false;
        }

        ConfigurationEventTypeXMLDOM other = (ConfigurationEventTypeXMLDOM) otherObj;
        if (!(other.rootElementName.equals(rootElementName)))
        {
            return false;
        }

        if (((other.rootElementNamespace == null) && (rootElementNamespace != null)) ||
            ((other.rootElementNamespace != null) && (rootElementNamespace == null)))
        {
            return false;
        }
        if ((other.rootElementNamespace != null) && (rootElementNamespace != null))
        {
            return rootElementNamespace.equals(other.rootElementNamespace);
        }
        return true;
    }
}
