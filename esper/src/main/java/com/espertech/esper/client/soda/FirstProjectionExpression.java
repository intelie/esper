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
 * Represents the "first" aggregation function.
 */
public class FirstProjectionExpression extends ExpressionBase
{
    /**
     * Ctor.
     */
    public FirstProjectionExpression() {
    }

    /**
     * Ctor.
     * @param expression to aggregate
     */
    public FirstProjectionExpression(Expression expression)
    {
        this.getChildren().add(expression);
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        return ExpressionPrecedenceEnum.UNARY;
    }

    public void toPrecedenceFreeEPL(StringWriter writer)
    {
        writer.write("first");
        writer.write('(');
        if (this.getChildren().size() > 0)
        {
            this.getChildren().get(0).toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }
        writer.write(")");
    }
}