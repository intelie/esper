package net.esper.client.soda;

import java.util.ArrayList;
import java.util.List;

public abstract class ExpressionBase implements Expression
{
    private List<Expression> children;

    protected ExpressionBase()
    {
        children = new ArrayList<Expression>();
    }

    public List<Expression> getChildren()
    {
        return children;
    }

    protected void addChild(Expression expression)
    {
        children.add(expression);
    }
}
