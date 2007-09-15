package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Current timestamp supplies the current engine time in an expression.
 */
public class CurrentTimestampExpression extends ExpressionBase
{
    /**
     * Ctor.
     */
    public CurrentTimestampExpression()
    {
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("current_timestamp()");
    }
}
