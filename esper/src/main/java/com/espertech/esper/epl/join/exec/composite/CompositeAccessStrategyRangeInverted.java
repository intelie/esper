package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CompositeAccessStrategyRangeInverted extends CompositeAccessStrategyRangeBase implements CompositeAccessStrategy {

    public CompositeAccessStrategyRangeInverted(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, Class coercionType) {
        super(start, includeStart, end, includeEnd, coercionType);
    }

    public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next) {
        Object comparableStart = start.get(event);
        if (comparableStart == null) {
            return null;
        }
        Object comparableEnd = end.get(event);
        if (comparableEnd == null) {
            return null;
        }
        comparableStart = EventBeanUtility.coerce(comparableStart, coercionType);
        comparableEnd = EventBeanUtility.coerce(comparableEnd, coercionType);

        TreeMap index = (TreeMap) parent;
        SortedMap<Object,Set<EventBean>> submapOne = index.headMap(comparableStart, !includeStart);
        SortedMap<Object,Set<EventBean>> submapTwo = index.tailMap(comparableEnd, !includeEnd);
        return InnerIndexQueryRange.handle(event, submapOne, submapTwo, result, next);
    }    
}
