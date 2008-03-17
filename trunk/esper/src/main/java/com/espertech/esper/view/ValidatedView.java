package com.espertech.esper.view;

import com.espertech.esper.epl.core.StreamTypeService;
import com.espertech.esper.epl.core.MethodResolutionService;
import com.espertech.esper.epl.expression.ExprValidationException;
import com.espertech.esper.epl.variable.VariableService;
import com.espertech.esper.schedule.TimeProvider;

/**
 * Interface for views that require validation against stream event types.
 */
public interface ValidatedView
{
    /**
     * Validate the view.
     * @param streamTypeService supplies the types of streams against which to validate
     * @param methodResolutionService for resolving imports and classes and methods
     * @param timeProvider for providing current time
     * @param variableService for access to variables
     * @throws ExprValidationException is thrown to indicate an exception in validating the view
     */
    public void validate(StreamTypeService streamTypeService,
                         MethodResolutionService methodResolutionService,
                         TimeProvider timeProvider,
                         VariableService variableService) throws ExprValidationException;
}
