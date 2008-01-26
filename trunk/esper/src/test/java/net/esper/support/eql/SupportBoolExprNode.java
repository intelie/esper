package net.esper.support.eql;

import net.esper.eql.core.MethodResolutionService;
import net.esper.eql.expression.ExprNode;
import net.esper.eql.expression.ExprValidationException;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewResourceDelegate;
import net.esper.eql.variable.VariableService;
import net.esper.event.EventBean;
import net.esper.schedule.TimeProvider;

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
