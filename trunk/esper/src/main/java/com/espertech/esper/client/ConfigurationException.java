/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

/**
 * Thrown to indicate a configuration problem.
 */
final public class ConfigurationException extends EPException
{
    /**
     * Ctor.
     * @param message - error message
     */
    public ConfigurationException(final String message)
    {
        super(message);
    }

    /**
     * Ctor for an inner exception and message.
     * @param message - error message
     * @param cause - inner exception
     */
    public ConfigurationException(final String message, final Throwable cause)
    {
        super(message, cause);
    }

    /**
     * Ctor - just an inner exception.
     * @param cause - inner exception
     */
    public ConfigurationException(final Throwable cause)
    {
        super(cause);
    }
}