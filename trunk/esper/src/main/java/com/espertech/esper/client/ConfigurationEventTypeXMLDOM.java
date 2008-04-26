/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.util.JavaClassHelper;
import com.espertech.esper.event.EventAdapterException;

import javax.xml.namespace.QName;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

/**
 * Configuration object for enabling the engine to process events represented as XML DOM document nodes.
 * <p>
 * Use this class to configure the engine for processing of XML DOM objects that represent events
 * and contain all the data for event properties used by statements.
 * <p>
 * Minimally required is the root element name which allows the engine to map the document
 * to the event type that has been named in an EPL or pattern statement.
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
public class ConfigurationEventTypeXMLDOM implements MetaDefItem, Serializable
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

    private boolean resolvePropertiesAbsolute;

    private String xPathFunctionResolver;
    private String xPathVariableResolver;

    /**
     * Ctor.
     */
    public ConfigurationEventTypeXMLDOM()
    {
        xPathProperties = new HashMap<String, XPathPropertyDesc>();
        namespacePrefixes = new HashMap<String, String>();
        resolvePropertiesAbsolute = true;
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
     * Adds an event property for which the engine uses the supplied XPath expression against
     * a DOM document node to resolve a property value.
     * @param name of the event property
     * @param xpath is an arbitrary xpath expression
     * @param type is a constant obtained from javax.xml.xpath.XPathConstants. Typical values are
     * XPathConstants.NUMBER, STRING and BOOLEAN.
     * @param castToType is the type name of the type that the return value of the xpath expression is casted to
     */
    public void addXPathProperty(String name, String xpath, QName type, String castToType)
    {
        Class castToTypeClass = null;

        if (castToType != null)
        {
            try
            {
                castToTypeClass = JavaClassHelper.getClassForSimpleName(castToType);
            }
            catch (EventAdapterException ex)
            {
                throw new ConfigurationException("Invalid cast-to type for xpath expression named '" + name + "': " + ex.getMessage());
            }
        }

        XPathPropertyDesc desc = new XPathPropertyDesc(name, xpath, type, castToTypeClass);
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
     * Indicates whether properties are compiled into absolute or deep XPath expressions (see setter method for more detail).
     * @return true for absolute properties, false for deep properties
     */
    public boolean isResolvePropertiesAbsolute()
    {
        return resolvePropertiesAbsolute;
    }

    /**
     * When set to true (the default), indicates that when properties are compiled to XPath expressions that the
     * compilation should generate an absolute XPath expression such as "/getQuote/request" for the
     * simple request property, or "/getQuote/request/symbol" for a "request.symbol" nested property,
     * wherein the root element node is "getQuote".
     * <p>
     * When set to false, indicates that when properties are compiled to XPath expressions that the
     * compilation should generate a deep XPath expression such as "//symbol" for the
     * simple symbol property, or "//request/symbol" for a "request.symbol" nested property.
     * @param resolvePropertiesAbsolute true for absolute XPath for properties (default), false for deep XPath
     */
    public void setResolvePropertiesAbsolute(boolean resolvePropertiesAbsolute)
    {
        this.resolvePropertiesAbsolute = resolvePropertiesAbsolute;
    }

    /**
     * Returns the class name of the XPath function resolver to be assigned to the XPath factory instance
     * upon type initialization.
     * @return class name of xpath function resolver, or null if none set
     */
    public String getXPathFunctionResolver()
    {
        return xPathFunctionResolver;
    }

    /**
     * Sets the class name of the XPath function resolver to be assigned to the XPath factory instance
     * upon type initialization.
     * @param xPathFunctionResolver class name of xpath function resolver, or null if none set
     */
    public void setXPathFunctionResolver(String xPathFunctionResolver)
    {
        this.xPathFunctionResolver = xPathFunctionResolver;
    }

    /**
     * Returns the class name of the XPath variable resolver to be assigned to the XPath factory instance
     * upon type initialization.
     * @return class name of xpath function resolver, or null if none set
     */
    public String getXPathVariableResolver()
    {
        return xPathVariableResolver;
    }

    /**
     * Sets the class name of the XPath variable resolver to be assigned to the XPath factory instance
     * upon type initialization.
     * @param xPathVariableResolver class name of xpath function resolver, or null if none set
     */
    public void setXPathVariableResolver(String xPathVariableResolver)
    {
        this.xPathVariableResolver = xPathVariableResolver;
    }

    /**
     * Descriptor class for event properties that are resolved via XPath-expression.
     */
    public static class XPathPropertyDesc implements Serializable
    {
        private String name;
        private String xpath;
        private QName type;
        private Class optionalCastToType;

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
         * Ctor.
         * @param name is the event property name
         * @param xpath is an arbitrary XPath expression
         * @param type is a javax.xml.xpath.XPathConstants constant
         * @param optionalCastToType if non-null then the return value of the xpath expression is cast to this value
         */
        public XPathPropertyDesc(String name, String xpath, QName type, Class optionalCastToType)
        {
            this.name = name;
            this.xpath = xpath;
            this.type = type;
            this.optionalCastToType = optionalCastToType;
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

        /**
         * Returns the class that the return value of the xpath expression is cast to, or null if no casting.
         * @return class to cast result of xpath expression to
         */
        public Class getOptionalCastToType()
        {
            return optionalCastToType;
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

    public int hashCode()
    {
        return rootElementName.hashCode();
    }
}
