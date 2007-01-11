package net.esper.eql.parse;

import net.esper.client.EPException;

/**
 * This exception is thrown to indicate a problem in a filter specification.
 */
public class ASTFilterSpecValidationException extends ASTWalkException
{
    /**
     * Ctor.
     * @param message - error message
     * @param t - inner throwable
     */
    public ASTFilterSpecValidationException(final String message, Throwable t)
    {
        super(message, t);
    }

    /**
     * Ctor.
     * @param message - error message
     */
    public ASTFilterSpecValidationException(final String message)
    {
        super(message);
    }
}
