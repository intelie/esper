package com.espertech.esper.event;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprEvaluator;
import com.espertech.esper.epl.expression.ExprValidationException;

public interface EventBeanManufacturer
{
    public EventBean manufacture(EventBean[] eventsPerStream, boolean newData);

    public void initialize(boolean isUsingWildcard, ExprEvaluator[] expressionNodes, String[] columnNames, Object[] expressionReturnTypes, MethodResolutionService methodResolutionService)
            throws ExprValidationException;
}
