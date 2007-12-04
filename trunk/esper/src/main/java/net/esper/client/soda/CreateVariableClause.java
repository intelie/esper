package net.esper.client.soda;

import java.io.Serializable;
import java.io.StringWriter;
import java.util.List;
import java.util.ArrayList;

public class CreateVariableClause implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String variableType;
    private String variableName;
    private Expression optionalAssignment;

    public static CreateVariableClause create(String variableType, String variableName)
    {
        return new CreateVariableClause(variableType, variableName, null);
    }

    public static CreateVariableClause create(String variableType, String variableName, Expression expression)
    {
        return new CreateVariableClause(variableType, variableName, expression);
    }

    public CreateVariableClause(String variableType, String variableName, Expression optionalAssignment)
    {
        this.variableType = variableType;
        this.variableName = variableName;
        this.optionalAssignment = optionalAssignment;
    }

    public String getVariableType()
    {
        return variableType;
    }

    public void setVariableType(String variableType)
    {
        this.variableType = variableType;
    }

    public String getVariableName()
    {
        return variableName;
    }

    public void setVariableName(String variableName)
    {
        this.variableName = variableName;
    }

    public Expression getOptionalAssignment()
    {
        return optionalAssignment;
    }

    public void setOptionalAssignment(Expression optionalAssignment)
    {
        this.optionalAssignment = optionalAssignment;
    }

    public void toEQL(StringWriter writer)
    {
        writer.append("create variable ");
        writer.append(variableType);
        writer.append(" ");
        writer.append(variableName);
        if (optionalAssignment != null)
        {
            writer.append(" = ");
            optionalAssignment.toEQL(writer);
        }
    }
}
