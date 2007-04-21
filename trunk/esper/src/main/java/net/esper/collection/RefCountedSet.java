/**************************************************************************************
 * Copyright (C) 2006 Esper Team. All rights reserved.                                *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package net.esper.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

/**
 * reference-counting set based on a HashMap implementation that stores keys and a reference counter for
 * each unique key value. Each time the same key is added, the reference counter increases.
 * Each time a key is removed, the reference counter decreases.
 */
public class RefCountedSet<K>
{
    private Map<K, Integer> refSet;
    private int numValues;

    /**
     * Constructor.
     */
    public RefCountedSet()
    {
        refSet = new HashMap<K, Integer>();
    }

    /**
     * Add a key to the set. Add with a reference count of one if the key didn't exist in the set.
     * Increase the reference count by one if the key already exists.
     * Return true if this is the first time the key was encountered, or false if key is already in set.
     * @param key to add
     * @return true if the key is not in the set already, false if the key is already in the set
     */
    public boolean add(K key)
    {
        Integer value = refSet.get(key);
        if (value == null)
        {
            refSet.put(key, 1);
            numValues++;
            return true;
        }

        value++;
        numValues++;
        refSet.put(key, value);
        return false;
    }

    /**
     * Removed a key to the set. Removes the key if the reference count is one.
     * Decreases the reference count by one if the reference count is more then one.
     * Return true if the reference count was one and the key thus removed, or false if key is stays in set.
     * @param key to add
     * @return true if the key is removed, false if it stays in the set
     * @throws IllegalStateException is a key is removed that wasn't added to the map
     */
    public boolean remove(K key)
    {
        Integer value = refSet.get(key);
        if (value == null)
        {
            throw new IllegalStateException("Attempting to remove key from map that wasn't added");
        }

        if (value == 1)
        {
            refSet.remove(key);
            numValues--;
            return true;
        }

        value--;
        refSet.put(key, value);
        numValues--;
        return false;
    }

    /**
     * Returns an iterator over the entry set.
     * @return entry set iterator
     */
    public Iterator<Map.Entry<K, Integer>> entryIterator()
    {
        return refSet.entrySet().iterator();
    }

    /**
     * Returns the number of values in the collection.
     * @return size
     */
    public int size()
    {
        return numValues;
    }
}
