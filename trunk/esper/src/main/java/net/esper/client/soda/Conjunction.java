package net.esper.client.soda;

import java.io.StringWriter;

public class Conjunction extends Junction
{
    public Conjunction()
    {
    }

    public Conjunction(Expression[] expressions)
    {
        for (int i = 0; i < expressions.length; i++)
        {
            addChild(expressions[i]);
        }
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = " and ";
        }
    }    
}
