package com.espertech.esper.epl.enummethod.dot;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventAdapterService;
import com.espertech.esper.event.bean.BeanEventType;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class ExprDotStaticMethodWrapIterable implements ExprDotStaticMethodWrap {
    private EventAdapterService eventAdapterService;
    private BeanEventType type;

    public ExprDotStaticMethodWrapIterable(EventAdapterService eventAdapterService, BeanEventType type) {
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

        // there is a need to read the iterator to the cache since if it's iterated twice, the iterator is already exhausted
        return new WrappingCollection(eventAdapterService, type, ((Iterable) result).iterator());
    }

    private static class WrappingCollection implements Collection<EventBean> {
        private EventAdapterService eventAdapterService;
        private BeanEventType type;
        private Iterator inner;
        private Object[] cache = null;

        private WrappingCollection(EventAdapterService eventAdapterService, BeanEventType type, Iterator inner) {
            this.eventAdapterService = eventAdapterService;
            this.type = type;
            this.inner = inner;
        }

        public int size() {
            if (cache == null) {
                init();
            }
            return cache.length;
        }

        public boolean isEmpty() {
            if (cache == null) {
                init();
            }
            return cache.length == 0;
        }

        public Iterator<EventBean> iterator() {
            if (cache == null) {
                init();
            }
            return new ExprDotStaticMethodWrapArray.WrappingIterator(eventAdapterService, type, cache);
        }

        private void init() {
            Deque<Object> q = new ArrayDeque<Object>();
            for (;inner.hasNext();) {
                q.add(inner.next());
            }
            cache = q.toArray();
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
}
