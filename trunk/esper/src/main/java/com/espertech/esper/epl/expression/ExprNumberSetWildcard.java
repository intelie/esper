package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.type.WildcardParameter;

public class ExprNumberSetWildcard extends ExprNode
{
    private static WildcardParameter wildcardParameter = new WildcardParameter();
    public String toExpressionString()
    {
        return "*";
    }

    public boolean isConstantResult()
    {
        return true;
    }

    public boolean equalsNode(ExprNode node)
    {
        return node instanceof ExprNumberSetWildcard;
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
    }

    public Class getType() throws ExprValidationException
    {
        return WildcardParameter.class;
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return wildcardParameter;
    }
}
