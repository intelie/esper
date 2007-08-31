package net.esper.client.soda;

import java.io.StringWriter;

public class CountProjectionExpression extends ExpressionBase
{
    private boolean isDistinct;

    public CountProjectionExpression(boolean isDistinct)
    {
        this.isDistinct = isDistinct;
    }

    public CountProjectionExpression(Expression expression, boolean isDistinct)
    {
        this.isDistinct = isDistinct;
        this.getChildren().add(expression);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("count(");
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
