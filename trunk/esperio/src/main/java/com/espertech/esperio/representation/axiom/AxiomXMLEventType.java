/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio.representation.axiom;

import com.espertech.esper.client.*;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.EventTypeSPI;
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
    // TODO
    private EventTypeMetadata metadata;
    private String defaultNamespacePrefix;
    private ConfigurationEventTypeAxiom config;
    private AxiomXPathNamespaceContext namespaceContext;
    // private Map<String, TypedEventPropertyGetter> propertyGetterCache;
    private String[] propertyNames;
    private EventPropertyDescriptor[] propertyDescriptors;
    private Map<String, EventPropertyDescriptor> propertyDescriptorMap;

    public Class getPropertyType(String propertyExpression)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EventPropertyGetter getGetter(String propertyExpression)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Class getUnderlyingType()
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Ctor.
     * @param configurationEventTypeAxiom is the configuration for XML access
     */
    public AxiomXMLEventType(EventTypeMetadata metadata, ConfigurationEventTypeAxiom configurationEventTypeAxiom)
    {
        this.metadata = metadata;
        this.config = configurationEventTypeAxiom;
        // this.propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();

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

    public String getName()
    {
        return metadata.getPublicName();
    }

    public EventPropertyDescriptor[] getPropertyDescriptors()
    {
        return propertyDescriptors;
    }

    public FragmentEventType getFragmentType(String property)
    {
        return null; // not providing the capability for fragments
    }

    public EventPropertyDescriptor getPropertyDescriptor(String propertyName)
    {
        return propertyDescriptorMap.get(propertyName);
    }    
}
