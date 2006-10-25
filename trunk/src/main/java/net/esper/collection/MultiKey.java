package net.esper.collection;

import java.util.Arrays;
import java.io.Serializable;

/**
 * Functions as a key value for Maps where keys need to be composite values.
 * The class allows a Map that uses MultiKeyUntyped entries for key values to use multiple objects as keys.
 * It calculates the hashCode from the key objects on construction and caches the hashCode.
 */
public final class MultiKey<T> implements Serializable
{
    private final T[] keys;
    private final int hashCode;

    /**
     * Constructor for multiple keys supplied in an object array.
     * @param keys is an array of key objects
     */
    public MultiKey(T[] keys)
    {
        if (keys == null)
        {
            throw new IllegalArgumentException("The array of keys must not be null");
        }

        int total = 0;
        for (int i = 0; i < keys.length; i++)
        {
            if (keys[i] != null) {
                total ^= keys[i].hashCode();
            }
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
    public final T get(int index)
    {
        return keys[index];
    }

    public final boolean equals(Object other)
    {
        if (other == this)
        {
            return true;
        }
        if (other instanceof MultiKey)
        {
            MultiKey otherKeys = (MultiKey) other;
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
        return "MultiKeyUntyped" + Arrays.asList(keys).toString();
    }

    /**
     * Returns the key value array.
     * @return key value array
     */
    public final T[] getArray()
    {
        return keys;
    }
}

