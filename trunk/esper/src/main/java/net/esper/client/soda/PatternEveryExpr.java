package net.esper.client.soda;

import java.io.StringWriter;

public class PatternEveryExpr extends PatternExprBase
{
    public PatternEveryExpr()
    {
    }

    public PatternEveryExpr(PatternExpr inner)
    {
        addChild(inner);
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("every (");
        this.getChildren().get(0).toEQL(writer);
        writer.write(')');
    }
}
