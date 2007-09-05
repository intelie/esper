package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Not-expression for negating a pattern sub-expression for use in pattern expressions. 
 */
public class PatternNotExpr extends PatternExprBase
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternNotExpr()
    {
    }

    /**
     * Ctor.
     * @param inner is the pattern expression to negate
     */
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
