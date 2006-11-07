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
    public List<EventBean>[] poll(EventBean[][] lookupEventsPerStream);
}
