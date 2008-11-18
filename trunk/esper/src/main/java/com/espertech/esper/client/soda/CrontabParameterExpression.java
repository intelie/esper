package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class CrontabParameterExpression extends ExpressionBase
{
    private ScheduleItemType type;
  
    public CrontabParameterExpression(ScheduleItemType type)
    {
        this.type = type;
    }

    public void toEPL(StringWriter writer)
    {
        if (!this.getChildren().isEmpty())
        {
            this.getChildren().get(0).toEPL(writer);
            writer.append(' ');
        }
        writer.write(type.getSyntax());
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
        WILDCARD("*"),

        /**
         * Last day of week or month.
         */
        LASTDAY("last"),

        /**
         * Weekday (nearest to a date)
         */
        WEEKDAY("weekday"),

        /**
         * Last weekday in a month
         */
        LASTWEEKDAY("lastweekday");

        private String syntax;

        private ScheduleItemType(String s)
        {
            this.syntax = s;
        }

        public String getSyntax()
        {
            return syntax;
        }
    }
}
