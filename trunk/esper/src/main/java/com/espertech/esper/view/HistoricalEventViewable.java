package com.espertech.esper.view;

import com.espertech.esper.epl.join.PollResultIndexingStrategy;
import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.db.DataCache;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.util.StopCallback;

import java.util.SortedSet;

/**
 * Interface for views that poll data based on information from other streams.
 */
public interface HistoricalEventViewable extends Viewable, ValidatedView, StopCallback
{
    public boolean hasRequiredStreams();
    public SortedSet<Integer> getRequiredStreams();

    // During iteration, hold rows stable.
    public ThreadLocal<DataCache> getDataCacheThreadLocal();
        
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
