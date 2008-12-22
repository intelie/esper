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
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.FragmentEventType;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.TypedEventPropertyGetter;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.property.Property;
import com.espertech.esper.event.property.PropertyParser;
import com.espertech.esper.event.property.SimpleProperty;

import javax.xml.xpath.XPathExpressionException;
import java.util.HashMap;
import java.util.Map;

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
    private SchemaModel schemaModel;

    // rootElementNamespace of the root Element
    private String rootElementNamespace;

    private Map<String, TypedEventPropertyGetter> propertyGetterCache;

    public boolean isPropertyExpressionXPath;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM - configuration for type
     * @param eventTypeMetadata - event type metadata
     * @param schemaModel - the schema representation
     */
    public SchemaXMLEventType(EventTypeMetadata eventTypeMetadata, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM, SchemaModel schemaModel, EventAdapterService eventAdapterService)
    {
        super(eventTypeMetadata, configurationEventTypeXMLDOM, eventAdapterService);
        this.propertyGetterCache = new HashMap<String, TypedEventPropertyGetter>();
        this.schemaModel = schemaModel;
        this.isPropertyExpressionXPath = configurationEventTypeXMLDOM.isPropertyExprXPath();

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

    protected FragmentEventType doResolveFragmentType(String property)
    {
        return null;  // TODO
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

        if (!isPropertyExpressionXPath)
        {
            Property prop = PropertyParser.parse(property, this.getEventAdapterService(), false);
            if (prop instanceof SimpleProperty)
            {
                // there is no such property since it wasn't in simplePropertyGetters
                return null;
            }

            SchemaItem schemaItem = prop.getPropertyTypeSchema(schemaModel, this.getEventAdapterService());
            EventPropertyGetter getterManufactured = prop.getGetterDOM(schemaModel, this.getEventAdapterService());
            propertyGetterCache.put(property, getterManufactured);
            return getter;
        }
        else
        {
            try
            {
                getter = SchemaXMLPropertyParser.parse(property,getXPathFactory(),getRootElementName(),rootElementNamespace, schemaModel);
                propertyGetterCache.put(property, getter);
                return getter;
            }
            catch (XPathExpressionException e) {
                throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
            }
        }
    }
}
