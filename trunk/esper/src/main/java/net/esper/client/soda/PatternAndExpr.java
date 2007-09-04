package net.esper.client.soda;

import java.io.StringWriter;

public class PatternAndExpr extends PatternExprBase
{
    public PatternAndExpr()
    {
    }

    public PatternAndExpr add(PatternExpr expr)
    {
        this.getChildren().add(expr);
        return this;
    }

    public PatternAndExpr(PatternExpr first, PatternExpr second, PatternExpr ...patternExprs)
    {
        this.addChild(first);
        this.addChild(second);
        for (int i = 0; i < patternExprs.length; i++)
        {
            this.addChild(patternExprs[i]);
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
            delimiter = " and ";
        }
    }
}
