package com.espertech.esper.support.eql;

import com.espertech.esper.eql.core.MethodResolutionService;
import com.espertech.esper.eql.expression.ExprNode;
import com.espertech.esper.eql.expression.ExprValidationException;
import com.espertech.esper.eql.core.StreamTypeService;
import com.espertech.esper.eql.core.ViewResourceDelegate;
import com.espertech.esper.eql.variable.VariableService;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.schedule.TimeProvider;

public class SupportBoolExprNode extends ExprNode
{
    private boolean evaluateResult;

    public SupportBoolExprNode(boolean evaluateResult)
    {
        this.evaluateResult = evaluateResult;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public boolean isConstantResult()
    {
        return false;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return evaluateResult;
    }

    public String toExpressionString()
    {
        return null;
    }

    public boolean equalsNode(ExprNode node)
    {
        throw new UnsupportedOperationException("not implemented");
    }    
}
