/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import antlr.collections.AST;
import net.esper.eql.generated.EqlTokenTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Builds a filter specification from filter AST nodes.
 */
public class ASTFilterSpecHelper implements EqlTokenTypes
{

    /**
     * Return the generated property name that is defined by the AST child node and it's siblings.
     * @param propertyNameExprChildNode is the child node from which to start putting the property name together
     * @return property name, ie. indexed[1] or mapped('key') or nested.nested or a combination or just 'simple'.
     */
    protected static String getPropertyName(AST propertyNameExprChildNode)
    {
        StringBuilder buffer = new StringBuilder();
        String delimiter = "";
        AST child = propertyNameExprChildNode;

        do
        {
            buffer.append(delimiter);

            switch (child.getType()) {
                case EVENT_PROP_SIMPLE:
                    buffer.append(child.getFirstChild().getText());
                    break;
                case EVENT_PROP_MAPPED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append('(');
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append(')');
                    break;
                case EVENT_PROP_INDEXED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append('[');
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append(']');
                    break;
                case EVENT_PROP_DYNAMIC_SIMPLE:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append('?');
                    break;
                case EVENT_PROP_DYNAMIC_MAPPED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append('(');
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append(')');
                    buffer.append('?');
                    break;
                case EVENT_PROP_DYNAMIC_INDEXED:
                    buffer.append(child.getFirstChild().getText());
                    buffer.append('[');
                    buffer.append(child.getFirstChild().getNextSibling().getText());
                    buffer.append(']');
                    buffer.append('?');
                    break;
                default:
                    throw new IllegalStateException("Event property AST node not recognized, type=" + child.getType());
            }

            delimiter = ".";
            child = child.getNextSibling();
        }
        while (child != null);

        return buffer.toString();
    }

    private static Log log = LogFactory.getLog(ASTFilterSpecHelper.class);
}
