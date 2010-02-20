/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

import com.espertech.esper.core.EPStatementObjectModelHelper;

import java.io.StringWriter;

/**
 * Constant value returns a fixed value for use in expressions.
 */
public class ConstantExpression extends ExpressionBase
{
    private Object constant;
    private String constantType;
    private static final long serialVersionUID = 1787950621647511049L;

    public ConstantExpression() {
    }

    public String getConstantType()
    {
        return constantType;
    }

    public void setConstantType(String constantType)
    {
        this.constantType = constantType;
    }

    /**
     * Ctor.
     * @param constant is the constant value, or null to represent the null value
     */
    public ConstantExpression(Object constant)
    {
        this.constant = constant;
    }

    public ConstantExpression(Object constant, String constantType)
    {
        this.constant = constant;
        this.constantType = constantType;
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        return ExpressionPrecedenceEnum.UNARY;
    }

    public void toPrecedenceFreeEPL(StringWriter writer)
    {
        EPStatementObjectModelHelper.renderEPL(writer, constant);
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
