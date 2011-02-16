package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.event.EventBeanUtility;

import java.util.*;

public class CompositeIndexEnterRemoveRange implements CompositeIndexEnterRemove {

    private final EventPropertyGetter propertyGetter;
    private final Class coercionType;
    private HashSet<EventBean> nullKeys;
    private CompositeIndexEnterRemove next;

    public CompositeIndexEnterRemoveRange(EventType eventType, String rangeProp, Class coercionType) {
        this.propertyGetter = EventBeanUtility.getSafePropertyGetter(eventType, rangeProp);
        this.coercionType = coercionType;
    }

    public void setNext(CompositeIndexEnterRemove next) {
        this.next = next;
    }

    public void getAll(HashSet<EventBean> result, Map parent) {
        if (next == null) {
            Map<Object, Set<EventBean>> eventMap = parent;
            for (Map.Entry<Object, Set<EventBean>> entry : eventMap.entrySet()) {
                result.addAll(entry.getValue());
            }
        }
        else {
            Map<Object, Map> eventMap = parent;
            for (Map.Entry<Object, Map> entry : eventMap.entrySet()) {
                next.getAll(result, entry.getValue());
            }
        }
        if (nullKeys != null) {
            result.addAll(nullKeys);
        }
    }

    public void enter(EventBean event, Map parent) {
        Object sortable = propertyGetter.get(event);

        if (sortable == null) {
            if (nullKeys == null) {
                nullKeys = new HashSet<EventBean>();
            }
            nullKeys.add(event);
            return;
        }

        sortable = EventBeanUtility.coerce(sortable, coercionType);

        // if this is a leaf, enter event
        if (next == null) {
            Map<Object, Set<EventBean>> eventMap = (Map<Object, Set<EventBean>>) parent;

            Set<EventBean> events = eventMap.get(sortable);
            if (events == null)
            {
                events = new HashSet<EventBean>();
                eventMap.put(sortable, events);
            }

            if (events.contains(event))
            {
                throw new IllegalArgumentException("Event already in index, event=" + event);
            }
            events.add(event);
        }
        else {
            Map innerIndex = (Map) parent.get(sortable);
            if (innerIndex == null) {
                innerIndex = new TreeMap();
                parent.put(sortable, innerIndex);
            }
            next.enter(event, innerIndex);
        }
    }

    public void remove(EventBean event, Map parent) {
        Object sortable = propertyGetter.get(event);

        if (sortable == null) {
            if (nullKeys != null) {
                nullKeys.remove(event);
            }
            return;
        }

        sortable = EventBeanUtility.coerce(sortable, coercionType);

        // if this is a leaf, remove event
        if (next == null) {
            Map<Object, Set<EventBean>> eventMap = (Map<Object, Set<EventBean>>) parent;

            Set<EventBean> events = eventMap.get(sortable);
            if (events == null)
            {
                return;
            }

            if (!events.remove(event))
            {
                return;
            }

            if (events.isEmpty())
            {
                parent.remove(sortable);
            }
        }
        else {
            Map innerIndex = (Map) parent.get(sortable);
            if (innerIndex == null) {
                return;
            }
            next.remove(event, innerIndex);
            if (innerIndex.isEmpty()) {
                parent.remove(sortable);
            }
        }
    }
}
