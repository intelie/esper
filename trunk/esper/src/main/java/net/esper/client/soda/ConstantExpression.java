package net.esper.client.soda;

import java.io.StringWriter;

public class ConstantExpression extends ExpressionBase
{
    private Object constant;

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

    public Object getConstant()
    {
        return constant;
    }

    public void setConstant(Object constant)
    {
        this.constant = constant;
    }
}
