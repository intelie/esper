/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

/**
 * Selector for use in output rate limiting.
 */
public enum OutputLimitSelector
{
    /**
     * Output first event of last interval.
     */
    FIRST ("first"),

    /**
     * Output last event of last interval.
     */
    LAST ("last"),

    /**
     * Output all events of last interval.
     */
    ALL ("all"),

    /**
     * Output all events as a snapshot considering the current state regardless of interval.
     */
    SNAPSHOT ("snapshot");

    private String text;

    private OutputLimitSelector(String text)
    {
        this.text = text;
    }

    /**
     * Returns the text for the selector.
     * @return text
     */
    public String getText()
    {
        return text;
    }
}
