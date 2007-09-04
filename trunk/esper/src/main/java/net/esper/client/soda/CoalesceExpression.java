package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Represents a coalesce-function which returns the first non-null value in a list of values.
 */
public class CoalesceExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     * <p>
     * Use add methods to add child expressions to acts upon.
     */
    public CoalesceExpression()
    {
    }

    /**
     * Ctor.
     * @param propertyOne the first property in the expression
     * @param propertyTwo the second property in the expression
     * @param moreProperties optional more properties in the expression
     */
    public CoalesceExpression(String propertyOne, String propertyTwo, String ...moreProperties)
    {
        addChild(new PropertyValueExpression(propertyOne));
        addChild(new PropertyValueExpression(propertyTwo));
        for (int i = 0; i < moreProperties.length; i++)
        {
            addChild(new PropertyValueExpression(moreProperties[i]));
        }
    }

    /**
     * Ctor.
     * @param exprOne provides the first value in the expression
     * @param exprTwo provides the second value in the expression
     * @param moreExpressions optional more expressions that are part of the function
     */
    public CoalesceExpression(Expression exprOne, Expression exprTwo, Expression ...moreExpressions)
    {
        addChild(exprOne);
        addChild(exprTwo);
        for (int i = 0; i < moreExpressions.length; i++)
        {
            addChild(moreExpressions[i]);
        }
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("coalesce(");

        String delimiter = "";
        for (Expression expr : this.getChildren())
        {
            writer.write(delimiter);
            expr.toEQL(writer);
            delimiter = ", ";
        }
        writer.write(')');
    }
}
