package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Projection representing a "count(*)" aggregation function.
 */
public class CountStarProjectionExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without inner expression.
     */
    public CountStarProjectionExpression()
    {
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("count(*)");
    }
}
