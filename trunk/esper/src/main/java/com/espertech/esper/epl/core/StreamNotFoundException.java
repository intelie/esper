/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

/**
 * Exception to indicate that a stream name could not be resolved.
 */
public class StreamNotFoundException extends StreamTypesException
{
    /**
     * Ctor.
     * @param msg - message
     */
    public StreamNotFoundException(String msg)
    {
        super(msg);
    }
}
