package net.esper.client.soda;

import java.io.StringWriter;

public class SubqueryExistsExpression extends ExpressionBase
{
    private EPStatementObjectModel model;

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

    public EPStatementObjectModel getModel()
    {
        return model;
    }

    public void setModel(EPStatementObjectModel model)
    {
        this.model = model;
    }
}
