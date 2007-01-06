package net.esper.collection;

import java.util.Collection;
import java.util.Iterator;

public class ArrayBackedCollection<T> implements Collection<T>
{
    private int lastIndex;
    private int currentIndex;
    private Object[] handles;

    public ArrayBackedCollection(int currentSize)
    {
        this.lastIndex = currentSize - 1;
        this.currentIndex = 0;
        this.handles = new Object[currentSize];
    }

    public boolean add(Object object)
    {
        if (currentIndex <= lastIndex)
        {
            handles[currentIndex++] = object;
            return true;
        }

        // allocate more by duplicating the current size
        int newSize = lastIndex * 2 + 2;
        Object[] newHandles = new Object[newSize];
        System.arraycopy(handles, 0, newHandles, 0, handles.length);
        handles = newHandles;
        lastIndex = newSize - 1;

        // add
        handles[currentIndex++] = object;
        return true;
    }

    public void clear()
    {
        currentIndex = 0;
    }

    public int size()
    {
        return currentIndex;
    }

    public Object[] getArray()
    {
        return handles;
    }

    public boolean isEmpty()
    {
        throw new UnsupportedOperationException();
    }

    public boolean contains(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public Iterator iterator()
    {
        throw new UnsupportedOperationException();
    }

    public Object[] toArray()
    {
        throw new UnsupportedOperationException();
    }


    public Object[] toArray(Object[] a)
    {
        throw new UnsupportedOperationException();
    }

    public boolean remove(Object o)
    {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(Collection c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean retainAll(Collection c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean removeAll(Collection c)
    {
        throw new UnsupportedOperationException();
    }

    public boolean containsAll(Collection c)
    {
        throw new UnsupportedOperationException();
    }
}
