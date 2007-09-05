package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;

/**
 * A single entry in an order-by clause consisting of an expression and order ascending or descending flag.
 */
public class OrderByElement implements Serializable
{
    private Expression expression;
    private boolean isDescending;

    /**
     * Ctor.
     * @param expression is the expression to order by
     * @param descending true for descending sort, false for ascending sort
     */
    public OrderByElement(Expression expression, boolean descending)
    {
        this.expression = expression;
        isDescending = descending;
    }

    /**
     * Returns the order-by value expression.
     * @return expression
     */
    public Expression getExpression()
    {
        return expression;
    }

    /**
     * Sets the order-by value expression.
     * @param expression provides order-by values
     */
    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    /**
     * Returns true for descending sorts for this column, false for ascending sort.
     * @return true for descending sort
     */
    public boolean isDescending()
    {
        return isDescending;
    }

    /**
     * Set to true for descending sorts for this column, false for ascending sort.
     * @param descending true for descending sort
     */
    public void setDescending(boolean descending)
    {
        isDescending = descending;
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        expression.toEQL(writer);
        if (isDescending)
        {
            writer.write(" desc");
        }
    }
}
