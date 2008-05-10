package com.espertech.esper.util;

/**
 * Interface for casting.
 */
public interface SimpleTypeCaster
{
    /**
     * Casts an object to another type, typically for numeric types.
     * <p>
     * May performs a compatibility check and returns null if not compatible.
     * @param object to cast
     * @return casted or transformed object, possibly the same, or null if the cast cannot be made
     */
    public Object cast(Object object);

    /**
     * Returns true to indicate that the cast target type is numeric.
     * @return true for numeric cast
     */
    public boolean isNumericCast();
}
