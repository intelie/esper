package net.esper.client.soda;

import java.io.StringWriter;

public class SubqueryInExpression extends ExpressionBase
{
    private EPStatementObjectModel model;

    public SubqueryInExpression(EPStatementObjectModel model)
    {
        this.model = model;
    }

    public SubqueryInExpression(Expression expression, EPStatementObjectModel model)
    {
        this.getChildren().add(expression);
        this.model = model;
    }

    public void toEQL(StringWriter writer)
    {
        this.getChildren().get(0).toEQL(writer);
        writer.write(" in (");
        writer.write(model.toEQL());
        writer.write(')');
    }

    public EPStatementObjectModel getModel()
    {
        return model;
    }

    public void setModel(EPStatementObjectModel model)
    {
        this.model = model;
    }
}
