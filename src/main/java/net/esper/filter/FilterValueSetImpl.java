package net.esper.filter;

import net.esper.event.EventType;

import java.util.List;

/**
 * Container for filter values for use by the {@link FilterService} to filter and distribute incoming events.
 */
public class FilterValueSetImpl implements FilterValueSet
{
    private final EventType eventType;
    private final List<FilterValueSetParam> parameters;

    /**
     * Ctor.
     * @param eventType - type of event to filter for
     * @param parameters - list of filter parameters
     */
    public FilterValueSetImpl(EventType eventType, List<FilterValueSetParam> parameters)
    {
        this.eventType = eventType;
        this.parameters = parameters;
    }

    /**
     * Returns event type to filter for.
     * @return event type to filter for
     */
    public EventType getEventType()
    {
        return eventType;
    }

    /**
     * Returns list of filter parameters.
     * @return list of filter parameters
     */
    public List<FilterValueSetParam> getParameters()
    {
        return parameters;
    }
}
