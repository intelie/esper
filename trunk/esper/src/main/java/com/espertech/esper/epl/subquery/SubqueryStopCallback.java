package com.espertech.esper.epl.subquery;

import com.espertech.esper.util.StopCallback;
import com.espertech.esper.epl.join.table.EventTable;

/**
 * Implements a stop callback for use with subqueries to clear their indexes
 * when a statement is stopped.
 */
public class SubqueryStopCallback implements StopCallback
{
    private final EventTable eventIndex;

    /**
     * Ctor.
     * @param eventIndex index to clear
     */
    public SubqueryStopCallback(EventTable eventIndex) {
        this.eventIndex = eventIndex;
    }

    // Clear out index on statement stop
    public void stop()
    {
        if (eventIndex != null)
        {
            eventIndex.clear();
        }
    }
}