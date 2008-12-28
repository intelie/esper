/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;


import com.espertech.esper.client.*;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.EventAdapterService;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Optimistic try to resolve the property string into an appropiate xPath,
 * and use it as getter.
 * Mapped and Indexed properties supported.
 * Because no type information is given, all property are resolved to String.
 * No namespace support.
 * Cannot access to xml attributes, only elements content.
 *
 * If an xsd is present, then use {@link com.espertech.esper.event.xml.SchemaXMLEventType SchemaXMLEventType }
 *
 * @author pablo
 *
 */
public class SimpleXMLEventType extends BaseXMLEventType {

    private final Map<String, EventPropertyGetter> propertyGetterCache;
    private String defaultNamespacePrefix;
    private final boolean isResolvePropertiesAbsolute;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM configures the event type
     * @param eventTypeMetadata event type metadata
     */
    public SimpleXMLEventType(EventTypeMetadata eventTypeMetadata, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM, EventAdapterService eventAdapterService)
    {
        super(eventTypeMetadata, configurationEventTypeXMLDOM, eventAdapterService);
        isResolvePropertiesAbsolute = configurationEventTypeXMLDOM.isResolvePropertiesAbsolute();

        // Set of namespace context for XPath expressions
        XPathNamespaceContext xPathNamespaceContext = new XPathNamespaceContext();
        for (Map.Entry<String, String> entry : configurationEventTypeXMLDOM.getNamespacePrefixes().entrySet())
        {
            xPathNamespaceContext.addPrefix(entry.getKey(), entry.getValue());
        }
        if (configurationEventTypeXMLDOM.getDefaultNamespace() != null)
        {
            String defaultNamespace = configurationEventTypeXMLDOM.getDefaultNamespace();
            xPathNamespaceContext.setDefaultNamespace(defaultNamespace);

            // determine a default namespace prefix to use to construct XPath expressions from pure property names
            defaultNamespacePrefix = null;
            for (Map.Entry<String, String> entry : configurationEventTypeXMLDOM.getNamespacePrefixes().entrySet())
            {
                if (entry.getValue().equals(defaultNamespace))
                {
                    defaultNamespacePrefix = entry.getKey();
                    break;
                }
            }
        }
        super.setNamespaceContext(xPathNamespaceContext);
        super.initialize(configurationEventTypeXMLDOM.getXPathProperties().values(), Collections.EMPTY_LIST);

        propertyGetterCache = new HashMap<String, EventPropertyGetter>();
    }

    protected Class doResolvePropertyType(String property) {
        return String.class;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        EventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null) {
            return getter;
        }

        XPathExpression xPathExpression = null;
        String xPathExpr;
        try
        {
            xPathExpr = SimpleXMLPropertyParser.parse(property,getRootElementName(), defaultNamespacePrefix, isResolvePropertiesAbsolute);
            XPath xpath = getXPathFactory().newXPath();
            xpath.setNamespaceContext(namespaceContext);
            xPathExpression = xpath.compile(xPathExpr);
        }
        catch (XPathExpressionException e)
        {
            throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
        }

        // TODO - fragment factory
        getter = new XPathPropertyGetter(property, xPathExpr, xPathExpression, XPathConstants.STRING, null, null);
        propertyGetterCache.put(property, getter);
        return getter;
    }

    protected FragmentEventType doResolveFragmentType(String property)
    {
        return null;  // TODO
    }
}
