package com.espertech.esper.view;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.StopCallback;
import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.PollResultIndexingStrategy;

/**
 * Interface for views that poll data based on information from other streams.
 */
public interface HistoricalEventViewable extends Viewable, ValidatedView, StopCallback
{
    /**
     * Poll for stored historical or reference data using events per stream and
     * returing for each event-per-stream row a separate list with events
     * representing the poll result.
     * @param lookupEventsPerStream is the events per stream where the
     * first dimension is a number of rows (often 1 depending on windows used) and
     * the second dimension is the number of streams participating in a join.
     * @param indexingStrategy the strategy to use for converting poll results into a indexed table for fast lookup 
     * @return array of lists with one list for each event-per-stream row  
     */
    public EventTable[] poll(EventBean[][] lookupEventsPerStream, PollResultIndexingStrategy indexingStrategy);
}
