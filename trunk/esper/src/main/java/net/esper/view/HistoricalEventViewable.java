package net.esper.view;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.util.StopCallback;

import java.util.Set;
import java.util.List;

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
     * @return array of lists with one list for each event-per-stream row  
     */
    public List<EventBean>[] poll(EventBean[][] lookupEventsPerStream);
}
