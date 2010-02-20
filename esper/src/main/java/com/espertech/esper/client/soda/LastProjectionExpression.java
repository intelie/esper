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
 * Represents the "last" aggregation function.
 */
public class LastProjectionExpression extends ExpressionBase
{
    private boolean distinct;

    public LastProjectionExpression() {
    }

    /**
     * Ctor.
     * @param isDistinct true for distinct
     */
    public LastProjectionExpression(boolean isDistinct)
    {
        this.distinct = isDistinct;
    }

    /**
     * Ctor.
     * @param expression to aggregate
     * @param isDistinct true for distinct
     */
    public LastProjectionExpression(Expression expression, boolean isDistinct)
    {
        this.distinct = isDistinct;
        this.getChildren().add(expression);
    }

    public ExpressionPrecedenceEnum getPrecedence() {
        return ExpressionPrecedenceEnum.UNARY;
    }

    public void toPrecedenceFreeEPL(StringWriter writer) {
        writer.write("last");
        writer.write('(');
        if (distinct)
        {
            writer.write("distinct ");
        }
        if (this.getChildren().size() > 0)
        {
            this.getChildren().get(0).toEPL(writer, ExpressionPrecedenceEnum.MINIMUM);
        }
        writer.write(")");
    }

    /**
     * Returns true for distinct.
     * @return boolean indicating distinct or not
     */
    public boolean isDistinct()
    {
        return distinct;
    }

    /**
     * Returns true for distinct.
     * @return boolean indicating distinct or not
     */
    public boolean getDistinct()
    {
        return distinct;
    }

    /**
     * Set to true for distinct.
     * @param distinct indicating distinct or not
     */
    public void setDistinct(boolean distinct)
    {
        this.distinct = distinct;
    }
}