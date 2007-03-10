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

    /**
     * Ctor.
     * @param constant is the constant value
     */
    public InSetOfValuesConstant(Object constant)
    {
        this.constant = constant;
    }

    public final Object getFilterValue(MatchedEventMap matchedEvents)
    {
        return constant;
    }

    /**
     * Returns the constant value.
     * @return constant
     */
    public Object getConstant()
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
