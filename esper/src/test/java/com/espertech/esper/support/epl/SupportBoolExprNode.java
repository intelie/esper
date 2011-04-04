package com.espertech.esper.support.epl;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.*;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.TimeProvider;

import java.util.Map;

public class SupportBoolExprNode extends ExprNode implements ExprEvaluator
{
    private boolean evaluateResult;

    public SupportBoolExprNode(boolean evaluateResult)
    {
        this.evaluateResult = evaluateResult;
    }

    public ExprEvaluator getExprEvaluator()
    {
        return this;
    }

    public void validate(ExprValidationContext validationContext) throws ExprValidationException {
    }

    public Class getType()
    {
        return Boolean.class;
    }

    public Map<String, Object> getEventType() {
        return null;
    }
    
    public boolean isConstantResult()
    {
        return false;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext context)
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
