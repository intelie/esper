package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;

/**
 * Interface for range-type filter parameters for type checking and to obtain the filter values for endpoints based
 * on prior results.
 */
public interface FilterSpecParamRangeValue
{
    /**
     * Check the type against the map of event tag and type.
     * @param taggedEventTypes map of event tags and types
     */
    public void checkType(Map<String, EventType> taggedEventTypes);

    /**
     * Returns the filter value representing the endpoint.
     * @param matchedEvents is the prior results
     * @return filter value
     */
    public double getFilterValue(MatchedEventMap matchedEvents);
}
