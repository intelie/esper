package net.esper.client.logstate;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Uniquely identifies a piece of engine state at any point in time.
 */
public final class LogKey implements Serializable
{
    private final int[] keys;
    private final int hashCode;

    /**
     * Constructor for multiple keys supplied in an object array.
     * @param keys is an array of key objects
     */
    public LogKey(int[] keys)
    {
        if (keys == null)
        {
            throw new IllegalArgumentException("The array of keys must not be null");
        }

        int total = 0;
        for (int i = 0; i < keys.length; i++)
        {
            Integer boxed = keys[i];
            total ^= boxed.hashCode();
        }

        this.hashCode = total;
        this.keys = keys;
    }

    /**
     * Returns the number of key objects.
     * @return size of key object array
     */
    public final int size()
    {
        return keys.length;
    }

    /**
     * Returns the key object at the specified position.
     * @param index is the array position
     * @return key object at position
     */
    public final int get(int index)
    {
        return keys[index];
    }

    public final boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof LogKey)
        {
            LogKey otherKeys = (LogKey) other;
            return Arrays.equals(keys, otherKeys.keys);
        }
        return false;
    }

    public final int hashCode()
    {
        return hashCode;
    }

    public final String toString()
    {
        return Arrays.toString(keys);
    }

    /**
     * Returns the key value array.
     * @return key value array
     */
    public final int[] getArray()
    {
        return keys;
    }
}
