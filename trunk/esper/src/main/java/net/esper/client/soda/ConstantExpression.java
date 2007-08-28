package net.esper.client.soda;

public class ConstantExpression extends ExpressionBase
{
    private Object constant;

    public ConstantExpression(Object constant)
    {
        this.constant = constant;
    }
}
