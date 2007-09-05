package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Minimum-value per-row expression (not aggregating) determines the minimum value among a set of values.
 */
public class MinRowExpression extends ExpressionBase
{
    /**
     * Ctor - for use to create an expression tree, without child expression.
     * <p>
     * Use add methods to add child expressions to acts upon.
     */
    public MinRowExpression()
    {
    }

    /**
     * Ctor.
     * @param propertyOne the name of the property providing a value to determine the minimum of
     * @param propertyTwo the name of the property providing a value to determine the minimum of
     * @param moreProperties optional additional properties to consider
     */
    public MinRowExpression(String propertyOne, String propertyTwo, String[] moreProperties)
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
     * @param exprOne provides a value to determine the maximum of
     * @param exprTwo provides a value to determine the maximum of
     * @param moreExpressions optional additional values to consider
     */
    public MinRowExpression(Expression exprOne, Expression exprTwo, Expression ...moreExpressions)
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
    public MinRowExpression add(Object object)
    {
        this.getChildren().add(new ConstantExpression(object));
        return this;
    }

    /**
     * Add an expression to include in the computation.
     * @param expression to add
     * @return expression
     */
    public MinRowExpression add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    /**
     * Add a property to include in the computation.
     * @param propertyName is the name of the property
     * @return expression
     */
    public MinRowExpression add(String propertyName)
    {
        this.getChildren().add(new PropertyValueExpression(propertyName));
        return this;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("min(");

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
