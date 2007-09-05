package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Concatenation expression that concatenates the result of child expressions to the expression.
 */
public class ConcatExpression extends ExpressionBase
{
    /**
     * Add a constant to include in the computation.
     * @param object constant to add
     * @return expression
     */
    public ConcatExpression add(Object object)
    {
        this.getChildren().add(new ConstantExpression(object));
        return this;
    }

    /**
     * Add an expression to include in the computation.
     * @param expression to add
     * @return expression
     */
    public ConcatExpression add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    /**
     * Add a property to include in the computation.
     * @param propertyName is the name of the property
     * @return expression
     */
    public ConcatExpression add(String propertyName)
    {
        this.getChildren().add(new PropertyValueExpression(propertyName));
        return this;
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = " || ";
        }
    }
}
