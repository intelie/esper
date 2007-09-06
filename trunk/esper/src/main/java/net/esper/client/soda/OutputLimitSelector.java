/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.client.soda;

/**
 * Selector for use in output rate limiting.
 */
public enum OutputLimitSelector
{
    /**
     * Output first event.
     */
    FIRST ("first"),

    /**
     * Output last event.
     */
    LAST ("last"),

    /**
     * Output all events.
     */
    ALL ("all");

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
