package net.esper.client.soda;

import java.io.StringWriter;

public class SubqueryExpression extends ExpressionBase
{
    private EPStatementObjectModel model;

    public SubqueryExpression(EPStatementObjectModel model)
    {
        this.model = model;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write('(');
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
