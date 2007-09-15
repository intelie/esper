package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Property-exists checks if a dynamic property exists.
 */
public class PropertyExistsExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     */
    public PropertyExistsExpression()
    {
    }

    /**
     * Ctor.
     * @param propertyName is the name of the property to check existence
     */
    public PropertyExistsExpression(String propertyName)
    {
        this.getChildren().add(Expressions.getPropExpr(propertyName));
    }

    /**
     * Renders the clause in textual representation.
     * @param writer to output to
     */
    public void toEQL(StringWriter writer)
    {
        writer.write("exists(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(")");
    }
}
