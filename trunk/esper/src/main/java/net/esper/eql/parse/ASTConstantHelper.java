/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import net.esper.type.*;
import net.esper.eql.generated.EsperEPLParser;
import org.antlr.runtime.tree.Tree;

/**
 * Parses constant strings and returns the constant Object.
 */
public class ASTConstantHelper
{
    /**
     * Parse the AST constant node and return Object value.
     * @param node - parse node for which to parse the string value
     * @return value matching AST node type
     */
    public static Object parse(Tree node)
    {
        switch(node.getType())
        {
            case EsperEPLParser.NUM_INT:       return parseIntLong(node.getText());
            case EsperEPLParser.INT_TYPE:      return parseIntLong(node.getText());
            case EsperEPLParser.LONG_TYPE:     return LongValue.parseString(node.getText());
            case EsperEPLParser.BOOL_TYPE:     return BoolValue.parseString(node.getText());
            case EsperEPLParser.FLOAT_TYPE:    return FloatValue.parseString(node.getText());
            case EsperEPLParser.DOUBLE_TYPE:   return DoubleValue.parseString(node.getText());
            case EsperEPLParser.STRING_TYPE:   return StringValue.parseString(node.getText());
            case EsperEPLParser.NULL_TYPE:     return null;
            default:
                throw new IllegalArgumentException("Unexpected constant of non-primitve type " + node.getType() + " encountered");
        }
    }

    private static Object parseIntLong(String arg)
    {
        // try to parse as an int first, else try to parse as a long
        try
        {
            return IntValue.parseString(arg);
        }
        catch (NumberFormatException ex)
        {
            try
            {
                return LongValue.parseString(arg);
            }
            catch (Exception e)
            {
                throw ex;
            }
        }

    }
}
