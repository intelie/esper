package com.espertech.esper.collection;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;

public class IteratorCollectionRO<EventBean> implements Collection<EventBean> {

    private Deque<EventBean> cache;
    private Iterator<EventBean> it;

    public IteratorCollectionRO(Iterator<EventBean> it) {
        this.it = it;
    }

    public int size() {
        if (cache == null) {
            init();
        }
        return cache.size();
    }

    private void init() {
        cache = new ArrayDeque<EventBean>();
        while(it.hasNext()) {
            cache.add(it.next());
        }
        it = null;
    }

    public boolean isEmpty() {
        if (cache == null) {
            init();
        }
        return cache.isEmpty();
    }

    public boolean contains(Object o) {
        if (cache == null) {
            init();
        }
        return cache.contains(o);
    }

    public Iterator<EventBean> iterator() {
        // original iterator cannot be used: it must not be exhausted multiple times
        if (cache == null) {
            init();
        }
        return cache.iterator();
    }

    public Object[] toArray() {
        if (cache == null) {
            init();
        }
        return cache.toArray();
    }

    public <T> T[] toArray(T[] a) {
        if (cache == null) {
            init();
        }
        return cache.toArray(a);
    }

    public boolean containsAll(Collection<?> c) {
        if (cache == null) {
            init();
        }
        return cache.containsAll(c);
    }

    public boolean add(EventBean eventBean) {
        throw new UnsupportedOperationException("Read-only implementation");
    }

    public boolean remove(Object o) {
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
