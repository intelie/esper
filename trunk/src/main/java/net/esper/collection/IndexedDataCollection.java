package net.esper.collection;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;

/**
 * Collection to hold indexed data. Each key maps to multiple values.
 * Objects can be added to keys. The key class should override the equals and hashCode methods.
 * Same value objects can be added twice to the collection - the collection does not enforce set behavior.
 */
public final class IndexedDataCollection
{
    private final HashMap<Object, LinkedList<Object>> eventIndex = new HashMap<Object, LinkedList<Object>>();

    /**
     * Add a value object to the index. If the value object already exists for the same key,
     * no error is thrown and the object is still added (no set behavior).
     * @param key is the key value
     * @param bean is the value object to add
     */
    public final void add(Object key, Object bean)
    {
        LinkedList<Object> listOfBeans = eventIndex.get(key);
        if (listOfBeans != null)
        {
            listOfBeans.add(bean);
            return;
        }

        listOfBeans = new LinkedList<Object>();
        listOfBeans.add(bean);
        eventIndex.put(key, listOfBeans);
    }

    /**
     * Removes a value object from the index, returning a boolean value to indicate if the value object was found.
     * @param key is the key value
     * @param bean is the value object to remove
     * @return true if the value object was successfully removed, false if the key or value object could not be found
     */
    public final boolean remove(Object key, Object bean)
    {
        LinkedList<Object> listOfBeans = eventIndex.get(key);
        if (listOfBeans == null)
        {
            return false;
        }

        boolean result = listOfBeans.remove(bean);
        if (listOfBeans.size() == 0)
        {
            eventIndex.remove(key);
        }
        return result;
    }

    /**
     * Returns a list of value objects for the given key, or null if there are no value objects for this key.
     * @param key is the index key value
     * @return null if key has no associated value objects, or the list of value objects for the key
     */
    public final List<Object> get(Object key)
    {
        LinkedList<Object> listOfBeans = eventIndex.get(key);
        return listOfBeans;
    }


}
