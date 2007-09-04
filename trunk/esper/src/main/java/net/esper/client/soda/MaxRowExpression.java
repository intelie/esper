package net.esper.client.soda;

import java.io.StringWriter;

public class MaxRowExpression extends ExpressionBase
{
    public MaxRowExpression()
    {
    }

    public MaxRowExpression(String propertyOne, String propertyTwo, String[] moreProperties)
    {
        addChild(new PropertyValueExpression(propertyOne));
        addChild(new PropertyValueExpression(propertyTwo));
        for (int i = 0; i < moreProperties.length; i++)
        {
            addChild(new PropertyValueExpression(moreProperties[i]));
        }
    }

    public MaxRowExpression(Expression exprOne, Expression exprTwo, Expression[] moreExpressions)
    {
        addChild(exprOne);
        addChild(exprTwo);
        for (int i = 0; i < moreExpressions.length; i++)
        {
            addChild(moreExpressions[i]);
        }
    }

    public void toEQL(StringWriter writer)
    {
        writer.write("max(");

        String delimiter = "";
        for (Expression expr : this.getChildren())
        {
            writer.write(delimiter);
            expr.toEQL(writer);
            delimiter = ", ";
        }
        writer.write(')');
    }
}
