package net.esper.client.soda;

import java.io.StringWriter;

/**
 * In-expression for a set of values returned by a subquery.
 */
public class SubqueryInExpression extends ExpressionBase
{
    private boolean isNotIn;
    private EPStatementObjectModel model;

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * @param model is the subquery statement object model
     * @param isNotIn is true for not-in
     */
    public SubqueryInExpression(EPStatementObjectModel model, boolean isNotIn)
    {
        this.model = model;
        this.isNotIn = isNotIn;
    }

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * @param expression is the expression providing the value to match
     * @param model is the subquery statement object model
     * @param isNotIn is true for not-in
     */
    public SubqueryInExpression(Expression expression, EPStatementObjectModel model, boolean isNotIn)
    {
        this.getChildren().add(expression);
        this.model = model;
        this.isNotIn = isNotIn;
    }

    /**
     * Returns true for not-in, or false for in-subquery.
     * @return true for not-in
     */
    public boolean isNotIn()
    {
        return isNotIn;
    }

    /**
     * Set to true for not-in, or false for in-subquery.
     * @param notIn true for not-in
     */
    public void setNotIn(boolean notIn)
    {
        isNotIn = notIn;
    }

    public void toEQL(StringWriter writer)
    {
        this.getChildren().get(0).toEQL(writer);
        if (isNotIn)
        {
            writer.write("not in (");
        }
        else
        {
            writer.write("in (");
        }
        writer.write(model.toEQL());
        writer.write(')');
    }

    /**
     * Returns the subquery statement object model.
     * @return subquery model
     */
    public EPStatementObjectModel getModel()
    {
        return model;
    }

    /**
     * Sets the subquery statement object model.
     * @param model is the subquery model to set
     */
    public void setModel(EPStatementObjectModel model)
    {
        this.model = model;
    }
}
