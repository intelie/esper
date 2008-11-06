/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;


import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.client.EPException;
import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.TypedEventPropertyGetter;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

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

    private final Map<String, TypedEventPropertyGetter> propertyGetterCache;
    private String defaultNamespacePrefix;
    private final boolean isResolvePropertiesAbsolute;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM configures the event type
     */
    public SimpleXMLEventType(ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(configurationEventTypeXMLDOM);
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
        super.setExplicitProperties(configurationEventTypeXMLDOM.getXPathProperties().values());

        propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();
    }

    protected Class doResolvePropertyType(String property) {
        return String.class;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null) {
            return getter;
        }

        XPathExpression xPathExpression = null;
        try
        {
            String xPathExpr = SimpleXMLPropertyParser.parse(property,getRootElementName(), defaultNamespacePrefix, isResolvePropertiesAbsolute);
            XPath xpath = getXPathFactory().newXPath();
            xpath.setNamespaceContext(namespaceContext);
            xPathExpression = xpath.compile(xPathExpr);
        }
        catch (XPathExpressionException e)
        {
            throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
        }

        getter = new XPathPropertyGetter(property, xPathExpression, XPathConstants.STRING, null);
        propertyGetterCache.put(property, getter);
        return getter;
    }
}
