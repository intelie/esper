package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.client.EventBean;

public class ExprNodeValidated extends ExprNode
{
    private final ExprNode inner;

    public ExprNodeValidated(ExprNode inner)
    {
        this.inner = inner;
    }

    public String toExpressionString()
    {
        return inner.toExpressionString();
    }

    public boolean isConstantResult()
    {
        return inner.isConstantResult();
    }

    public boolean equalsNode(ExprNode node)
    {
        if (node instanceof ExprNodeValidated)
        {
            return inner.equalsNode(((ExprNodeValidated) node).inner);
        }
        return inner.equalsNode(node);
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
    }

    public Class getType()
    {
        return inner.getType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return inner.evaluate(eventsPerStream, isNewData);
    }
}
