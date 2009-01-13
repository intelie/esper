/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.core;

import com.espertech.esper.collection.Pair;

/**
 * Base class for stream and property name resolution errors.
 */
public abstract class StreamTypesException extends Exception
{
    private final Pair<Integer, String> optionalSuggestion;

    /**
     * Ctor.
     * @param message - message
     */
    public StreamTypesException(String message, Pair<Integer, String> suggestion)
    {
        super(message);
        this.optionalSuggestion = suggestion;
    }

    public Pair<Integer, String> getOptionalSuggestion()
    {
        return optionalSuggestion;
    }
}
