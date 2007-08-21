package net.esper.client.soda;

public class RelationalOpExpression extends ExpressionBase
{
    private String operator;

    public RelationalOpExpression(Expression left, String operator, Expression right)
    {
        this.operator = operator;
    }

    public String getOperator()
    {
        return operator;
    }
}
