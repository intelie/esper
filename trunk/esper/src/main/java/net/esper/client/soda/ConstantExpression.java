/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

import net.esper.core.EPStatementObjectModelHelper;

import java.io.StringWriter;

/**
 * Constant value returns a fixed value for use in expressions.
 */
public class ConstantExpression extends ExpressionBase
{
    private Object constant;

    /**
     * Ctor.
     * @param constant is the constant value, or null to represent the null value
     */
    public ConstantExpression(Object constant)
    {
        this.constant = constant;
    }

    public void toEQL(StringWriter writer)
    {
        EPStatementObjectModelHelper.renderEQL(writer, constant);
    }

    /**
     * Returns the constant value that the expression represents.
     * @return value of constant
     */
    public Object getConstant()
    {
        return constant;
    }

    /**
     * Sets the constant value that the expression represents.
     * @param constant is the value, or null to indicate the null value
     */
    public void setConstant(Object constant)
    {
        this.constant = constant;
    }
}
