package net.esper.filter;

import net.esper.event.EventType;
import net.esper.pattern.MatchedEventMap;

import java.util.Map;

/**
 * Constant value in a list of values following an in-keyword.
 */
public class InSetOfValuesConstant implements FilterSpecParamInValue
{
    private Object constant;

    public InSetOfValuesConstant(Object constant)
    {
        this.constant = constant;
    }

    public final Class validate(Map<String, EventType> taggedEventTypes)
    {
        if (constant == null)
        {
            return null;
        }
        return constant.getClass();
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        return constant;
    }

    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }

        if (!(obj instanceof InSetOfValuesConstant))
        {
            return false;
        }

        InSetOfValuesConstant other = (InSetOfValuesConstant) obj;
        return other.constant.equals(this.constant);
    }
}
