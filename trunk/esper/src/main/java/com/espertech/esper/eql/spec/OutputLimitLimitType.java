package com.espertech.esper.eql.spec;

/**
 * Enum for describing the type of output limit within an interval. 
 */
public enum OutputLimitLimitType
{
    /**
     * Output first event, relative to the output batch.
     */
    FIRST,

    /**
     * Output last event, relative to the output batch.
     */
    LAST,

    /**
     * Output all events, relative to the output batch.
     */
    ALL,

    /**
     * Output a snapshot of the current state, relative to the full historical state of a statement.
     */
    SNAPSHOT
}
