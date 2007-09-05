package net.esper.client.soda;

import java.io.StringWriter;

/**
 * Constant value returns a fixed value for use in expressions.
 */
public class ConstantExpression extends ExpressionBase
{
    private Object constant;

    /**
     * Ctor.
     * @param constant is the constant value, or null to represent the null value
     */
    public ConstantExpression(Object constant)
    {
        this.constant = constant;
    }

    public void toEQL(StringWriter writer)
    {
        if (constant == null)
        {
            writer.write("null");
            return;
        }

        if ((constant instanceof String) ||
            (constant instanceof Character))
        {
            writer.write('\'');
            writer.write(constant.toString());
            writer.write('\'');
        }
        else
        {
            writer.write(constant.toString());
        }        
    }

    /**
     * Returns the constant value that the expression represents.
     * @return value of constant
     */
    public Object getConstant()
    {
        return constant;
    }

    /**
     * Sets the constant value that the expression represents.
     * @param constant is the value, or null to indicate the null value
     */
    public void setConstant(Object constant)
    {
        this.constant = constant;
    }
}
