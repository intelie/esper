package net.esper.eql.spec;

import net.esper.util.MetaDefItem;
import net.esper.eql.expression.ExprNode;

public class OnTriggerSetAssignment implements MetaDefItem
{
    private String variableName;
    private ExprNode expression;

    public OnTriggerSetAssignment(String variableName, ExprNode expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public ExprNode getExpression()
    {
        return expression;
    }

    public void setExpression(ExprNode expression)
    {
        this.expression = expression;
    }
}
