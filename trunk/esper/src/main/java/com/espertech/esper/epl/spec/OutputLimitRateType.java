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
