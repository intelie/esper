/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Previous function for obtaining property values of previous events.
 */
public class PreviousExpression extends ExpressionBase
{
    /**
     * Ctor.
     */
    public PreviousExpression()
    {
    }

    /**
     * Ctor.
     * @param expression provides the index to use
     * @param propertyName is the name of the property to return the value for
     */
    public PreviousExpression(Expression expression, String propertyName)
    {
        this.addChild(expression);
        this.addChild(new PropertyValueExpression(propertyName));
    }

    /**
     * Ctor.
     * @param index provides the index
     * @param propertyName is the name of the property to return the value for
     */
    public PreviousExpression(int index, String propertyName)
    {
        this.addChild(new ConstantExpression(index));
        this.addChild(new PropertyValueExpression(propertyName));
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("prev(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(", ");
        this.getChildren().get(1).toEQL(writer);
        writer.write(')');
    }
}
