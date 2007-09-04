package net.esper.client.soda;

import java.io.StringWriter;

public class StaticMethodExpression extends ExpressionBase
{
    private String className;
    private String method;

    public StaticMethodExpression(String className, String method, Object[] parameters)
    {
        this.className = className;
        this.method = method;
        
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

    public StaticMethodExpression(String className, String method)
    {
        this.className = className;
        this.method = method;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(className);
        writer.write('.');
        writer.write(method);
        writer.write('(');

        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = ", ";
        }

        writer.write(')');
    }

    public String getClassName()
    {
        return className;
    }

    public void setClassName(String className)
    {
        this.className = className;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }
}
