package net.esper.eql.expression;

import net.esper.eql.core.AutoImportService;
import net.esper.eql.core.StreamTypeService;
import net.esper.eql.core.ViewFactoryDelegate;

/**
 * Validation interface for expression nodes.
 */
public interface ExprValidator
{
    /**
     * Validate node.
     * @param streamTypeService serves stream event type info
     * @param autoImportService - for resolving class names in library method invocations
     * @throws ExprValidationException thrown when validation failed
     */
    public void validate(StreamTypeService streamTypeService,
                         AutoImportService autoImportService,
                         ViewFactoryDelegate viewFactoryDelegate) throws ExprValidationException;

    /**
     * Returns the type that the node's evaluate method returns an instance of.
     * @return type returned when evaluated
     * @throws ExprValidationException thrown when validation failed
     */
    public Class getType() throws ExprValidationException;
}