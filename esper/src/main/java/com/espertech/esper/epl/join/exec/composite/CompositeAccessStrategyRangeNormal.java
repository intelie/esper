package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.event.EventBeanUtility;

import java.util.*;

public class CompositeAccessStrategyRangeNormal extends CompositeAccessStrategyRangeBase implements CompositeAccessStrategy {

    private boolean allowReverseRange;

    public CompositeAccessStrategyRangeNormal(EventPropertyGetter start, boolean includeStart, int startStreamNum, EventPropertyGetter end, boolean includeEnd, int endStreamNum, Class coercionType, boolean allowReverseRange) {
        super(start, includeStart, startStreamNum, end, includeEnd, endStreamNum, coercionType);
        this.allowReverseRange = allowReverseRange;
    }

    public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, CompositeIndexQuery next) {
        Object comparableStart = start.get(event);
        if (comparableStart == null) {
            return null;
        }
        Object comparableEnd = end.get(event);
        if (comparableEnd == null) {
            return null;
        }
        TreeMap index = (TreeMap) parent;
        comparableStart = EventBeanUtility.coerce(comparableStart, coercionType);
        comparableEnd = EventBeanUtility.coerce(comparableEnd, coercionType);

        SortedMap<Object,Set<EventBean>> submap;
        try {
            submap = index.subMap(comparableStart, includeStart, comparableEnd, includeEnd);
        }
        catch (IllegalArgumentException ex) {
            if (allowReverseRange) {
                submap = index.subMap(comparableEnd, includeStart, comparableStart, includeEnd);
            }
            else {
                return null;
            }
        }

        return CompositeIndexQueryRange.handle(event, submap, null, result, next);
    }

    public Collection<EventBean> lookup(EventBean[] eventPerStream, Map parent, Collection<EventBean> result, CompositeIndexQuery next) {
        Object comparableStart = start.get(eventPerStream[startStreamNum]);
        if (comparableStart == null) {
            return null;
        }
        Object comparableEnd = end.get(eventPerStream[endStreamNum]);
        if (comparableEnd == null) {
            return null;
        }
        TreeMap index = (TreeMap) parent;
        comparableStart = EventBeanUtility.coerce(comparableStart, coercionType);
        comparableEnd = EventBeanUtility.coerce(comparableEnd, coercionType);

        SortedMap<Object,Set<EventBean>> submap;
        try {
            submap = index.subMap(comparableStart, includeStart, comparableEnd, includeEnd);
        }
        catch (IllegalArgumentException ex) {
            if (allowReverseRange) {
                submap = index.subMap(comparableEnd, includeStart, comparableStart, includeEnd);
            }
            else {
                return null;
            }
        }

        return CompositeIndexQueryRange.handle(eventPerStream, submap, null, result, next);
    }
}
