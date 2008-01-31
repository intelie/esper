package com.espertech.esper.eql.named;

import com.espertech.esper.event.EventBean;

/**
 * A deletion strategy is for use with named window in on-delete statements and encapsulates
 * the strategy for resolving one or more events arriving in the on-clause of an on-delete statement
 * to one or more events to be deleted from the named window.
 */
public interface LookupStrategy
{
    /**
     * Determines the events to be deleted from a named window.
     * @param newData is the correlation events
     * @return the events to delete from the named window
     */
    public EventBean[] lookup(EventBean[] newData);
}
