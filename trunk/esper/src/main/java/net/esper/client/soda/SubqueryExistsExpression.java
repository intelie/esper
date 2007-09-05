package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Exists-expression for a set of values returned by a subquery.
 */
public class SubqueryExistsExpression extends ExpressionBase
{
    private EPStatementObjectModel model;

    /**
     * Ctor - for use to create an expression tree, without child expression.
     * @param model is the subquery statement object model
     */
    public SubqueryExistsExpression(EPStatementObjectModel model)
    {
        this.model = model;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("exists (");
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
