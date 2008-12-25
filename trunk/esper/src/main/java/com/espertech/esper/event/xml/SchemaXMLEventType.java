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
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.EventTypeMetadata;
import com.espertech.esper.event.ExplicitPropertyDescriptor;
import com.espertech.esper.event.property.Property;
import com.espertech.esper.event.property.PropertyParser;
import com.espertech.esper.event.xml.getter.DOMConvertingGetter;
import com.espertech.esper.event.xml.getter.DOMPropertyGetter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class SchemaXMLEventType extends BaseXMLEventType
{
    private final SchemaModel schemaModel;
    private final SchemaElementComplex schemaModelRoot;
    private final String rootElementNamespace;
    private final Map<String, EventPropertyGetter> propertyGetterCache;
    private final boolean isPropertyExpressionXPath;

    /**
     * Ctor.
     * @param configurationEventTypeXMLDOM - configuration for type
     * @param eventTypeMetadata - event type metadata
     * @param schemaModel - the schema representation
     */
    public SchemaXMLEventType(EventTypeMetadata eventTypeMetadata, ConfigurationEventTypeXMLDOM configurationEventTypeXMLDOM, SchemaModel schemaModel, EventAdapterService eventAdapterService)
    {
        super(eventTypeMetadata, configurationEventTypeXMLDOM, eventAdapterService);
        
        this.propertyGetterCache = new HashMap<String, EventPropertyGetter>();
        this.schemaModel = schemaModel;
        this.rootElementNamespace = configurationEventTypeXMLDOM.getRootElementNamespace();
        this.schemaModelRoot = SchemaUtil.findRootElement(schemaModel, rootElementNamespace, this.getRootElementName());
        this.isPropertyExpressionXPath = configurationEventTypeXMLDOM.isPropertyExprXPath();

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

        // add properties for the root element
        List<ExplicitPropertyDescriptor> additionalSchemaProps = new ArrayList<ExplicitPropertyDescriptor>();

        // Add a property for each complex child element
        for (SchemaElementComplex complex : schemaModelRoot.getChildren())
        {
            String propertyName = complex.getName();
            Class returnType = Node.class;
            boolean isFragment = false;

            if (complex.getOptionalSimpleType() != null)
            {
                returnType = SchemaUtil.toReturnType(complex.getOptionalSimpleType());
                isFragment = false;
            }
            if (complex.isArray())
            {
                returnType = NodeList.class;
            }

            EventPropertyGetter getter = doResolvePropertyGetter(propertyName);
            EventPropertyDescriptor desc = new EventPropertyDescriptor(propertyName, returnType, false, false, complex.isArray(), false, isFragment);
            ExplicitPropertyDescriptor explicit = new ExplicitPropertyDescriptor(desc, getter, false, null);
            additionalSchemaProps.add(explicit);
        }

        // Add a property for each simple child element
        for (SchemaElementSimple simple : schemaModelRoot.getSimpleElements())
        {
            String propertyName = simple.getName();
            Class returnType = SchemaUtil.toReturnType(simple.getType());
            EventPropertyGetter getter = doResolvePropertyGetter(propertyName);
            EventPropertyDescriptor desc = new EventPropertyDescriptor(propertyName, returnType, false, false, simple.isArray(), false, false);
            ExplicitPropertyDescriptor explicit = new ExplicitPropertyDescriptor(desc, getter, false, null);
            additionalSchemaProps.add(explicit);
        }

        // Add a property for each attribute
        for (SchemaItemAttribute attribute : schemaModelRoot.getAttributes())
        {
            String propertyName = attribute.getName();
            Class returnType = SchemaUtil.toReturnType(attribute.getType());
            EventPropertyGetter getter = doResolvePropertyGetter(propertyName);
            EventPropertyDescriptor desc = new EventPropertyDescriptor(propertyName, returnType, false, false, false, false, false);
            ExplicitPropertyDescriptor explicit = new ExplicitPropertyDescriptor(desc, getter, false, null);
            additionalSchemaProps.add(explicit);
        }

        // Finally add XPath properties as that may depend on the rootElementNamespace
        super.initialize(configurationEventTypeXMLDOM.getXPathProperties().values(), additionalSchemaProps);
    }

    protected FragmentEventType doResolveFragmentType(String property)
    {
        return null;
        /*
        TODO
        Property prop = PropertyParser.parse(property, this.getEventAdapterService(), false);

        SchemaItem item = prop.getPropertyTypeSchema(schemaModelRoot, this.getEventAdapterService());
        if (item == null)
        {
            return null;
        }

        if (!(item instanceof SchemaElementComplex))
        {
            return null;
        }

        SchemaElementComplex complex = (SchemaElementComplex) item;
        if (complex.getOptionalSimpleType() != null)
        {
            return null;    // no transposal if the complex type also has a simple value else that is hidden
        }

        String[] atomicProps = prop.toPropertyArray();

        String delimiter = "";
        StringBuilder propertyNameXPath = new StringBuilder(this.getRootElementName());
        for (String atomic : atomicProps)
        {
            propertyNameXPath.append(delimiter);
            propertyNameXPath.append(atomic);
            delimiter = "/";
        }

        EventType existingType = this.getEventAdapterService().getXMLDOMType(propertyNameXPath.toString());
        if (existingType == null)
        {
            return null;
        }
        return new FragmentEventType(existingType, complex.isArray(), false);
        */
    }

    protected Class doResolvePropertyType(String property) {
        Property prop = PropertyParser.parse(property, this.getEventAdapterService(), false);
        SchemaItem item = prop.getPropertyTypeSchema(schemaModelRoot, this.getEventAdapterService());
        if (item == null)
        {
            throw new PropertyAccessException("Failed to locate property '" + property + "' in schema");
        }

        return SchemaUtil.toReturnType(item);
    }

    protected EventPropertyGetter doResolvePropertyGetter(String property) {
        EventPropertyGetter getter = propertyGetterCache.get(property);
        if (getter != null)
        {
            return getter;
        }
        
        if (!isPropertyExpressionXPath)
        {
            Property prop = PropertyParser.parse(property, this.getEventAdapterService(), false);

            SchemaItem item = prop.getPropertyTypeSchema(schemaModelRoot, this.getEventAdapterService());
            if (item == null)
            {
                throw new PropertyAccessException("Failed to locate property '" + property + "' in schema");
            }

            getter = prop.getGetterDOM(schemaModelRoot, this.getEventAdapterService());

            Class returnType = SchemaUtil.toReturnType(item);
            if (returnType != Node.class)
            {
                getter = new DOMConvertingGetter((DOMPropertyGetter) getter, returnType);
            }
            propertyGetterCache.put(property, getter);
            
            return getter;
        }
        else
        {
            return resolvePropertyXPath(property);
        }
    }

    private EventPropertyGetter resolvePropertyXPath(String property)
    {
        try
        {
            EventPropertyGetter resolved = SchemaXMLPropertyParser.getXPathResolution(property,getXPathFactory(),getRootElementName(),rootElementNamespace, schemaModel, this.getEventAdapterService());
            propertyGetterCache.put(property, resolved);
            return resolved;
        }
        catch (XPathExpressionException e) {
            throw new EPException("Error constructing XPath expression from property name '" + property + '\'', e);
        }
    }
}
