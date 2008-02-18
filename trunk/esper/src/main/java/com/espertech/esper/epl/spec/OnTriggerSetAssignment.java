package com.espertech.esper.epl.spec;

import com.espertech.esper.util.MetaDefItem;
import com.espertech.esper.epl.expression.ExprNode;

/**
 * Descriptor for an on-set assignment. 
 */
public class OnTriggerSetAssignment implements MetaDefItem
{
    private String variableName;
    private ExprNode expression;

    /**
     * Ctor.
     * @param variableName variable name
     * @param expression expression providing new variable value
     */
    public OnTriggerSetAssignment(String variableName, ExprNode expression)
    {
        this.variableName = variableName;
        this.expression = expression;
    }

    /**
     * Returns the variable name
     * @return variable name
     */
    public String getVariableName()
    {
        return variableName;
    }

    /**
     * Returns the expression providing the new variable value, or null if none
     * @return assignment expression
     */
    public ExprNode getExpression()
    {
        return expression;
    }

    /**
     * Sets the expression providing the new variable value
     * @param expression assignment expression, or null if none
     */
    public void setExpression(ExprNode expression)
    {
        this.expression = expression;
    }
}
