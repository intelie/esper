/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.parse;

import antlr.collections.AST;
import net.esper.type.*;
import net.esper.eql.generated.EqlEvalTokenTypes;

/**
 * Parses constant strings and returns the constant Object.
 */
public class ASTConstantHelper implements EqlEvalTokenTypes
{
    /**
     * Parse the AST constant node and return Object value.
     * @param node - parse node for which to parse the string value
     * @return value matching AST node type
     */
    public static Object parse(AST node)
    {
        switch(node.getType())
        {
            case NUM_INT:       return IntValue.parseString(node.getText());
            case INT_TYPE:      return IntValue.parseString(node.getText());
            case LONG_TYPE:     return LongValue.parseString(node.getText());
            case BOOL_TYPE:     return BoolValue.parseString(node.getText());
            case FLOAT_TYPE:    return FloatValue.parseString(node.getText());
            case DOUBLE_TYPE:   return DoubleValue.parseString(node.getText());
            case STRING_TYPE:   return StringValue.parseString(node.getText());
            case NULL_TYPE:     return null;
            default:
                throw new IllegalArgumentException("Unexpected constant of non-primitve type " + node.getType() + " encountered");
        }
    }
}
