package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Represents an arithmatic expression.
 */
public class ArithmaticExpression extends ExpressionBase
{
    private String operator;

    /**
     * Ctor.
     * @param operator can be any of '-', '+', '*', '/' or '%' (modulo).
     */
    public ArithmaticExpression(String operator)
    {
        this.operator = operator;
    }

    /**
     * Ctor.
     * @param left the left hand side
     * @param operator can be any of '-', '+', '*', '/' or '%' (modulo).
     * @param right the right hand side 
     */
    public ArithmaticExpression(Expression left, String operator, Expression right)
    {
        this.operator = operator;
        addChild(left);
        addChild(right);
    }

    /**
     * Returns the arithmatic operator.
     * @return operator
     */
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
