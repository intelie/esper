package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Previous function for obtaining property values of previous events.
 */
public class PreviousExpression extends ExpressionBase
{
    /**
     * Ctor.
     */
    public PreviousExpression()
    {
    }

    /**
     * Ctor.
     * @param expression provides the index to use
     * @param propertyName is the name of the property to return the value for
     */
    public PreviousExpression(Expression expression, String propertyName)
    {
        this.addChild(expression);
        this.addChild(new PropertyValueExpression(propertyName));
    }

    /**
     * Ctor.
     * @param index provides the index
     * @param propertyName is the name of the property to return the value for
     */
    public PreviousExpression(int index, String propertyName)
    {
        this.addChild(new ConstantExpression(index));
        this.addChild(new PropertyValueExpression(propertyName));
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("prev(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(", ");
        this.getChildren().get(1).toEQL(writer);
        writer.write(')');
    }
}
