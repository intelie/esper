package com.espertech.esper.client.soda;

import java.io.StringWriter;

public class CrontabParameterSetExpression extends ExpressionBase
{
    public CrontabParameterSetExpression()
    {
    }

    public void toEPL(StringWriter writer)
    {
        String delimiter = "";
        writer.write("[");
        for (Expression expr : this.getChildren())
        {
            writer.append(delimiter);
            expr.toEPL(writer);
            delimiter = ", ";
        }
        writer.write("]");
    }
}
