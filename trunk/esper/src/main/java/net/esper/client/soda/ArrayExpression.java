package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Represents an array expression in a syntax of the form {element 1, element 2, ... element n}.
 */
public class ArrayExpression extends ExpressionBase
{
    /**
     * Add a property to the expression.
     * @param property to add
     * @return expression
     */
    public ArrayExpression add(String property)
    {
        this.getChildren().add(new PropertyValueExpression(property));
        return this;
    }

    /**
     * Add a constant to the expression.
     * @param object constant to add
     * @return expression
     */
    public ArrayExpression add(Object object)
    {
        this.getChildren().add(new ConstantExpression(object));
        return this;
    }

    /**
     * Add an expression representing an array element to the expression.
     * @param expression to add
     * @return expression
     */
    public ArrayExpression add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("{");
        boolean isFirst = true;
        for (Expression child : this.getChildren())
        {
            if (!isFirst)
            {
                writer.write(", ");
            }
            child.toEQL(writer);
            isFirst = false;
        }
        writer.write("}");
    }
}
