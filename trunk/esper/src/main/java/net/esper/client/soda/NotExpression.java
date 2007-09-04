package net.esper.client.soda;

import java.io.StringWriter;

public class NotExpression extends ExpressionBase
{
    public NotExpression(Expression inner)
    {
        this.addChild(inner);
    }

    public NotExpression()
    {        
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("not ");
        this.getChildren().get(0).toEQL(writer);
    }
}
