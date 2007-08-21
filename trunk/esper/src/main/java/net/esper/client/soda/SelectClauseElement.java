package net.esper.client.soda;

import net.esper.util.MetaDefItem;

import java.io.Serializable;

public class SelectClauseElement implements Serializable
{
    private Expression expression;
    private String optionalAsName;

    public SelectClauseElement(Expression expression)
    {
        this.expression = expression;
    }

    public SelectClauseElement(Expression expression, String optionalAsName)
    {
        this.expression = expression;
        this.optionalAsName = optionalAsName;
    }

    public Expression getExpression()
    {
        return expression;
    }

    public void setExpression(Expression expression)
    {
        this.expression = expression;
    }

    public String getOptionalAsName()
    {
        return optionalAsName;
    }

    public void setOptionalAsName(String optionalAsName)
    {
        this.optionalAsName = optionalAsName;
    }
}
