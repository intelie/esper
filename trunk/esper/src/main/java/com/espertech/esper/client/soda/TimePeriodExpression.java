package com.espertech.esper.client.soda;

import java.io.StringWriter;

/**
 * Represent an expression 
 */
public class TimePeriodExpression extends ExpressionBase
{
    private boolean hasDays;
    private boolean hasHours;
    private boolean hasMinutes;
    private boolean hasSeconds;
    private boolean hasMilliseconds;

    public TimePeriodExpression(boolean hasDays, boolean hasHours, boolean hasMinutes, boolean hasSeconds, boolean hasMilliseconds)
    {
        this.hasDays = hasDays;
        this.hasHours = hasHours;
        this.hasMinutes = hasMinutes;
        this.hasSeconds = hasSeconds;
        this.hasMilliseconds = hasMilliseconds;
    }

    public TimePeriodExpression(Expression daysExpr, Expression hoursExpr, Expression minutesExpr, Expression secondsExpr, Expression millisecondsExpr)
    {
        if (daysExpr != null)
        {
            hasDays = true;
            this.addChild(daysExpr);
        }
        if (hoursExpr != null)
        {
            hasHours = true;
            this.addChild(hoursExpr);
        }
        if (minutesExpr != null)
        {
            hasMinutes = true;
            this.addChild(minutesExpr);
        }
        if (secondsExpr != null)
        {
            hasSeconds = true;
            this.addChild(secondsExpr);
        }
        if (millisecondsExpr != null)
        {
            hasMilliseconds = true;
            this.addChild(millisecondsExpr);
        }
    }

    public boolean isHasDays()
    {
        return hasDays;
    }

    public void setHasDays(boolean hasDays)
    {
        this.hasDays = hasDays;
    }

    public boolean isHasHours()
    {
        return hasHours;
    }

    public void setHasHours(boolean hasHours)
    {
        this.hasHours = hasHours;
    }

    public boolean isHasMinutes()
    {
        return hasMinutes;
    }

    public void setHasMinutes(boolean hasMinutes)
    {
        this.hasMinutes = hasMinutes;
    }

    public boolean isHasSeconds()
    {
        return hasSeconds;
    }

    public void setHasSeconds(boolean hasSeconds)
    {
        this.hasSeconds = hasSeconds;
    }

    public boolean isHasMilliseconds()
    {
        return hasMilliseconds;
    }

    public void setHasMilliseconds(boolean hasMilliseconds)
    {
        this.hasMilliseconds = hasMilliseconds;
    }

    public void toEPL(StringWriter writer)
    {
        String delimiter = "";
        int countExpr = 0;
        if (hasDays)
        {
            this.getChildren().get(countExpr).toEPL(writer);
            writer.append(" days");
            delimiter = " ";
            countExpr++;
        }
        if (hasHours)
        {
            writer.write(delimiter);
            this.getChildren().get(countExpr).toEPL(writer);
            writer.append(" hours");
            delimiter = " ";
            countExpr++;
        }
        if (hasMinutes)
        {
            writer.write(delimiter);
            this.getChildren().get(countExpr).toEPL(writer);
            writer.append(" minutes");
            delimiter = " ";
            countExpr++;
        }
        if (hasSeconds)
        {
            writer.write(delimiter);
            this.getChildren().get(countExpr).toEPL(writer);
            writer.append(" seconds");
            delimiter = " ";
            countExpr++;
        }
        if (hasMilliseconds)
        {
            writer.write(delimiter);
            this.getChildren().get(countExpr).toEPL(writer);
            writer.append(" milliseconds");
        }
    }
}