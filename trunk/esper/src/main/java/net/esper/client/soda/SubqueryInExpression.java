package net.esper.client.soda;

import java.io.StringWriter;

public class SubqueryInExpression extends ExpressionBase
{
    private boolean isNotIn;
    private EPStatementObjectModel model;

    public SubqueryInExpression(EPStatementObjectModel model, boolean isNotIn)
    {
        this.model = model;
        this.isNotIn = isNotIn;
    }

    public SubqueryInExpression(Expression expression, EPStatementObjectModel model, boolean isNotIn)
    {
        this.getChildren().add(expression);
        this.model = model;
        this.isNotIn = isNotIn;
    }

    public boolean isNotIn()
    {
        return isNotIn;
    }

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

    public EPStatementObjectModel getModel()
    {
        return model;
    }

    public void setModel(EPStatementObjectModel model)
    {
        this.model = model;
    }
}
