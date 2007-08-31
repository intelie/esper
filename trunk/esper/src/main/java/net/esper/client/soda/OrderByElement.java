package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

public class OrderByElement implements Serializable
{
    private Expression expression;
    private boolean isDescending;

    public OrderByElement(Expression expression, boolean descending)
    {
        this.expression = expression;
        isDescending = descending;
    }

    public Expression getExpression()
    {
        return expression;
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public boolean isDescending()
    {
        return isDescending;
    }

    public void setDescending(boolean descending)
    {
        isDescending = descending;
    }

    public void toEQL(StringWriter writer)
    {
        expression.toEQL(writer);
        if (isDescending)
        {
            writer.write(" desc");
        }
    }
}
