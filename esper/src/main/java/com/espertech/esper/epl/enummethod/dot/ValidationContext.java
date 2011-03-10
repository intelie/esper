package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.expression.ExprEvaluatorContext;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.TimeProvider;

public class ValidationContext {
    private MethodResolutionService methodResolutionService;
    private ViewResourceDelegate viewResourceDelegate;
    private TimeProvider timeProvider;
    private VariableService variableService;
    private ExprEvaluatorContext exprEvaluatorContext;
    private EventAdapterService eventAdapterService;

    public ValidationContext(MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext, EventAdapterService eventAdapterService) {
        this.methodResolutionService = methodResolutionService;
        this.viewResourceDelegate = viewResourceDelegate;
        this.timeProvider = timeProvider;
        this.variableService = variableService;
        this.exprEvaluatorContext = exprEvaluatorContext;
        this.eventAdapterService = eventAdapterService;
    }

    public MethodResolutionService getMethodResolutionService() {
        return methodResolutionService;
    }

    public ViewResourceDelegate getViewResourceDelegate() {
        return viewResourceDelegate;
    }

    public TimeProvider getTimeProvider() {
        return timeProvider;
    }

    public VariableService getVariableService() {
        return variableService;
    }

    public ExprEvaluatorContext getExprEvaluatorContext() {
        return exprEvaluatorContext;
    }

    public EventAdapterService getEventAdapterService() {
        return eventAdapterService;
    }
}
