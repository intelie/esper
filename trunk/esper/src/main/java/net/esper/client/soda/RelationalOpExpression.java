package net.esper.client.soda;

import java.io.StringWriter;

public class RelationalOpExpression extends ExpressionBase
{
    private String operator;

    public RelationalOpExpression(String operator)
    {
        this.operator = operator.trim();
    }

    public RelationalOpExpression(Expression left, String operator, Expression right)
    {
        this.operator = operator.trim();
        addChild(left);
        addChild(right);
    }

    public String getOperator()
    {
        return operator;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write('(');
        this.getChildren().get(0).toEQL(writer);
        writer.write(' ');
        writer.write(operator);
        writer.write(' ');
        this.getChildren().get(1).toEQL(writer);
        writer.write(')');
    }
}
