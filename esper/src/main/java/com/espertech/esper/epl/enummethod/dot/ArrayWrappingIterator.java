package com.espertech.esper.epl.enummethod.dot;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayWrappingIterator implements Iterator {
    private Object array;
    private int count;

    public ArrayWrappingIterator(Object array) {
        this.array = array;
    }

    public boolean hasNext() {
        if (Array.getLength(array) > count) {
            return true;
        }
        return false;
    }

    public Object next() {
        if (hasNext()) {
            return Array.get(array, count++);
        }
        throw new NoSuchElementException();
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
