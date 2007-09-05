package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Logical OR for use in pattern expressions.
 */
public class PatternOrExpr extends PatternExprBase
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternOrExpr()
    {
    }

    /**
     * Ctor.
     * @param first a first pattern expression in the OR relationship
     * @param second a second pattern expression in the OR relationship
     * @param patternExprs further optional pattern expressions in the OR relationship
     */
    public PatternOrExpr(PatternExpr first, PatternExpr second, PatternExpr ...patternExprs)
    {
        this.addChild(first);
        this.addChild(second);
        for (int i = 0; i < patternExprs.length; i++)
        {
            this.addChild(patternExprs[i]);
        }
    }

    /**
     * Adds a pattern expression to the OR relationship between patterns.
     * @param expr to add
     * @return pattern expression
     */
    public PatternOrExpr add(PatternExpr expr)
    {
        this.getChildren().add(expr);
        return this;
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
