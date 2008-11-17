package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;
import com.espertech.esper.event.EventBean;

public class ExprNumberSetRange extends ExprNode
{
    public String toExpressionString()
    {
        return this.
    }

    public boolean isConstantResult()
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean equalsNode(ExprNode node)
    {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void validate(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService) throws ExprValidationException
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Class getType() throws ExprValidationException
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Object evaluate(EventBean[] eventsPerStream, boolean isNewData)
    {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
