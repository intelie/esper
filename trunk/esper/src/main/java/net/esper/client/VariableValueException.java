package net.esper.client;

/**
 * Indicates that a variable value could not be assigned.
 */
public class VariableValueException extends EPException
{
    /**
     * Ctor.
     * @param message supplies exception details
     */
    public VariableValueException(final String message)
    {
        super(message);
    }
}
