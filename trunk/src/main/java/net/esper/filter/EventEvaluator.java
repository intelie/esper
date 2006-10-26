package net.esper.filter;

import net.esper.event.EventBean;

import java.util.List;

/**
 * Interface for matching an event instance based on the event's property values to
 * filters, specifically filter parameter constants or ranges.
 */
public interface EventEvaluator
{
    /**
     * Perform the matching of an event based on the event property values,
     * adding any callbacks for matches found to the matches list.
     * @param event is the event object wrapper to obtain event property values from
     * @param matches accumulates the matching filter callbacks
     */
    public void matchEvent(EventBean event, List<FilterCallback> matches);
}
