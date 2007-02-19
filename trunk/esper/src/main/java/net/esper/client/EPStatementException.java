/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

/**
 * This exception is thrown to indicate a problem in statement creation, such as syntax error or type checking problem
 * etc.
 */
public class EPStatementException extends EPException
{
    private String expression;

    /**
     * Ctor.
     * @param message - error message
     * @param expression - expression text
     */
    public EPStatementException(final String message, final String expression)
    {
        super(message);
        this.expression = expression;
    }

    /**
     * Returns expression text for statement.
     * @return expression text
     */
    public String getExpression()
    {
        return expression;
    }

    /**
     * Sets expression text for statement.
     * @param expression text
     */
    public void setExpression(String expression)
    {
        this.expression = expression;
    }

    public String getMessage() {
        StringBuilder msg = new StringBuilder(super.getMessage());
        if (expression != null)
        {
            msg.append(" [");
            msg.append(expression);
            msg.append(']');
        }
        return msg.toString();
    }
}
