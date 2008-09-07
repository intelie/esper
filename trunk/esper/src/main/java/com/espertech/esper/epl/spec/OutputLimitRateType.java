/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.epl.spec;

/**
 * Enum for the type of rate for output-rate limiting.
 */
public enum OutputLimitRateType
{
    /**
     * Output by events.
     */
    EVENTS,

    /**
     * Output by seconds.
     */
    TIME_SEC,

    /**
     * Output by minutes.
     */
    TIME_MIN,

    /**
     * Output following a crontab-like schedule.
     */
    CRONTAB,

    /**
     * Output when an expression turns true.
     */
    WHEN_EXPRESSION
}
