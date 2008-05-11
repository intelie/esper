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
    TIME_MIN
}
