package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;

/**
 * Base expression.
 */
public abstract class ExpressionBase implements Expression
{
    private List<Expression> children;

    /**
     * Ctor.
     */
    protected ExpressionBase()
    {
        children = new ArrayList<Expression>();
    }

    /**
     * Returns the list of sub-expressions to the current expression.
     * @return list of child expressions
     */
    public List<Expression> getChildren()
    {
        return children;
    }

    /**
     * Adds a new child expression to the current expression.
     * @param expression to add
     */
    protected void addChild(Expression expression)
    {
        children.add(expression);
    }
}
