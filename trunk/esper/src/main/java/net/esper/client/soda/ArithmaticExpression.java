package net.esper.client.soda;

import java.io.StringWriter;

public class ArithmaticExpression extends ExpressionBase
{
    private String operator;

    public ArithmaticExpression(String operator)
    {
        this.operator = operator;
    }

    public ArithmaticExpression(Expression left, String operator, Expression right)
    {
        this.operator = operator;
        addChild(left);
        addChild(right);
    }

    public String getOperator()
    {
        return operator;
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("(");
        String delimiter = "";
        for (Expression child : this.getChildren())
        {
            writer.write(delimiter);
            child.toEQL(writer);
            delimiter = " " + operator + " ";
        }
        writer.write(")");
    }
}
