package net.esper.pattern.guard;

/**
 * Thrown to indicate a validation error in guard parameterization.
 */
public class GuardParameterException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public GuardParameterException(String message)
    {
        super(message);
    }
}
