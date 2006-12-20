package net.esper.filter;

import net.esper.event.EventType;
import java.util.List;

/**
 * Contains the filter criteria to sift through events. The filter criteria are the event class to look for and
 * a set of parameters (property names, operators and constant/range values).
 */
public interface FilterValueSet
{
    /**
     * Returns type of event to filter for.
     * @return event type
     */
    public EventType getEventType();

    /**
     * Returns list of filter parameters.
     * @return list of filter params
     */
    public List<FilterValueSetParam> getParameters();
}
