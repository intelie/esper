package net.esper.filter;

import net.esper.pattern.MatchedEventMap;
import net.esper.event.EventBean;
import net.esper.event.EventType;

import java.util.Map;

/**
 * This class represents a filter parameter containing a reference to another event's property
 * in the event pattern result, for use to describe a filter parameter in a {@link FilterSpec} filter specification.
 */
public final class FilterSpecParamEventProp extends FilterSpecParam
{
    private final String resultEventAsName;
    private final String resultEventProperty;

    /**
     * Constructor.
     * @param propertyName is the event property name
     * @param filterOperator is the type of compare
     * @param resultEventAsName is the name of the result event from which to get a property value to compare
     * @param resultEventProperty is the name of the property to get from the named result event
     * @throws IllegalArgumentException if an operator was supplied that does not take a single constant value
     */
    public FilterSpecParamEventProp(String propertyName, FilterOperator filterOperator, String resultEventAsName,
                                    String resultEventProperty)
        throws IllegalArgumentException
    {
        super(propertyName, filterOperator);
        this.resultEventAsName = resultEventAsName;
        this.resultEventProperty = resultEventProperty;

        if (filterOperator.isRangeOperator())
        {
            throw new IllegalArgumentException("Illegal filter operator " + filterOperator + " supplied to " +
                    "event property filter parameter");
        }
    }

    /**
     * Returns tag for result event.
     * @return tag
     */
    public String getResultEventAsName()
    {
        return resultEventAsName;
    }

    /**
     * Returns the property of the result event.
     * @return property name
     */
    public String getResultEventProperty()
    {
        return resultEventProperty;
    }

    public Class getFilterValueClass(Map<String, EventType> taggedEventTypes)
    {
        EventType type = taggedEventTypes.get(resultEventAsName);
        if (type == null)
        {
            throw new IllegalStateException("Event named '" +
                    '\'' + resultEventAsName + "' not found in event pattern result set");
        }
        return type.getPropertyType(resultEventProperty);
    }    

    public Object getFilterValue(MatchedEventMap matchedEvents)
    {
        EventBean event = matchedEvents.getMatchingEvent(resultEventAsName);
        if (event == null)
        {
            throw new IllegalStateException("Event named '" +
                    '\'' + resultEventAsName + "' not found in event pattern result set");
        }

        Object value = event.get(resultEventProperty);
        return value;
    }

    public final String toString()
    {
        return super.toString() +
                " resultEventAsName=" + resultEventAsName +
                " resultEventProperty=" + resultEventProperty;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof FilterSpecParamEventProp))
        {
            return false;
        }

        FilterSpecParamEventProp other = (FilterSpecParamEventProp) obj;
        if (!super.equals(other))
        {
            return false;
        }

        if ((!this.resultEventAsName.equals(other.resultEventAsName)) ||
            (!this.resultEventProperty.equals(other.resultEventProperty)))
        {
            return false;
        }
        return true;
    }
}
