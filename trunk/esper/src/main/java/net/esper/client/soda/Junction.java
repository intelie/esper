package net.esper.client.soda;

public abstract class Junction extends ExpressionBase
{
    public Junction add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }
}
