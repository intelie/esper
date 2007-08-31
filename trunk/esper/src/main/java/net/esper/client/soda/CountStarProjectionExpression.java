package net.esper.client.soda;

import java.io.StringWriter;

public class CountStarProjectionExpression extends ExpressionBase
{
    public CountStarProjectionExpression()
    {
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("count(*)");
    }
}
