package net.esper.eql.expression;

/**
 * Thrown to indicate a validation error in a filter expression.
 */
public class ExprValidationException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public ExprValidationException(String message)
    {
        super(message);
    }
}
