package net.esper.eql.expression;

/**
 * Validation interface for filter nodes.
 */
public interface ExprValidator
{
    /**
     * Validate node.
     * @param streamTypeService serves stream event type info
     * @throws ExprValidationException thrown when validation failed
     */
    public void validate(StreamTypeService streamTypeService) throws ExprValidationException;

    /**
     * Returns the type that the node's evaluate method returns an instance of.
     * @return type returned when evaluated
     * @throws ExprValidationException thrown when validation failed
     */
    public Class getType() throws ExprValidationException;
}
