/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

package com.espertech.esper.epl.expression;

/**
 * Thrown to indicate a validation error in an expression originating from a property resolution error.
 */
public class ExprValidationPropertyException extends ExprValidationException
{
    private static final long serialVersionUID = -3377829438234985081L;

    /**
     * Ctor.
     * @param message - validation error message
     */
    public ExprValidationPropertyException(String message)
    {
        super(message);
    }

    /**
     * Ctor.
     * @param message is the error message
     * @param cause is the inner exception
     */
    public ExprValidationPropertyException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
