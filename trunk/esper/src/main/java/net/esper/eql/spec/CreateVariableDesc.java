package net.esper.eql.spec;

import net.esper.util.MetaDefItem;
import net.esper.eql.expression.ExprNode;

import java.util.List;

public class CreateVariableDesc implements MetaDefItem
{
    private String variableType;
    private String variableName;
    private ExprNode assignment;

    public CreateVariableDesc(String variableType, String variableName, ExprNode assignment)
    {
        this.variableType = variableType;
        this.variableName = variableName;
        this.assignment = assignment;
    }

    public String getVariableType()
    {
        return variableType;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public ExprNode getAssignment()
    {
        return assignment;
    }
}
