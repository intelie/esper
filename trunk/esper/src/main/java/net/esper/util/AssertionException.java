package net.esper.util;

/**
 * Exception thrown when a runtime assertion failed.
 */
public class AssertionException extends RuntimeException
{
    /**
     * Ctor.
     * @param message - assertion message
     */
    public AssertionException(String message)
    {
        super(message);
    }
}
