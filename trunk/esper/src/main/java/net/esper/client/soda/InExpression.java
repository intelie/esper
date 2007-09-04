package net.esper.client.soda;

import java.io.StringWriter;

public class InExpression extends ExpressionBase
{
    boolean isNotIn;

    public InExpression(boolean isNotIn)
    {
        this.isNotIn = isNotIn;
    }

    public InExpression(Expression value, boolean isNotIn, Object... parameters)
    {
        this.isNotIn = isNotIn;
        this.getChildren().add(value);
        for (int i = 0; i < parameters.length; i++)
        {
            if (parameters[i] instanceof Expression)
            {
                this.getChildren().add((Expression)parameters[i]);
            }
            else
            {
                this.getChildren().add(new ConstantExpression(parameters[i]));
            }
        }
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
            writer.write(" not in (");
        }
        else
        {
            writer.write(" in (");
        }

        String delimiter = "";
        for (int i = 1; i < this.getChildren().size(); i++)
        {
            writer.write(delimiter);
            this.getChildren().get(i).toEQL(writer);
            delimiter = ", ";
        }
        writer.write(')');
    }
}
