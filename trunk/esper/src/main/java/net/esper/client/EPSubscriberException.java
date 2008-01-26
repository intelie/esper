/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client;

/**
 * This exception is thrown to indicate that a subscriber registration failed
 * such as when the subscribe does not expose an acceptable method to receive statement results.
 */
public class EPSubscriberException extends EPException
{
    /**
     * Ctor.
     * @param message - error message
     */
    public EPSubscriberException(final String message)
    {
        super(message);
    }
}
