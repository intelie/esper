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
 * Regular expression evaluates a "regexp" regular expression.
 */
public class RegExpExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     */    
    public RegExpExpression()
    {
    }

    /**
     * Ctor.
     * @param left provides values to match against regexp string
     * @param right provides the regexp string
     */
    public RegExpExpression(Expression left, Expression right)
    {
        this(left, right, null);
    }

    /**
     * Ctor.
     * @param left provides values to match against regexp string
     * @param right provides the regexp string
     * @param escape provides the escape character
     */
    public RegExpExpression(Expression left, Expression right, Expression escape)
    {
        this.getChildren().add(left);
        this.getChildren().add(right);
        if (escape != null)
        {
            this.getChildren().add(escape);
        }
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write("(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(" regexp ");
        this.getChildren().get(1).toEQL(writer);

        if (this.getChildren().size() > 2)
        {
            writer.write(" escape ");
            this.getChildren().get(2).toEQL(writer);
        }
        writer.write(")");
    }
}
