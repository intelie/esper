package net.esper.client.soda;

import java.io.StringWriter;

public class SumProjectionExpression extends ExpressionBase
{
    private boolean isDistinct;

    public SumProjectionExpression(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    public SumProjectionExpression(Expression inner)
    {
        addChild(inner);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("sum(");
        if (isDistinct)
        {
            writer.write("distinct ");
        }
        this.getChildren().get(0).toEQL(writer);
        writer.write(")");
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
