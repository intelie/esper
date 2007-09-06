/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * Part of a select-clause to describe individual select-clause expressions.
 */
public class SelectClauseElement implements Serializable
{
    private static final long serialVersionUID = 0L;

    private Expression expression;
    private String asName;

    /**
     * Ctor.
     * @param expression is the selection expression
     */
    public SelectClauseElement(Expression expression)
    {
        this.expression = expression;
    }

    /**
     * Ctor.
     * @param expression is the selection expression
     * @param optionalAsName is the "as"-tag for the expression
     */
    public SelectClauseElement(Expression expression, String optionalAsName)
    {
        this.expression = expression;
        this.asName = optionalAsName;
    }

    /**
     * Returns the selection expression.
     * @return expression
     */
    public Expression getExpression()
    {
        return expression;
    }

    /**
     * Sets the selection expression.
     * @param expression is the selection expression
     */
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    /**
     * Returns the optional "as"-name of the expression, or null if not defined
     * @return tag or null for selection expression
     */
    public String getAsName()
    {
        return asName;
    }

    /**
     * Sets the optional "as"-name of the expression, or null if not defined
     * @param asName column alias or null for selection expression
     */
    public void setAsName(String asName)
    {
        this.asName = asName;
    }

    /**
     * Renders the element in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        expression.toEQL(writer);
        if (asName != null)
        {
            writer.write(" as ");
            writer.write(asName);
        }
    }
}
