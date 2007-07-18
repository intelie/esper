package net.esper.pattern.observer;

/**
 * Thrown to indicate a validation error in guard parameterization.
 */
public class ObserverParameterException extends Exception
{
    /**
     * Ctor.
     * @param message - validation error message
     */
    public ObserverParameterException(String message)
    {
        super(message);
    }
}
