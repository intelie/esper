package net.esper.client.soda;

import java.io.StringWriter;

public class PatternOrExpr extends PatternExprBase
{
    public PatternOrExpr()
    {
    }

    public PatternOrExpr add(PatternExpr expr)
    {
        this.getChildren().add(expr);
        return this;
    }

    public PatternOrExpr(PatternExpr first, PatternExpr second, PatternExpr ...more)
    {
        this.addChild(first);
        this.addChild(second);
        for (int i = 0; i < more.length; i++)
        {
            this.addChild(more[i]);
        }
    }

    public void toEQL(StringWriter writer)
    {
        String delimiter = "";
        for (PatternExpr child : this.getChildren())
        {
            writer.write(delimiter);
            writer.write('(');
            child.toEQL(writer);
            writer.write(')');
            delimiter = " or ";
        }
    }
}
