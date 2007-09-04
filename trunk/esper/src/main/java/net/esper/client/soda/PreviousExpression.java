package net.esper.client.soda;

import java.io.StringWriter;

public class PreviousExpression extends ExpressionBase
{
    public PreviousExpression()
    {
    }

    public PreviousExpression(Expression expression, String propertyName)
    {
        this.addChild(expression);
        this.addChild(new PropertyValueExpression(propertyName));
    }

    public PreviousExpression(int index, String propertyName)
    {
        this.addChild(new ConstantExpression(index));
        this.addChild(new PropertyValueExpression(propertyName));
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("prev(");
        this.getChildren().get(0).toEQL(writer);
        writer.write(", ");
        this.getChildren().get(1).toEQL(writer);
        writer.write(')');
    }
}
