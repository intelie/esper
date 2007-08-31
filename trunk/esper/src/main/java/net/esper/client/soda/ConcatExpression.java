package net.esper.client.soda;

import java.io.StringWriter;

public class ConcatExpression extends ExpressionBase
{
    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = " || ";
        }
    }
}
