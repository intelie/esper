package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Negates the contained-within subexpression.
 * <p>
 * Has a single child expression to be negated.
 */
public class NotExpression extends ExpressionBase
{
    /**
     * Ctor.
     * @param inner is the expression to negate
     */
    public NotExpression(Expression inner)
    {
        this.addChild(inner);
    }

    /**
     * Ctor - for use to create an expression tree, without child expression.
     */
    public NotExpression()
    {        
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("not ");
        this.getChildren().get(0).toEQL(writer);
    }
}
