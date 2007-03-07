package net.esper.filter;

import net.esper.event.EventBean;
import net.esper.pattern.MatchedEventMap;
import net.esper.util.JavaClassHelper;

/**
 * Event property value in a list of values following an in-keyword.
 */
public class InSetOfValuesEventProp implements FilterSpecParamInValue
{
    private final String resultEventAsName;
    private final String resultEventProperty;
    private final boolean isMustCoerce;
    private final Class coercionType;

    /**
     * Ctor.
     * @param resultEventAsName is the event tag
     * @param resultEventProperty is the event property name
     */
    public InSetOfValuesEventProp(String resultEventAsName, String resultEventProperty, boolean isMustCoerce, Class coercionType)
    {
        this.resultEventAsName = resultEventAsName;
        this.resultEventProperty = resultEventProperty;
        this.coercionType = coercionType;
        this.isMustCoerce = isMustCoerce;
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
        // Coerce if necessary
        if (isMustCoerce)
        {
            value = JavaClassHelper.coerceBoxed((Number) value, coercionType);
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
