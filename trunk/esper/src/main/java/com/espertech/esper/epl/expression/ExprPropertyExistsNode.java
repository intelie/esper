package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;

/**
 * Represents the EXISTS(property) function in an expression tree.
 */
public class ExprPropertyExistsNode extends ExprNode
{
    private ExprIdentNode identNode;

    /**
     * Ctor.
     */
    public ExprPropertyExistsNode()
    {
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        if (this.getChildNodes().size() != 1)
        {
            throw new ExprValidationException("Exists function node must have exactly 1 child node");
        }

        if (!(this.getChildNodes().get(0) instanceof ExprIdentNode))
        {
            throw new ExprValidationException("Exists function expects an property value expression as the child node");
        }

        identNode = (ExprIdentNode) this.getChildNodes().get(0);
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Class getType() throws ExprValidationException
    {
        return Boolean.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return identNode.evaluatePropertyExists(eventsPerStream, isNewData);
    }

    public String toExpressionString()
    {
        StringBuilder buffer = new StringBuilder();
        buffer.append("exists(");
        buffer.append(this.getChildNodes().get(0).toExpressionString());
        buffer.append(')');
        return buffer.toString();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprPropertyExistsNode))
        {
            return false;
        }

        return true;
    }
}
