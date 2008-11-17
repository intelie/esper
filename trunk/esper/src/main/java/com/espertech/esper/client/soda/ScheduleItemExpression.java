package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class ScheduleItemExpression extends ExpressionBase
{
    private ScheduleItemType type;
  
    public ScheduleItemExpression(ScheduleItemType type)
    {
        this.type = type;
    }

    public void toEPL(StringWriter writer)
    {
        if (type == ScheduleItemType.WILDCARD)
        {
            writer.write("*");
        }
    }

    public ScheduleItemType getType()
    {
        return type;
    }

    public void setType(ScheduleItemType type)
    {
        this.type = type;
    }

    public enum ScheduleItemType
    {
       WILDCARD
    }
}
