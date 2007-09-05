package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Maximum of the (distinct) values returned by an expression.
 */
public class MaxProjectionExpression extends ExpressionBase
{
    private boolean isDistinct;

    /**
     * Ctor - for use to create an expression tree, without inner expression
     * @param isDistinct true if distinct
     */
    public MaxProjectionExpression(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    /**
     * Ctor - adds the expression to project.
     * @param expression returning values to project
     * @param isDistinct true if distinct
     */
    public MaxProjectionExpression(Expression expression, boolean isDistinct)
    {
        this.isDistinct = isDistinct;
        this.getChildren().add(expression);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("max(");
        if (isDistinct)
        {
            writer.write("distinct ");
        }
        this.getChildren().get(0).toEQL(writer);
        writer.write(")");
    }

    /**
     * Returns true if the projection considers distinct values only.
     * @return true if distinct
     */
    public boolean isDistinct()
    {
        return isDistinct;
    }

    /**
     * Set the distinct flag indicating the projection considers distinct values only.
     * @param distinct true for distinct, false for not distinct
     */
    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }
}
