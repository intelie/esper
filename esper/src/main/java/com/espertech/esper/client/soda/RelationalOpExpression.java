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
 * Comparison using one of the relational operators (=, !=, <, <=, >, >=, is, is not).
 */
public class RelationalOpExpression extends ExpressionBase
{
    private String operator;
    private static final long serialVersionUID = -4590496481449181068L;

    /**
     * Ctor.
     */
    public RelationalOpExpression() {
    }

    /**

     * Ctor.
     * @param operator is the relational operator.
     */
    public RelationalOpExpression(String operator)
    {
        this.operator = operator.trim();
    }

    /**
     * Ctor.
     * @param left provides a value to compare against
     * @param operator is the operator to use
     * @param right provides a value to compare against
     */
    public RelationalOpExpression(Expression left, String operator, Expression right)
    {
        this.operator = operator.trim();
        addChild(left);

        if (right != null)
        {
            addChild(right);
        }
        else
        {
            addChild(new ConstantExpression(null));
        }
    }

    /**
     * Returns the operator to use.
     * @return operator.
     */
    public String getOperator()
    {
        return operator;
    }

    /**
     * Sets the operator to use.
     * @param operator to use
     */
    public void setOperator(String operator)
    {
        this.operator = operator;
    }

    public ExpressionPrecedenceEnum getPrecedence()
    {
        if (operator.equals("=")) {
            return ExpressionPrecedenceEnum.EQUALS;
        }
        return ExpressionPrecedenceEnum.RELATIONAL_BETWEEN_IN;
    }

    public void toPrecedenceFreeEPL(StringWriter writer)
    {
        this.getChildren().get(0).toEPL(writer, getPrecedence());
        writer.write(' ');
        writer.write(operator);
        writer.write(' ');
        this.getChildren().get(1).toEPL(writer, getPrecedence());
    }
}
