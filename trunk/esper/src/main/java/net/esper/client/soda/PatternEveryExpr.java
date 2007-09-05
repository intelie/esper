package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Pattern 'every' expression that controls the lifecycle of pattern sub-expressions. 
 */
public class PatternEveryExpr extends PatternExprBase
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternEveryExpr()
    {
    }

    /**
     * Ctor.
     * @param inner is the pattern expression to control lifecycle on
     */
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
