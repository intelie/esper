package net.esper.view;

import net.esper.eql.core.StreamTypeService;
import net.esper.eql.expression.ExprValidationException;

/**
 * Interface for views that require validation against stream event types. 
 */
public interface ValidatedView
{
    public void validate(StreamTypeService streamTypeService) throws ExprValidationException;
}
