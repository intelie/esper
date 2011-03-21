package com.espertech.esper.epl.enummethod.dot;

import java.lang.reflect.Array;
import java.util.Iterator;

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
        Object next = Array.get(array, count++);
        if (next == null) {
            return null;
        }
        return next;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
