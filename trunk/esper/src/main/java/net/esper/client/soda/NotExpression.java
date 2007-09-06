/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Negates the contained-within subexpression.
 * <p>
 * Has a single child expression to be negated.
 */
public class NotExpression extends ExpressionBase
{
    /**
     * Ctor.
     * @param inner is the expression to negate
     */
    public NotExpression(Expression inner)
    {
        this.addChild(inner);
    }

    /**
     * Ctor - for use to create an expression tree, without child expression.
     */
    public NotExpression()
    {        
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("not ");
        this.getChildren().get(0).toEQL(writer);
    }
}
