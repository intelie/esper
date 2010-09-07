package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationSupport;
import com.espertech.esper.epl.agg.AggregationValidationContext;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SupportPluginAggregationMethodThree extends AggregationSupport implements Serializable
{
    private static List<AggregationValidationContext> contexts = new ArrayList<AggregationValidationContext>();
    private int count;

    public static List<AggregationValidationContext> getContexts()
    {
        return contexts;
    }

    public void clear()
    {
        count = 0;
    }

    @Override
    public void validate(AggregationValidationContext validationContext)
    {
        contexts.add(validationContext);
    }

    public void enter(Object value)
    {
        Object[] params = (Object[]) value;
        int lower = (Integer) params[0];
        int upper = (Integer) params[1];
        int val = (Integer) params[2];
        if ((val >= lower) && (val <= upper))
        {
            count++;
        }
    }

    public void leave(Object value)
    {
        Object[] params = (Object[]) value;
        int lower = (Integer) params[0];
        int upper = (Integer) params[1];
        int val = (Integer) params[2];
        if ((val >= lower) && (val <= upper))
        {
            count--;
        }
    }

    public Object getValue()
    {
        return count;
    }

    public Class getValueType()
    {
        return int.class;
    }
}
