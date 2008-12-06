/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.EPException;
import com.espertech.esper.client.EventPropertyDescriptor;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.EventTypeSPI;
import com.espertech.esper.event.TypedEventPropertyGetter;
import com.espertech.esper.event.xml.SimpleXMLPropertyParser;
import org.apache.axiom.om.OMNode;
import org.apache.axiom.om.xpath.AXIOMXPath;
import org.jaxen.JaxenException;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Apache Axiom event type provides event metadata for Axiom OMDocument events.
 * <p>
 * Optimistic try to resolve the property string into an appropiate xPath, and
 * use it as getter. Mapped and Indexed properties supported. Because no type
 * information is given, all property are resolved to String. No namespace
 * support. Cannot access to xml attributes, only elements content.
 * <p>
 * See {@link AxiomEventRepresentation} for more details.
 */
public class AxiomXMLEventType implements EventTypeSPI
{
    private EventTypeMetadata metadata;
    private String defaultNamespacePrefix;
    private ConfigurationEventTypeAxiom config;
    private AxiomXPathNamespaceContext namespaceContext;
    private Map<String, TypedEventPropertyGetter> propertyGetterCache;
    private String[] propertyNames;
    private EventPropertyDescriptor[] propertyDescriptors;

    /**
     * Ctor.
     * @param configurationEventTypeAxiom is the configuration for XML access
     */
    public AxiomXMLEventType(EventTypeMetadata metadata, ConfigurationEventTypeAxiom configurationEventTypeAxiom)
    {
        this.metadata = metadata;
        this.config = configurationEventTypeAxiom;
        this.propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();

        // Set up a namespace context for XPath expressions
        namespaceContext = new AxiomXPathNamespaceContext();
        for (Map.Entry<String, String> entry : configurationEventTypeAxiom.getNamespacePrefixes().entrySet())
        {
            namespaceContext.addPrefix(entry.getKey(), entry.getValue());
        }

        // add namespaces
        if (configurationEventTypeAxiom.getDefaultNamespace() != null)
        {
            String defaultNamespace = configurationEventTypeAxiom.getDefaultNamespace();
            namespaceContext.setDefaultNamespace(defaultNamespace);

            // determine a default namespace prefix to use to construct XPath
            // expressions from pure property names
            defaultNamespacePrefix = null;
            for (Map.Entry<String, String> entry : configurationEventTypeAxiom.getNamespacePrefixes().entrySet())
            {
                if (entry.getValue().equals(defaultNamespace))
                {
                    defaultNamespacePrefix = entry.getKey();
                    break;
                }
            }
        }

        // determine XPath properties that are predefined
        String xpathExpression = null;
        try {
            for (ConfigurationEventTypeAxiom.XPathPropertyDesc property : config.getXPathProperties().values())
            {
                TypedEventPropertyGetter getter = resolvePropertyGetter(property.getName(), property.getXpath(), property.getType(), property.getOptionalCastToType());
                propertyGetterCache.put(property.getName(), getter);
            }
        }
        catch (XPathExpressionException ex)
        {
            throw new EPException("XPath expression could not be compiled for expression '" + xpathExpression + '\'', ex);
        }

        propertyNames = new String[propertyGetterCache.size()];
        propertyDescriptors = new EventPropertyDescriptor[propertyGetterCache.size()];
        int count = 0;
        for (Map.Entry<String, TypedEventPropertyGetter> entry : propertyGetterCache.entrySet())
        {
            propertyNames[count] = entry.getKey();
            propertyDescriptors[count] = new EventPropertyDescriptor(entry.getKey(), entry.getValue().getResultClass(), false, false, false, false, false);
            count++;
        }
    }

    public Class getPropertyType(String property) {
		TypedEventPropertyGetter getter = propertyGetterCache.get(property);
		if (getter != null)
			return getter.getResultClass();
		return String.class;    // all other types are assumed to exist and be of type String
	}

	public Class getUnderlyingType() {
		return OMNode.class;
	}

	public EventPropertyGetter getGetter(String property) {
		EventPropertyGetter getter = propertyGetterCache.get(property);
		if (getter != null)
			return getter;
        try
        {
            return resolveDynamicProperty(property);
        }
        catch (XPathExpressionException e)
        {
            return null;
        }
    }

	public String[] getPropertyNames() {
		return propertyNames;
	}

	public boolean isProperty(String property) {
		return (getGetter(property) != null);
	}

    public EventType[] getSuperTypes() {
        return null;
    }

    public Iterator<EventType> getDeepSuperTypes() {
        return null;
    }

    /**
     * Returns the configuration for the alias.
     * @return configuration details underlying the type
     */
    public ConfigurationEventTypeAxiom getConfig()
    {
        return config;
    }

    public EventTypeMetadata getMetadata()
    {
        return metadata;
    }    

    private TypedEventPropertyGetter resolveDynamicProperty(String property) throws XPathExpressionException
    {
        // not defined, come up with an XPath
        String xPathExpr = SimpleXMLPropertyParser.parse(property, config.getRootElementName(), defaultNamespacePrefix, config.isResolvePropertiesAbsolute());
        return resolvePropertyGetter(property, xPathExpr, XPathConstants.STRING, null);
    }

    private TypedEventPropertyGetter resolvePropertyGetter(String propertyName, String xPathExpr, QName type, Class optionalCastToType) throws XPathExpressionException
    {
        AXIOMXPath axXPath;
        try
        {
            axXPath = new AXIOMXPath(xPathExpr);
        }
        catch (JaxenException e)
        {
            throw new EPException("Error constructing XPath expression from property name '" + propertyName + '\'', e);
        }

        axXPath.setNamespaceContext(namespaceContext);
        return new AxiomXPathPropertyGetter(propertyName, axXPath, type, optionalCastToType);
    }

    public String getName()
    {
        return metadata.getPublicName();
    }

    public EventPropertyDescriptor[] getPropertyDescriptors()
    {
        return propertyDescriptors;
    }

    public EventType getFragmentType(String property)
    {
        return null;   // TODO
    }
}
