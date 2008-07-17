/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client.soda;

/**
 * Unit for output rate limiting.
 */
public enum OutputLimitUnit
{
    /**
     * The minutes unit.
     */
    MINUTES ("minutes"),

    /**
     * The seconds unit.
     */
    SECONDS ("seconds"),

    /**
     * The number of events unit.
     */
    EVENTS ("events"),

    /**
     * The unit representing a when-expression.
     */
    WHEN_EXPRESSION ("when"),

    /**
     * The unit representing a crontab-at-expression.
     */
    CRONTAB_EXPRESSION ("crontab");

    private String text;

    private OutputLimitUnit(String text)
    {
        this.text = text;
    }

    /**
     * Returns the text for the unit.
     * @return unit text
     */
    public String getText()
    {
        return text;
    }
}
