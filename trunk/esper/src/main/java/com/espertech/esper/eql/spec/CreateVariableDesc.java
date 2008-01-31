package com.espertech.esper.eql.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.eql.expression.ExprNode;

import java.util.List;

/**
 * Descriptor for create-variable statements.
 */
public class CreateVariableDesc implements MetaDefItem
{
    private String variableType;
    private String variableName;
    private ExprNode assignment;

    /**
     * Ctor.
     * @param variableType type of the variable
     * @param variableName name of the variable
     * @param assignment expression assigning the initial value, or null if none
     */
    public CreateVariableDesc(String variableType, String variableName, ExprNode assignment)
    {
        this.variableType = variableType;
        this.variableName = variableName;
        this.assignment = assignment;
    }

    /**
     * Returns the variable type.
     * @return type of variable
     */
    public String getVariableType()
    {
        return variableType;
    }

    /**
     * Returns the variable name
     * @return name
     */
    public String getVariableName()
    {
        return variableName;
    }

    /**
     * Returns the assignment expression, or null if none
     * @return expression or null
     */
    public ExprNode getAssignment()
    {
        return assignment;
    }
}
