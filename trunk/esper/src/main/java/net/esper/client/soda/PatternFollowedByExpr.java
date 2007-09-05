package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Followed-by for use in pattern expressions. 
 */
public class PatternFollowedByExpr extends PatternExprBase
{
    /**
     * Ctor - for use to create a pattern expression tree, without pattern child expression.
     */
    public PatternFollowedByExpr()
    {
    }

    /**
     * Ctor.
     * @param first a first pattern expression in the followed-by relationship
     * @param second a second pattern expression in the followed-by relationship
     * @param patternExprs further optional pattern expressions in the followed-by relationship
     */
    public PatternFollowedByExpr(PatternExpr first, PatternExpr second, PatternExpr ...patternExprs)
    {
        this.addChild(first);
        this.addChild(second);
        for (int i = 0; i < patternExprs.length; i++)
        {
            this.addChild(patternExprs[i]);
        }
    }

    /**
     * Adds a pattern expression to the followed-by relationship between patterns.
     * @param expr to add
     * @return pattern expression
     */
    public PatternFollowedByExpr add(PatternExpr expr)
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
            delimiter = " -> ";
        }
    }
}
