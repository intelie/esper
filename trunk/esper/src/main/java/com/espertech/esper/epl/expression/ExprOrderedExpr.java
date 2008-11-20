package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.event.EventBean;

public class ExprOrderedExpr extends ExprNode
{
    private final boolean isDescending;

    public ExprOrderedExpr(boolean descending)
    {
        isDescending = descending;
    }

    public String toExpressionString()
    {
        String inner = this.getChildNodes().get(0).toExpressionString();
        if (isDescending)
        {
            return inner + " desc";
        }
        return inner;
    }

    public boolean isConstantResult()
    {
        return getChildNodes().get(0).isConstantResult();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (!(node instanceof ExprOrderedExpr))
        {
            return false;
        }
        ExprOrderedExpr other = (ExprOrderedExpr) node;
        return other.isDescending == this.isDescending;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        // always valid
    }

    public Class getType()
    {
        return getChildNodes().get(0).getType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return getChildNodes().get(0).evaluate(eventsPerStream, isNewData);
    }

    public boolean isDescending()
    {
        return isDescending;
    }
}
