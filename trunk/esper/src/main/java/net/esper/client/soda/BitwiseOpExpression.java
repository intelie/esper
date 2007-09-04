package net.esper.client.soda;

import net.esper.type.BitWiseOpEnum;

import java.io.StringWriter;

public class BitwiseOpExpression extends ExpressionBase
{
    private BitWiseOpEnum binaryOp;

    public BitwiseOpExpression(BitWiseOpEnum binaryOp)
    {
        this.binaryOp = binaryOp;
    }

    /**
     * Add a property to the expression.
     * @param property to add
     * @return expression
     */
    public BitwiseOpExpression add(String property)
    {
        this.getChildren().add(new PropertyValueExpression(property));
        return this;
    }

    public BitwiseOpExpression add(Object object)
    {
        this.getChildren().add(new ConstantExpression(object));
        return this;
    }

    public BitwiseOpExpression add(Expression expression)
    {
        this.getChildren().add(expression);
        return this;
    }

    public void toEQL(StringWriter writer)
    {
        boolean isFirst = true;
        for (Expression child : this.getChildren())
        {
            if (!isFirst)
            {
                writer.write(' ');
                writer.write(binaryOp.getExpressionText());
                writer.write(' ');
            }
            child.toEQL(writer);
            isFirst = false;
        }
    }

    public BitWiseOpEnum getBinaryOp()
    {
        return binaryOp;
    }

    public void setBinaryOp(BitWiseOpEnum binaryOp)
    {
        this.binaryOp = binaryOp;
    }
}
