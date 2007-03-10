package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;
import net.esper.util.MetaDefItem;

import java.util.Map;

/**
 * Interface for range-type filter parameters for type checking and to obtain the filter values for endpoints based
 * on prior results.
 */
public interface FilterSpecParamRangeValue extends MetaDefItem
{
    /**
     * Returns the filter value representing the endpoint.
     * @param matchedEvents is the prior results
     * @return filter value
     */
    public Double getFilterValue(MatchedEventMap matchedEvents);
}
