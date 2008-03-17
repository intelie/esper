/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.parse;

import com.espertech.esper.epl.generated.EsperEPL2GrammarParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.antlr.runtime.tree.Tree;

/**
 * Builds a filter specification from filter AST nodes.
 */
public class ASTFilterSpecHelper
{
    /**
     * Return the generated property name that is defined by the AST child node and it's siblings.
     * @param parentNode the AST node to consider as the parent for the child nodes to look at
     * @param startIndex the index of the child node to start looking at
     * @return property name, ie. indexed[1] or mapped('key') or nested.nested or a combination or just 'simple'.
     */
    protected static String getPropertyName(Tree parentNode, int startIndex)
    {
        StringBuilder buffer = new StringBuilder();
        String delimiter = "";

        for (int i = startIndex; i < parentNode.getChildCount(); i++)
        {
        	Tree child = parentNode.getChild(i);
            buffer.append(delimiter);

            switch (child.getType()) {
                case EsperEPL2GrammarParser.EVENT_PROP_SIMPLE:
                    buffer.append(child.getChild(0).getText());
                    break;
                case EsperEPL2GrammarParser.EVENT_PROP_MAPPED:
                    buffer.append(child.getChild(0).getText());
                    buffer.append('(');
                    buffer.append(child.getChild(1).getText());
                    buffer.append(')');
                    break;
                case EsperEPL2GrammarParser.EVENT_PROP_INDEXED:
                    buffer.append(child.getChild(0).getText());
                    buffer.append('[');
                    buffer.append(child.getChild(1).getText());
                    buffer.append(']');
                    break;
                case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_SIMPLE:
                    buffer.append(child.getChild(0).getText());
                    buffer.append('?');
                    break;
                case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_MAPPED:
                    buffer.append(child.getChild(0).getText());
                    buffer.append('(');
                    buffer.append(child.getChild(1).getText());
                    buffer.append(')');
                    buffer.append('?');
                    break;
                case EsperEPL2GrammarParser.EVENT_PROP_DYNAMIC_INDEXED:
                    buffer.append(child.getChild(0).getText());
                    buffer.append('[');
                    buffer.append(child.getChild(1).getText());
                    buffer.append(']');
                    buffer.append('?');
                    break;
                default:
                    throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
            }

            delimiter = ".";
        }

        return buffer.toString();
    }

    private static Log log = LogFactory.getLog(ASTFilterSpecHelper.class);
}
