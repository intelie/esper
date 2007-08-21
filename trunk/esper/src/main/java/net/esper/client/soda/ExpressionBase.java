package net.esper.client.soda;

import java.util.List;

public abstract class ExpressionBase implements Expression
{
    public List<Expression> getChildren()
    {
        return null;
    }
}
