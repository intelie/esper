package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Expression representing the prior function.
 */
public class PriorExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     */
    public PriorExpression()
    {
    }

    /**
     * Ctor.
     * @param index is the index of the prior event
     * @param propertyName is the property to return
     */
    public PriorExpression(int index, String propertyName)
    {
        this.addChild(new ConstantExpression(index));
        this.addChild(new PropertyValueExpression(propertyName));
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("prior(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(", ");
        this.getChildren().get(1).toEQL(writer);
        writer.write(')');
    }
}
