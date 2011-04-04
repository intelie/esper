package com.espertech.esper.epl.expression;

import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.StreamTypeServiceImpl;
import com.espertech.esper.epl.core.ViewResourceDelegate;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.schedule.TimeProvider;

import java.lang.annotation.Annotation;

public class ExprValidationContext {
    private final StreamTypeService streamTypeService;
    private final MethodResolutionService methodResolutionService;
    private final ViewResourceDelegate viewResourceDelegate;
    private final TimeProvider timeProvider;
    private final VariableService variableService;
    private final ExprEvaluatorContext exprEvaluatorContext;
    private final EventAdapterService eventAdapterService;
    private final String statementName;
    private final Annotation[] annotations;

    public ExprValidationContext(StreamTypeService streamTypeService, MethodResolutionService methodResolutionService, ViewResourceDelegate viewResourceDelegate, TimeProvider timeProvider, VariableService variableService, ExprEvaluatorContext exprEvaluatorContext, EventAdapterService eventAdapterService, String statementName, Annotation[] annotations) {
        this.streamTypeService = streamTypeService;
        this.methodResolutionService = methodResolutionService;
        this.viewResourceDelegate = viewResourceDelegate;
        this.timeProvider = timeProvider;
        this.variableService = variableService;
        this.exprEvaluatorContext = exprEvaluatorContext;
        this.eventAdapterService = eventAdapterService;
        this.statementName = statementName;
        this.annotations = annotations;
    }

    public ExprValidationContext(StreamTypeServiceImpl types, ExprValidationContext ctx) {
        this(types, ctx.getMethodResolutionService(), ctx.getViewResourceDelegate(), ctx.getTimeProvider(), ctx.getVariableService(), ctx.getExprEvaluatorContext(), ctx.getEventAdapterService(), ctx.getStatementName(), ctx.getAnnotations());
    }

    public StreamTypeService getStreamTypeService() {
        return streamTypeService;
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

    public String getStatementName() {
        return statementName;
    }

    public Annotation[] getAnnotations() {
        return annotations;
    }
}
