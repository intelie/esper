/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.eql.expression;

/**
 * Thrown to indicate a validation error in a filter expression.
 */
public class ExprValidationException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public ExprValidationException(String message)
    {
        super(message);
    }

    /**
     * Ctor.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public ExprValidationException(String message, Throwable cause)
    {
        super(message, cause);
    }
}