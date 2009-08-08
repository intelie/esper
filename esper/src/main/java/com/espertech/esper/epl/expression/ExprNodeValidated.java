package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.agg.AggregationMethod;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.client.EventBean;

/**
 * A placeholder for another expression node that has been validated already.
 */
public class ExprNodeValidated extends ExprNode
{
    private final ExprNode inner;
    private static final long serialVersionUID = 301058622892268624L;

    /**
     * Ctor.
     * @param inner nested expression node
     */
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

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext) throws ExprValidationException
    {
    }

    public Class getType()
    {
        return inner.getType();
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData, ExprEvaluatorContext exprEvaluatorContext)
    {
        return inner.evaluate(eventsPerStream, isNewData, exprEvaluatorContext);
    }
}
