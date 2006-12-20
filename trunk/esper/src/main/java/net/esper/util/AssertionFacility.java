package net.esper.util;

/**
 * Simple facility for asserting at runtime.
 */
public class AssertionFacility
{    
    /**
     * Assert the value is true and raise exception if false.
     * @param value - boolean to check
     * @param message - message to place in exception
     * @throws AssertionException thrown if value is false
     */
    public static void assertTrue(boolean value, String message) throws AssertionException
    {
        if (!value)
        {
            throw new AssertionException(message);
        }
    }

    /**
     * Assert the value is false and raise exception if true.
     * @param value - boolean to check
     * @param message - message to place in exception
     * @throws AssertionException thrown if value is true
     */
    public static void assertFalse(boolean value, String message) throws AssertionException
    {
        if (value)
        {
            throw new AssertionException(message);
        }
    }
}
