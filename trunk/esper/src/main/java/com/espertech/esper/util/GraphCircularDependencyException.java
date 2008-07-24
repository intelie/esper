package com.espertech.esper.util;

/**
 * Exception to represent a circular dependency.
 */
public class GraphCircularDependencyException extends Exception
{
    /**
     * Ctor.
     * @param message supplies the detailed description
     */
    public GraphCircularDependencyException(final String message)
    {
        super(message);
    }

    /**
     * Ctor.
     * @param message supplies the detailed description
     * @param cause the exception cause
     */
    public GraphCircularDependencyException(final String message, final Throwable cause)
    {
        super(message, cause);
    }
}
