package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Count of (distinct) rows, equivalent to "count(*)"
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
