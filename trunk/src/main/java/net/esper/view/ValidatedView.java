package net.esper.view;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.expression.ExprValidationException;

/**
 * Interface for views that require validation against stream event types.
 */
public interface ValidatedView
{
    /**
     * Validate the view.
     * @param streamTypeService supplies the types of streams against which to validate
     * @throws ExprValidationException is thrown to indicate an exception in validating the view
     */
    public void validate(StreamTypeService streamTypeService) throws ExprValidationException;
}
