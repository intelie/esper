package net.esper.client.soda;

import java.util.List;
import java.util.ArrayList;

/**
 * Abstract base class for all pattern expressions.
 */
public abstract class PatternExprBase implements PatternExpr
{
    private List<PatternExpr> children;

    /**
     * Ctor.
     */
    protected PatternExprBase()
    {
        children = new ArrayList<PatternExpr>();
    }

    public List<PatternExpr> getChildren()
    {
        return children;
    }

    /**
     * Adds a sub-expression to the pattern expression.
     * @param expression to add
     */
    protected void addChild(PatternExpr expression)
    {
        children.add(expression);
    }
}
