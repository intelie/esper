package com.espertech.esper.epl.join.table.comp;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.epl.join.plan.RangeKeyDesc;
import com.espertech.esper.event.EventBeanUtility;

import java.util.*;

public class InnerIndexQueryRange implements InnerIndexQuery {

    private final CompositeAccessStrategy strategy;
    private InnerIndexQuery next;

    public InnerIndexQueryRange(EventType eventType, RangeKeyDesc rangeProp, Class coercionType) {

        if (rangeProp.getOp().isRange()) {
            EventPropertyGetter start = EventBeanUtility.getSafePropertyGetter(eventType, rangeProp.getStart());
            boolean includeStart = rangeProp.getOp().isIncludeStart();
            EventPropertyGetter end = EventBeanUtility.getSafePropertyGetter(eventType, rangeProp.getEnd());
            boolean includeEnd = rangeProp.getOp().isIncludeEnd();
            if (!rangeProp.getOp().isRangeInverted()) {
                strategy = new CompositeAccessStrategyRange(start, includeStart, end, includeEnd, coercionType);
            }
            else {
                strategy = new CompositeAccessStrategyRangeInverted(start, includeStart, end, includeEnd, coercionType);
            }
        }
        else {
            EventPropertyGetter key = EventBeanUtility.getSafePropertyGetter(eventType, rangeProp.getKey());
            if (rangeProp.getOp() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
                strategy = new CompositeAccessStrategyGE(key, coercionType);
            }
            else if (rangeProp.getOp() == QueryGraphRangeEnum.GREATER) {
                strategy = new CompositeAccessStrategyGT(key, coercionType);
            }
            else if (rangeProp.getOp() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
                strategy = new CompositeAccessStrategyLE(key, coercionType);
            }
            else if (rangeProp.getOp() == QueryGraphRangeEnum.LESS) {
                strategy = new CompositeAccessStrategyLT(key, coercionType);
            }
            else {
                throw new IllegalArgumentException("Comparison operator " + rangeProp.getOp() + " not supported");
            }
        }
    }

    public void add(EventBean event, Map parent, Set<EventBean> result) {
        strategy.lookup(event, parent, result, next);
    }

    public Set<EventBean> get(EventBean event, Map parent) {
        return strategy.lookup(event, parent, null, next);
    }

    protected static Set<EventBean> handle(EventBean event, SortedMap sortedMapOne, SortedMap sortedMapTwo, Set<EventBean> result, InnerIndexQuery next) {
        if (next == null) {
            if (result == null) {
                result = new HashSet<EventBean>();
            }
            addResults(sortedMapOne, sortedMapTwo, result);
            return result;
        }
        else {
            if (result == null) {
                result = new HashSet<EventBean>();
            }
            Map<Object, Map> map = (Map<Object, Map>) sortedMapOne;
            for (Map.Entry<Object, Map> entry : map.entrySet()) {
                next.add(event, entry.getValue(), result);
            }
            if (sortedMapTwo != null) {
                map = (Map<Object, Map>) sortedMapTwo;
                for (Map.Entry<Object, Map> entry : map.entrySet()) {
                    next.add(event, entry.getValue(), result);
                }
            }
            return result;
        }
    }

    private static void addResults(SortedMap sortedMapOne, SortedMap sortedMapTwo, Set<EventBean> result) {
        Map<Object, Set<EventBean>> map = (Map<Object, Set<EventBean>>) sortedMapOne;
        for (Map.Entry<Object, Set<EventBean>> entry : map.entrySet()) {
            result.addAll(entry.getValue());
        }

        if (sortedMapTwo != null) {
            map = (Map<Object, Set<EventBean>>) sortedMapTwo;
            for (Map.Entry<Object, Set<EventBean>> entry : map.entrySet()) {
                result.addAll(entry.getValue());
            }
        }
    }

    public void setNext(InnerIndexQuery next) {
        this.next = next;
    }

    public interface CompositeAccessStrategy {
        public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next);
    }

    public static class CompositeAccessStrategyRange implements CompositeAccessStrategy {
        private EventPropertyGetter start;
        private boolean includeStart;
        private EventPropertyGetter end;
        private boolean includeEnd;
        private Class coercionType;

        public CompositeAccessStrategyRange(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, Class coercionType) {
            this.start = start;
            this.includeStart = includeStart;
            this.end = end;
            this.includeEnd = includeEnd;
            this.coercionType = coercionType;
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
            TreeMap index = (TreeMap) parent;
            comparableStart = EventBeanUtility.coerce(comparableStart, coercionType);
            comparableEnd = EventBeanUtility.coerce(comparableEnd, coercionType);

            SortedMap<Object,Set<EventBean>> submap;
            try {
                submap = index.subMap(comparableStart, includeStart, comparableEnd, includeEnd);
            }
            catch (IllegalArgumentException ex) {
                submap = index.subMap(comparableEnd, includeStart, comparableStart, includeEnd);
            }

            return InnerIndexQueryRange.handle(event, submap, null, result, next);
        }
    }

    public static class CompositeAccessStrategyRangeInverted implements CompositeAccessStrategy {
        private EventPropertyGetter start;
        private boolean includeStart;
        private EventPropertyGetter end;
        private boolean includeEnd;
        private Class coercionType;

        public CompositeAccessStrategyRangeInverted(EventPropertyGetter start, boolean includeStart, EventPropertyGetter end, boolean includeEnd, Class coercionType) {
            this.start = start;
            this.includeStart = includeStart;
            this.end = end;
            this.includeEnd = includeEnd;
            this.coercionType = coercionType;
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

    public static class CompositeAccessStrategyGE implements CompositeAccessStrategy {
        private EventPropertyGetter key;
        private Class coercionType;

        public CompositeAccessStrategyGE(EventPropertyGetter key, Class coercionType) {
            this.key = key;
            this.coercionType = coercionType;
        }

        public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next) {
            TreeMap index = (TreeMap) parent;
            Object comparable = key.get(event);
            if (comparable == null) {
                return null;
            }
            comparable = EventBeanUtility.coerce(comparable, coercionType);
            return InnerIndexQueryRange.handle(event, index.tailMap(comparable), null, result, next);
        }
    }

    public static class CompositeAccessStrategyGT implements CompositeAccessStrategy {
        private EventPropertyGetter key;
        private Class coercionType;

        public CompositeAccessStrategyGT(EventPropertyGetter key, Class coercionType) {
            this.key = key;
            this.coercionType = coercionType;
        }

        public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next) {
            TreeMap index = (TreeMap) parent;
            Object comparable = key.get(event);
            if (comparable == null) {
                return null;
            }
            comparable = EventBeanUtility.coerce(comparable, coercionType);
            return InnerIndexQueryRange.handle(event, index.tailMap(comparable, false), null, result, next);
        }
    }

    public static class CompositeAccessStrategyLE implements CompositeAccessStrategy {
        private EventPropertyGetter key;
        private Class coercionType;

        public CompositeAccessStrategyLE(EventPropertyGetter key, Class coercionType) {
            this.key = key;
            this.coercionType = coercionType;
        }

        public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next) {
            TreeMap index = (TreeMap) parent;
            Object comparable = key.get(event);
            if (comparable == null) {
                return null;
            }
            comparable = EventBeanUtility.coerce(comparable, coercionType);
            return InnerIndexQueryRange.handle(event, index.headMap(comparable, true), null, result, next);
        }
    }

    public static class CompositeAccessStrategyLT implements CompositeAccessStrategy {
        private EventPropertyGetter key;
        private Class coercionType;

        public CompositeAccessStrategyLT(EventPropertyGetter key, Class coercionType) {
            this.key = key;
            this.coercionType = coercionType;
        }

        public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, InnerIndexQuery next) {
            TreeMap index = (TreeMap) parent;
            Object comparable = key.get(event);
            if (comparable == null) {
                return null;
            }
            comparable = EventBeanUtility.coerce(comparable, coercionType);
            return InnerIndexQueryRange.handle(event, index.headMap(comparable), null, result, next);
        }
    }
}
