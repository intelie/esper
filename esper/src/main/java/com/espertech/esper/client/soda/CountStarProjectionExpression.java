/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Count of (distinct) rows, equivalent to "count(*)"
 */
public class CountStarProjectionExpression extends ExpressionBase
{
    private static final long serialVersionUID = -784442964133996045L;

    /**
     * Ctor - for use to create an expression tree, without inner expression.
     */
    public CountStarProjectionExpression()
    {
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        return ExpressionPrecedenceEnum.UNARY;
    }

    public void toPrecedenceFreeEPL(StringWriter writer)
    {
        writer.write("count(*");
        String delimiter = "";
        for (Expression param : this.getChildren()) {
            writer.write(delimiter);
            delimiter = ", ";
            param.toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }
        writer.write(")");
    }
}
