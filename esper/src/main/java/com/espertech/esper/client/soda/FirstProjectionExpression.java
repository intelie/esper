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
    private boolean isDistinct;

    /**
     * Ctor.
     * @param isDistinct true for distinct
     */
    public FirstProjectionExpression(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    /**
     * Ctor.
     * @param expression to aggregate
     * @param isDistinct true for distinct
     */
    public FirstProjectionExpression(Expression expression, boolean isDistinct)
    {
        this.isDistinct = isDistinct;
        this.getChildren().add(expression);
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEPL(StringWriter writer)
    {
        writer.write("first");
        writer.write('(');
        if (isDistinct)
        {
            writer.write("distinct ");
        }
        if (this.getChildren().size() > 0)
        {
            this.getChildren().get(0).toEPL(writer);
        }
        writer.write(")");
    }

    /**
     * Returns true for distinct.
     * @return boolean indicating distinct or not
     */
    public boolean isDistinct()
    {
        return isDistinct;
    }

    /**
     * Set to true for distinct.
     * @param distinct indicating distinct or not
     */
    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }
}