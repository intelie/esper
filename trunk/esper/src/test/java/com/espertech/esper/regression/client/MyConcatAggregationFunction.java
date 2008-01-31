package com.espertech.esper.regression.client;

import com.espertech.esper.eql.agg.AggregationSupport;

import java.io.Serializable;

public class MyConcatAggregationFunction extends AggregationSupport implements Serializable
{
    private final static char DELIMITER = ' ';
    private StringBuilder builder;
    private String delimiter;

    public MyConcatAggregationFunction()
    {
        super();
        builder = new StringBuilder();
        delimiter = "";
    }

    public void validate(Class childNodeType)
    {
        // No need to check the expression node type
    }

    public void clear()
    {
        builder = new StringBuilder();
    }

    public void enter(Object value)
    {
        if (value != null)
        {
            builder.append(delimiter);
            builder.append(value.toString());
            delimiter = String.valueOf(DELIMITER);
        }
    }

    public void leave(Object value)
    {
        if (value != null)
        {
            builder.delete(0, value.toString().length() + 1);
        }
    }

    public Object getValue()
    {
        return builder.toString();
    }

    public Class getValueType()
    {
        return String.class;
    }

}
