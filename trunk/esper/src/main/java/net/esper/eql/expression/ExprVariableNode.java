package net.esper.eql.expression;

import net.esper.eql.core.*;
import net.esper.eql.variable.VariableService;
import net.esper.eql.variable.VariableReader;
import net.esper.event.EventBean;
import net.esper.schedule.TimeProvider;

/**
 * Represents a variable in an expression tree.
 */
public class ExprVariableNode extends ExprNode
{
    private final String variableName;
    private Class variableType;
    private VariableReader reader;

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

    public String getVariableName()
    {
        return variableName;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        reader = variableService.getReader(variableName);
        if (reader == null)
        {
            throw new ExprValidationException("A variable by name '" + variableName + " has not been declared");
        }

        // the variable name should not overlap with a property name
        try
        {
            streamTypeService.resolveByPropertyName(variableName);
            throw new ExprValidationException("The variable by name '" + variableName + " is ambigous to a property of the same name");
        }
        catch (DuplicatePropertyException e)
        {
            throw new ExprValidationException("The variable by name '" + variableName + " is ambigous to a property of the same name");
        }
        catch (PropertyNotFoundException e)
        {
        }

        variableType = reader.getType();
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
        return reader.getValue();
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
