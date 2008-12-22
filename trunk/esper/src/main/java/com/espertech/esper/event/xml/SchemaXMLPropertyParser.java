/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;

import com.espertech.esper.client.PropertyAccessException;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.collection.Pair;
import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import com.espertech.esper.type.IntValue;
import com.espertech.esper.type.StringValue;
import com.espertech.esper.util.ExecutionPathDebugLog;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.property.PropertyParser;
import com.espertech.esper.event.property.Property;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import java.util.List;

/**
 * Parses event property names and transforms to XPath expressions using the schema information supplied. Supports the
 * nested, indexed and mapped event properties.
 */
public class SchemaXMLPropertyParser
{
    /**
     * Return the xPath corresponding to the given property.
     * The propertyName String may be simple, nested, indexed or mapped.
     *
     * @param propertyName is the event property name
     * @param namespace is the default namespace
     * @param schemaModel is the schema model
     * @param xPathFactory is the xpath factory instance to use
     * @param rootElementName is the name of the root element
     * @return xpath expression
     * @throws XPathExpressionException when the XPath expression is invalid
     */
    public static PropertyResolutionResult getXPathResolution(String propertyName, XPathFactory xPathFactory, String rootElementName, String namespace, SchemaModel schemaModel, EventAdapterService eventAdapterService) throws XPathExpressionException
    {
        XPathNamespaceContext ctx = new XPathNamespaceContext();
        List<String> namespaces = schemaModel.getNamespaces();

        for (int i = 0; i < namespaces.size(); i++)
        {
            ctx.addPrefix("n" + i, namespaces.get(i));
        }

        Tree ast = SimpleXMLPropertyParser.parse(propertyName);
        Property property = PropertyParser.parse(propertyName, eventAdapterService, false);

        SchemaElementComplex rootComplexElement = SchemaUtil.findRootElement(schemaModel, namespace, rootElementName);
        String prefix = ctx.getPrefix(rootComplexElement.getNamespace());
        if (prefix == null) {
            prefix = "";
        }
        else {
            prefix = prefix + ':';
        }

        StringBuilder xPathBuf = new StringBuilder();
        xPathBuf.append('/');
        xPathBuf.append(prefix);
        xPathBuf.append(rootElementName);

        SchemaElementComplex parentComplexElement = rootComplexElement;
        Pair<String, QName> pair = null;

        if (ast.getChildCount() == 1)
        {
            pair = makeProperty(rootComplexElement, ast.getChild(0), ctx);
            if (pair == null)
            {
                throw new PropertyAccessException("Failed to locate property '" + propertyName + "' in schema");
            }
            xPathBuf.append(pair.getFirst());
        }
        else
        {
            for (int i = 0; i < ast.getChildCount(); i++)
            {
                Tree child = ast.getChild(i);
                pair = makeProperty(parentComplexElement, child, ctx);
                if (pair == null)
                {
                    throw new PropertyAccessException("Failed to locate property '" + propertyName + "' nested property part '" + child.toString() + "' in schema");
                }

                String text = child.getChild(0).getText();
                SchemaItem obj = SchemaUtil.findPropertyMapping(parentComplexElement, text);
                if (obj instanceof SchemaElementComplex)
                {
                    parentComplexElement = (SchemaElementComplex) obj;
                }
                xPathBuf.append(pair.getFirst());
            }
        }

        String xPath = xPathBuf.toString();
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".parse XPath for property '" + propertyName + "' is expression=" + xPath);
        }

        // Compile assembled XPath expression
        XPath path = xPathFactory.newXPath();
        path.setNamespaceContext(ctx);

        if (log.isDebugEnabled())
        {
            log.debug("Compiling XPath " + xPath);
        }
        XPathExpression expr = path.compile(xPath);

        // get type
        SchemaItem item = property.getPropertyTypeSchema(rootComplexElement, eventAdapterService);
        if (item == null)
        {
            return null;
        }

        Class resultType = SchemaUtil.toReturnType(item);
        EventPropertyGetter getter = new XPathPropertyGetter(propertyName, expr, pair.getSecond(), resultType);
        return new PropertyResolutionResult(getter, resultType, parentComplexElement);
    }

    private static Pair<String, QName> makeProperty(SchemaElementComplex parent, Tree child, XPathNamespaceContext ctx)
    {
        String text = child.getChild(0).getText();
        SchemaItem obj = SchemaUtil.findPropertyMapping(parent, text);
        if ((obj instanceof SchemaElementSimple) || (obj instanceof SchemaElementComplex)){
            return makeElementProperty((SchemaElement) obj, child, ctx);
        }
        else if (obj != null) {
            return makeAttributeProperty((SchemaItemAttribute) obj, child, ctx);
        }
        else
        {
            return null;
        }
    }

    private static Pair<String, QName> makeAttributeProperty(SchemaItemAttribute attribute, Tree child, XPathNamespaceContext ctx)
    {
        String prefix = ctx.getPrefix(attribute.getNamespace());
        if (prefix == null)
            prefix = "";
        else
            prefix = prefix + ':';
        switch (child.getType())
        {
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_SIMPLE:
            case EsperEPL2GrammarParser.EVENT_PROP_SIMPLE:
                QName type = SchemaUtil.simpleTypeToQName(attribute.getType());
                String path = "/@" + prefix + child.getChild(0).getText();
                return new Pair<String, QName>(path, type);
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_MAPPED:
            case EsperEPL2GrammarParser.EVENT_PROP_MAPPED:
                throw new RuntimeException("Mapped properties not applicable to attributes");
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_INDEXED:
            case EsperEPL2GrammarParser.EVENT_PROP_INDEXED:
                throw new RuntimeException("Mapped properties not applicable to attributes");
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    private static Pair<String, QName> makeElementProperty(SchemaElement schemaElement, Tree child, XPathNamespaceContext ctx)
    {
        QName type;
        if (schemaElement instanceof SchemaElementSimple) {
            type = SchemaUtil.simpleTypeToQName(((SchemaElementSimple) schemaElement).getType());
        }
        else
        {
            SchemaElementComplex complex = (SchemaElementComplex) schemaElement;
            if (complex.getOptionalSimpleType() != null)
            {
                type = SchemaUtil.simpleTypeToQName(complex.getOptionalSimpleType());
            }
            else
            {
                // The result is a node
                type = XPathConstants.NODE;
            }
        }

        String prefix = ctx.getPrefix(schemaElement.getNamespace());
        if (prefix == null) {
            prefix = "";
        }
        else {
            prefix = prefix + ':';
        }

        switch (child.getType())
        {
            case EsperEPL2GrammarParser.EVENT_PROP_SIMPLE:
                if (schemaElement.isArray()) {
                    throw new PropertyAccessException("Simple property not allowed in repeating elements");
                }
                return new Pair<String, QName>('/' + prefix + child.getChild(0).getText(), type);

            case EsperEPL2GrammarParser.EVENT_PROP_MAPPED:
                if (!schemaElement.isArray()) {
                    throw new PropertyAccessException("Element " + child.getChild(0).getText() + " is not a collection, cannot be used as mapped property");
                }
                String key = StringValue.parseString(child.getChild(1).getText());
                return new Pair<String, QName>('/' + prefix + child.getChild(0).getText() + "[@id='" + key + "']", type);

            case EsperEPL2GrammarParser.EVENT_PROP_INDEXED:
                if (!schemaElement.isArray()) {
                    throw new PropertyAccessException("Element " + child.getChild(0).getText() + " is not a collection, cannot be used as mapped property");
                }
                int index = IntValue.parseString(child.getChild(1).getText());
                int xPathPosition = index + 1;
                return new Pair<String, QName>('/' + prefix + child.getChild(0).getText() + "[position() = " + xPathPosition + ']', type);

            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    private static Log log = LogFactory.getLog(SchemaXMLPropertyParser.class);
}
