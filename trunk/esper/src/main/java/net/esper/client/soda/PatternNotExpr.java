package net.esper.client.soda;

import java.io.StringWriter;

public class PatternNotExpr extends PatternExprBase
{
    public PatternNotExpr()
    {
    }

    public PatternNotExpr(PatternExpr inner)
    {
        this.getChildren().add(inner);    
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("not ");
        this.getChildren().get(0).toEQL(writer);    
    }
}
