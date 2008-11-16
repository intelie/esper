package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class TimePeriodExpression extends ExpressionBase
{
    private Expression days;
    private Expression hours;
    private Expression minutes;
    private Expression seconds;
    private Expression milliseconds;

    public TimePeriodExpression(Expression days, Expression hours, Expression minutes, Expression seconds, Expression milliseconds)
    {
        this.days = days;
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
    }

    public Expression getMilliseconds()
    {
        return milliseconds;
    }

    public void setMilliseconds(Expression milliseconds)
    {
        this.milliseconds = milliseconds;
    }

    public Expression getSeconds()
    {
        return seconds;
    }

    public void setSeconds(Expression seconds)
    {
        this.seconds = seconds;
    }

    public Expression getMinutes()
    {
        return minutes;
    }

    public void setMinutes(Expression minutes)
    {
        this.minutes = minutes;
    }

    public Expression getHours()
    {
        return hours;
    }

    public void setHours(Expression hours)
    {
        this.hours = hours;
    }

    public Expression getDays()
    {
        return days;
    }

    public void setDays(Expression days)
    {
        this.days = days;
    }

    public void toEPL(StringWriter writer)
    {
        if (days != null)
        {
            writer.write(" ");
            days.toEPL(writer);
            writer.append(" days");
        }
        if (hours != null)
        {
            writer.write(" ");
            hours.toEPL(writer);
            writer.append(" hours");
        }
        if (minutes != null)
        {
            writer.write(" ");
            minutes.toEPL(writer);
            writer.append(" minutes");
        }
        if (seconds != null)
        {
            writer.write(" ");
            seconds.toEPL(writer);
            writer.append(" seconds");
        }
        if (milliseconds != null)
        {
            writer.write(" ");
            milliseconds.toEPL(writer);
            writer.append(" milliseconds");
        }
    }
}
