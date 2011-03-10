package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.bean.BeanEventType;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;

public class ExprDotStaticMethodWrapArray implements ExprDotStaticMethodWrap {
    private EventAdapterService eventAdapterService;
    private BeanEventType type;

    public ExprDotStaticMethodWrapArray(EventAdapterService eventAdapterService, BeanEventType type) {
        this.eventAdapterService = eventAdapterService;
        this.type = type;
    }

    public EventType getEventType() {
        return type;
    }

    public Collection<EventBean> convert(Object result) {
        if (result == null) {
            return null;
        }
        if (!result.getClass().isArray()) {
            return null;
        }
        return new WrappingCollection(eventAdapterService, type, result);
    }

    private static class WrappingCollection implements Collection<EventBean> {

        private EventAdapterService eventAdapterService;
        private BeanEventType type;
        private Object array;

        private WrappingCollection(EventAdapterService eventAdapterService, BeanEventType type, Object array) {
            this.eventAdapterService = eventAdapterService;
            this.type = type;
            this.array = array;
        }

        public int size() {
            return Array.getLength(array);
        }

        public boolean isEmpty() {
            return size() == 0;
        }

        public Iterator<EventBean> iterator() {
            return new WrappingIterator(eventAdapterService, type, array);
        }

        public boolean contains(Object o) {
            throw new UnsupportedOperationException("Partial implementation");
        }

        public Object[] toArray() {
            throw new UnsupportedOperationException("Partial implementation");
        }

        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException("Partial implementation");
        }

        public boolean add(EventBean eventBean) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public boolean addAll(Collection<? extends EventBean> c) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("Read-only implementation");
        }

        public void clear() {
            throw new UnsupportedOperationException("Read-only implementation");
        }
    }

    public static class WrappingIterator implements Iterator<EventBean> {
        private EventAdapterService eventAdapterService;
        private BeanEventType type;
        private Object array;
        private int count;

        public WrappingIterator(EventAdapterService eventAdapterService, BeanEventType type, Object array) {
            this.eventAdapterService = eventAdapterService;
            this.type = type;
            this.array = array;
        }

        public boolean hasNext() {
            if (Array.getLength(array) > count) {
                return true;
            }
            return false;
        }

        public EventBean next() {
            Object next = Array.get(array, count++);
            if (next == null) {
                return null;
            }
            return eventAdapterService.adapterForTypedBean(next, type);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
