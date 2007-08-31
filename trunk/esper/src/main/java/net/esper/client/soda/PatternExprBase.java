package net.esper.client.soda;

import java.util.List;
import java.util.ArrayList;

public abstract class PatternExprBase implements PatternExpr
{
    private List<PatternExpr> children;

    protected PatternExprBase()
    {
        children = new ArrayList<PatternExpr>();
    }

    public List<PatternExpr> getChildren()
    {
        return children;
    }

    public void addChild(PatternExpr expression)
    {
        children.add(expression);
    }
}
