/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;

import javax.xml.xpath.XPathExpressionException;

import com.sun.org.apache.xerces.internal.xs.XSModel;
import com.sun.org.apache.xerces.internal.xs.XSImplementation;
import com.sun.org.apache.xerces.internal.xs.XSLoader;
import com.sun.org.apache.xerces.internal.dom.DOMXSImplementationSourceImpl;

import com.espertech.esper.event.EventPropertyGetter;
import com.espertech.esper.event.TypedEventPropertyGetter;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.client.ConfigurationEventTypeXMLDOM;
import com.espertech.esper.client.EPException;
import com.espertech.esper.util.ResourceLoader;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;

import java.util.Map;
import java.util.HashMap;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * EventType for xml events that have a Schema.
 * Mapped and Indexed properties are supported.
 * All property types resolved via the declared xsd types.
 * Can access attributes.
 * Validates the property string at construction time.
 * @author pablo
 *
 */
public class SchemaXMLEventType extends BaseXMLEventType {

    // schema model
    private XSModel xsModel;

    // rootElementNamespace of the root Element
    private String rootElementNamespace;

    private Map<String, TypedEventPropertyGetter> propertyGetterCache;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM - configuration for type
     * @param eventTypeMetadata - event type metadata
     */
    public SchemaXMLEventType(EventTypeMetadata eventTypeMetadata, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM)
    {
        super(eventTypeMetadata, configurationEventTypeXMLDOM);
        propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();

        // Load schema
        String schemaResource = configurationEventTypeXMLDOM.getSchemaResource();
        try
        {
            readSchema(schemaResource);
        }
        catch (EPException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new EPException("Failed to read schema '" + schemaResource + '\'', ex);
        }

        // Use the root namespace for resolving the root element
        rootElementNamespace = configurationEventTypeXMLDOM.getRootElementNamespace();

        // Set of namespace context for XPath expressions
        XPathNamespaceContext ctx = new XPathNamespaceContext();
        if (configurationEventTypeXMLDOM.getDefaultNamespace() != null)
        {
            ctx.setDefaultNamespace(configurationEventTypeXMLDOM.getDefaultNamespace());
        }
        for (Map.Entry<String, String> entry : configurationEventTypeXMLDOM.getNamespacePrefixes().entrySet())
        {
            ctx.addPrefix(entry.getKey(), entry.getValue());
        }
        super.setNamespaceContext(ctx);

        // Finally add XPath properties as that may depend on the rootElementNamespace
        super.setExplicitProperties(configurationEventTypeXMLDOM.getXPathProperties().values());
    }

    private void readSchema(String schemaResource) throws IllegalAccessException, InstantiationException, ClassNotFoundException,
            EPException, URISyntaxException
    {
        URL url = ResourceLoader.resolveClassPathOrURLResource("schema", schemaResource);
        String uri = url.toURI().toString();

        // Uses Xerxes internal classes
        DOMImplementationRegistry registry = DOMImplementationRegistry.newInstance();
        registry.addSource(new DOMXSImplementationSourceImpl());
        XSImplementation impl =(XSImplementation) registry.getDOMImplementation("XS-Loader");
        XSLoader schemaLoader = impl.createXSLoader(null);
        xsModel = schemaLoader.loadURI(uri);

        if (xsModel == null)
        {
            throw new EPException("Failed to read schema via URL '" + schemaResource + '\'');
        }
    }

    protected Class doResolvePropertyType(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null){
            return getter.getResultClass();
        }

        getter = (TypedEventPropertyGetter) doResolvePropertyGetter(property);
        if (getter != null) {
            return getter.getResultClass();
        }
        return null;
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        TypedEventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null) {
            return getter;
        }

        try
        {
            getter = SchemaXMLPropertyParser.parse(property,getXPathFactory(),getRootElementName(),rootElementNamespace,xsModel);
            propertyGetterCache.put(property, getter);
            return getter;
        }
        catch (XPathExpressionException e) {
            throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
        }
    }

    protected String[] doListPropertyNames() {
        return null;
    }
}
