package net.esper.filter;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.util.JavaClassHelper;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;

/**
 * An event property as a filter parameter representing a range.
 */
public class RangeValueEventProp implements FilterSpecParamRangeValue
{
    private final String resultEventAsName;
    private final String resultEventProperty;

    /**
     * Ctor.
     * @param resultEventAsName is the event tag
     * @param resultEventProperty is the event property name
     */
    public RangeValueEventProp(String resultEventAsName, String resultEventProperty)
    {
        this.resultEventAsName = resultEventAsName;
        this.resultEventProperty = resultEventProperty;
    }

    public final Double getFilterValue(MatchedEventMap matchedEvents)
    {
        EventBean event = matchedEvents.getMatchingEvent(resultEventAsName);
        if (event == null)
        {
            throw new IllegalStateException("Matching event named " +
                    '\'' + resultEventAsName + "' not found in event result set");
        }

        Number value = (Number) event.get(resultEventProperty);
        if (value == null)
        {
            return null;
        }        
        return value.doubleValue();
    }

    public String getResultEventAsName()
    {
        return resultEventAsName;
    }

    public String getResultEventProperty()
    {
        return resultEventProperty;
    }

    public final String toString()
    {
        return "resultEventProp=" + resultEventAsName + '.' + resultEventProperty;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof RangeValueEventProp))
        {
            return false;
        }

        RangeValueEventProp other = (RangeValueEventProp) obj;
        if ( (other.resultEventAsName.equals(this.resultEventAsName)) &&
             (other.resultEventProperty.equals(this.resultEventProperty)))
        {
            return true;
        }

        return false;
    }
}
