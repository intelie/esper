package net.esper.eql.expression;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.event.EventBean;
import net.esper.schedule.TimeProvider;

/**
 * Represents a variable in an expression tree.
 */
public class ExprVariableNode extends ExprNode
{
    private String variableName;
    private Class variableType;

    /**
     * Ctor.
     */
    public ExprVariableNode(String variableName)
    {
        if (variableName == null)
        {
            throw new IllegalArgumentException("Variable name is null");
        }
        this.variableName = variableName;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider) throws ExprValidationException
    {
    }

    public Class getType() throws ExprValidationException
    {
        if (variableType == null)
        {
            throw new IllegalStateException("Variable node has not been validated");
        }
        return variableType;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public String toString()
    {
        return "variableName=" + variableName;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return null;
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append(variableName);
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprVariableNode))
        {
            return false;
        }

        ExprVariableNode other = (ExprVariableNode) node;

        return other.variableName.equals(this.variableName);
    }
}
