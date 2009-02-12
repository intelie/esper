package com.espertech.esper.support.epl;

import com.espertech.esper.epl.agg.AggregationSupport;

import java.io.Serializable;

public class SupportPluginAggregationMethodThree extends AggregationSupport implements Serializable
{
    private static Class[] childNodeType;
    private static boolean[] isConstantValue;
    private static Object[] constantValue;
    
    private int count;

    public static Class[] getChildNodeType()
    {
        return childNodeType;
    }

    public static boolean[] getIsConstantValue()
    {
        return isConstantValue;
    }

    public static Object[] getConstantValue()
    {
        return constantValue;
    }

    public void clear()
    {
        count = 0;
    }

    public void validate(Class childNodeType)
    {
    }

    public void validateMultiParameter(Class[] childNodeType, boolean[] isConstantValue, Object[] constantValue)
    {
        super.validateMultiParameter(childNodeType, isConstantValue, constantValue);
        this.childNodeType = childNodeType;
        this.isConstantValue = isConstantValue;
        this.constantValue = constantValue;
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
