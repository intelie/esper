package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;
import net.esper.util.MetaDefItem;

import java.util.Map;

/**
 * Denotes a value for use by the in-keyword within a list of values
 */
public interface FilterSpecParamInValue extends MetaDefItem
{
    /**
     * Returns the actual value to filter for from prior matching events
     * @param matchedEvents is a map of matching events
     * @return filter-for value
     */
    public Object getFilterValue(MatchedEventMap matchedEvents);
}
