package net.esper.filter;

import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;

/**
 * Event property value in a list of values following an in-keyword.
 */
public class InSetOfValuesEventProp implements FilterSpecParamInValue
{
    private final String resultEventAsName;
    private final String resultEventProperty;

    /**
     * Ctor.
     * @param resultEventAsName is the event tag
     * @param resultEventProperty is the event property name
     */
    public InSetOfValuesEventProp(String resultEventAsName, String resultEventProperty)
    {
        this.resultEventAsName = resultEventAsName;
        this.resultEventProperty = resultEventProperty;
    }

    public final Class validate(Map<String, EventType> taggedEventTypes)
    {
        EventType type = taggedEventTypes.get(resultEventAsName);
        if (type == null)
        {
            throw new IllegalStateException("Matching event type named " +
                    '\'' + resultEventAsName + "' not found in event result set");
        }

        Class propertyClass = type.getPropertyType(resultEventProperty);
        if (propertyClass == null)
        {
            throw new IllegalStateException("Property " + resultEventProperty + " of event type " +
                    '\'' + resultEventAsName + "' not found");
        }
        return propertyClass;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        EventBean event = matchedEvents.getMatchingEvent(resultEventAsName);
        if (event == null)
        {
            throw new IllegalStateException("Matching event named " +
                    '\'' + resultEventAsName + "' not found in event result set");
        }

        Object value = event.get(resultEventProperty);
        if (value == null)
        {
            throw new IllegalStateException("Event property named " +
                    '\'' + resultEventAsName + '.' + resultEventProperty + "' returned null value");
        }
        return value;
    }

    /**
     * Returns the tag used for the event property.
     * @return tag
     */
    public String getResultEventAsName()
    {
        return resultEventAsName;
    }

    /**
     * Returns the event property name.
     * @return property name
     */
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

        if (!(obj instanceof InSetOfValuesEventProp))
        {
            return false;
        }

        InSetOfValuesEventProp other = (InSetOfValuesEventProp) obj;
        if ( (other.resultEventAsName.equals(this.resultEventAsName)) &&
             (other.resultEventProperty.equals(this.resultEventProperty)))
        {
            return true;
        }

        return false;
    }
}
