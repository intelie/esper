package com.espertech.esper.pattern.observer;

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

    /**
     * Ctor.
     * @param message the error message
     * @param cause the causal exception
     */
    public ObserverParameterException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
