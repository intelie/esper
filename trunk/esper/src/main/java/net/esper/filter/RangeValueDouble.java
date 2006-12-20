package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;

/**
 * A Double-typed value as a filter parameter representing a range.
 */
public class RangeValueDouble implements FilterSpecParamRangeValue
{
    private final double doubleValue;

    /**
     * Ctor.
     * @param doubleValue is the value of the range endpoint
     */
    public RangeValueDouble(double doubleValue)
    {
        this.doubleValue = doubleValue;
    }

    public final void checkType(Map<String, EventType> taggedEventTypes)
    {
        return;
    }

    public final double getFilterValue(MatchedEventMap matchedEvents)
    {
        return doubleValue;
    }

    public final String toString()
    {
        return Double.toString(doubleValue);
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof RangeValueDouble))
        {
            return false;
        }

        RangeValueDouble other = (RangeValueDouble) obj;
        return other.doubleValue == this.doubleValue;
    }
}
