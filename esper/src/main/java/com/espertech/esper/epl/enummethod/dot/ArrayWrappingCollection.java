package com.espertech.esper.epl.enummethod.dot;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ArrayWrappingCollection implements Collection {

    private Object array;

    public ArrayWrappingCollection(Object array) {
        this.array = array;
    }

    public int size() {
        return Array.getLength(array);
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public Iterator iterator() {
        return new ArrayWrappingIterator(array);
    }

    public Object[] toArray() {
        return (Object[]) array;
    }

    public Object[] toArray(Object[] a) {
        return (Object[]) array;
    }

    public boolean contains(Object o) {
        throw new UnsupportedOperationException("Partial implementation");
    }

    public boolean add(Object o) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean containsAll(Collection c) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public void clear() {
        throw new UnsupportedOperationException("Read-only implementation");
    }
}
