package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class CrontabRangeExpression extends ExpressionBase
{
    public CrontabRangeExpression()
    {
    }

    public CrontabRangeExpression(Expression lowerBounds, Expression upperBounds)
    {
        this.getChildren().add(lowerBounds);
        this.getChildren().add(upperBounds);
    }

    public void toEPL(StringWriter writer)
    {
        this.getChildren().get(0).toEPL(writer);
        writer.append(":");
        this.getChildren().get(1).toEPL(writer);
    }
}
