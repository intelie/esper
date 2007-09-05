package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Coalesce-function which returns the first non-null value in a list of values.
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

    /**
     * Add a constant to include in the computation.
     * @param object constant to add
     * @return expression
     */
    public CoalesceExpression add(Object object)
    {
        this.getChildren().add(new ConstantExpression(object));
        return this;
    }

    /**
     * Add an expression to include in the computation.
     * @param expression to add
     * @return expression
     */
    public CoalesceExpression add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    /**
     * Add a property to include in the computation.
     * @param propertyName is the name of the property
     * @return expression
     */
    public CoalesceExpression add(String propertyName)
    {
        this.getChildren().add(new PropertyValueExpression(propertyName));
        return this;
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
