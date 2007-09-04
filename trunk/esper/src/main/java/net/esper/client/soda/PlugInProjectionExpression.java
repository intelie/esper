package net.esper.client.soda;

import net.esper.type.BitWiseOpEnum;

import java.io.StringWriter;

public class PlugInProjectionExpression extends ExpressionBase
{
    private String functionName;
    private boolean isDistinct;

    public PlugInProjectionExpression(String functionName, boolean isDistinct)
    {
        this.functionName = functionName;
        this.isDistinct = isDistinct;
    }

    public PlugInProjectionExpression(String functionName, boolean isDistinct, Expression expression)
    {
        this.functionName = functionName;
        this.isDistinct = isDistinct;
        this.getChildren().add(expression);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write(functionName);
        writer.write('(');
        if (isDistinct)
        {
            writer.write("distinct ");
        }
        if (this.getChildren().size() > 0)
        {
            this.getChildren().get(0).toEQL(writer);
        }
        writer.write(")");
    }

    public String getFunctionName()
    {
        return functionName;
    }

    public void setFunctionName(String functionName)
    {
        this.functionName = functionName;
    }

    public boolean isDistinct()
    {
        return isDistinct;
    }

    public void setDistinct(boolean distinct)
    {
        isDistinct = distinct;
    }
}
