/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.event.xml;

import com.espertech.esper.antlr.NoCaseSensitiveStream;
import com.espertech.esper.epl.generated.EsperEPL2GrammarLexer;
import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import com.espertech.esper.event.PropertyAccessException;
import com.espertech.esper.type.IntValue;
import com.espertech.esper.type.StringValue;
import com.espertech.esper.util.ExecutionPathDebugLog;
import org.antlr.runtime.CharStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.Tree;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.StringReader;

/**
 * Parses event property names and transforms to XPath expressions. Supports
 * nested, indexed and mapped event properties.
 */
public class SimpleXMLPropertyParser
{
    /**
     * Return the xPath corresponding to the given property.
     * The propertyName String may be simple, nested, indexed or mapped.
     *
     * @param propertyName is the property name to parse
     * @param rootElementName is the name of the root element for generating the XPath expression
     * @param defaultNamespacePrefix is the prefix of the default namespace
     * @param isResolvePropertiesAbsolute is true to indicate to resolve XPath properties as absolute props
     * or relative props
     * @return xpath expression
     */
    public static String parse(String propertyName, String rootElementName, String defaultNamespacePrefix, boolean isResolvePropertiesAbsolute)
    {
        Tree ast = parse(propertyName);

        StringBuilder xPathBuf = new StringBuilder();
        xPathBuf.append('/');
        if (isResolvePropertiesAbsolute)
        {
            if (defaultNamespacePrefix != null)
            {
                xPathBuf.append(defaultNamespacePrefix);
                xPathBuf.append(':');
            }
            xPathBuf.append(rootElementName);
        }

        if (ast.getChildCount() == 1)
        {
            xPathBuf.append(makeProperty(ast.getChild(0), defaultNamespacePrefix));
        }
        else
        {
            for (int i = 0; i < ast.getChildCount(); i++)
            {
                xPathBuf.append(makeProperty(ast.getChild(i), defaultNamespacePrefix));
            }
        }

        String xPath = xPathBuf.toString();
        if ((ExecutionPathDebugLog.isDebugEnabled) && (log.isDebugEnabled()))
        {
            log.debug(".parse For property '" + propertyName + "' the xpath is '" + xPath + '\'');
        }

        return xPath;
    }

    private static String makeProperty(Tree child, String defaultNamespacePrefix)
    {
        String prefix = "";
        if (defaultNamespacePrefix != null)
        {
            prefix = defaultNamespacePrefix + ":";
        }

        switch (child.getType())
        {
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_SIMPLE:
            case EsperEPL2GrammarParser.EVENT_PROP_SIMPLE:
                return '/' + prefix + child.getChild(0).getText();
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_MAPPED:
            case EsperEPL2GrammarParser.EVENT_PROP_MAPPED:
                String key = StringValue.parseString(child.getChild(1).getText());
                return '/' + prefix + child.getChild(0).getText() + "[@id='" + key + "']";
            case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_INDEXED:
            case EsperEPL2GrammarParser.EVENT_PROP_INDEXED:
                int index = IntValue.parseString(child.getChild(1).getText());
                return '/' + prefix + child.getChild(0).getText() + "[position() = " + index + ']';
            default:
                throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
        }
    }

    /**
     * Parses a given property name returning an AST.
     * @param propertyName to parse
     * @return AST syntax tree
     */
    protected static Tree parse(String propertyName)
    {
        CharStream input;
        try
        {
            input = new NoCaseSensitiveStream(new StringReader(propertyName));
        }
        catch (IOException ex)
        {
            throw new PropertyAccessException("IOException parsing property name '" + propertyName + '\'', ex);
        }

        EsperEPL2GrammarLexer lex = new EsperEPL2GrammarLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lex);
        EsperEPL2GrammarParser g = new EsperEPL2GrammarParser(tokens);
        EsperEPL2GrammarParser.startEventPropertyRule_return r;

        try
        {
             r = g.startEventPropertyRule();
        }
        catch (RecognitionException e)
        {
            throw new PropertyAccessException("Failed to parse property name '" + propertyName + '\'', e);
        }

        return (Tree) r.getTree();
    }

    private static final Log log = LogFactory.getLog(SimpleXMLPropertyParser.class);
}
