package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.epl.join.plan.QueryGraphRangeEnum;
import com.espertech.esper.epl.join.plan.QueryGraphValueRange;
import com.espertech.esper.epl.join.plan.QueryGraphValueRangeIn;
import com.espertech.esper.epl.join.plan.QueryGraphValueRangeRelOp;
import com.espertech.esper.epl.join.table.SubqueryRangeKeyDesc;
import com.espertech.esper.event.EventBeanUtility;

import java.util.*;

public class CompositeIndexQueryRange implements CompositeIndexQuery {

    private final CompositeAccessStrategy strategy;
    private CompositeIndexQuery next;

    public CompositeIndexQueryRange(EventType[] typePerStream, SubqueryRangeKeyDesc subqRangeKey, Class coercionType) {

        QueryGraphValueRange rangeProp = subqRangeKey.getRangeInfo();

        if (rangeProp.getType().isRange()) {
            QueryGraphValueRangeIn in = (QueryGraphValueRangeIn) rangeProp;
            EventPropertyGetter start = EventBeanUtility.getAssertPropertyGetter(typePerStream[subqRangeKey.getStartStreamNum()], in.getPropertyStart());
            boolean includeStart = rangeProp.getType().isIncludeStart();

            EventPropertyGetter end = EventBeanUtility.getAssertPropertyGetter(typePerStream[subqRangeKey.getEndStreamNum()], in.getPropertyEnd());
            boolean includeEnd = rangeProp.getType().isIncludeEnd();

            if (!rangeProp.getType().isRangeInverted()) {
                strategy = new CompositeAccessStrategyRangeNormal(start, includeStart, subqRangeKey.getStartStreamNum(), end, includeEnd, subqRangeKey.getEndStreamNum(), coercionType, ((QueryGraphValueRangeIn) rangeProp).isAllowRangeReversal());
            }
            else {
                strategy = new CompositeAccessStrategyRangeInverted(start, includeStart, subqRangeKey.getStartStreamNum(), end, includeEnd, subqRangeKey.getEndStreamNum(), coercionType);
            }
        }
        else {
            QueryGraphValueRangeRelOp relOp = (QueryGraphValueRangeRelOp) rangeProp;
            EventPropertyGetter key = EventBeanUtility.getAssertPropertyGetter(typePerStream[subqRangeKey.getKeyStreamNum()], relOp.getPropertyKey());
            if (rangeProp.getType() == QueryGraphRangeEnum.GREATER_OR_EQUAL) {
                strategy = new CompositeAccessStrategyGE(key, coercionType, subqRangeKey.getKeyStreamNum());
            }
            else if (rangeProp.getType() == QueryGraphRangeEnum.GREATER) {
                strategy = new CompositeAccessStrategyGT(key, coercionType, subqRangeKey.getKeyStreamNum());
            }
            else if (rangeProp.getType() == QueryGraphRangeEnum.LESS_OR_EQUAL) {
                strategy = new CompositeAccessStrategyLE(key, coercionType, subqRangeKey.getKeyStreamNum());
            }
            else if (rangeProp.getType() == QueryGraphRangeEnum.LESS) {
                strategy = new CompositeAccessStrategyLT(key, coercionType, subqRangeKey.getKeyStreamNum());
            }
            else {
                throw new IllegalArgumentException("Comparison operator " + rangeProp.getType() + " not supported");
            }
        }
    }

    public void add(EventBean event, Map parent, Set<EventBean> result) {
        strategy.lookup(event, parent, result, next);
    }

    public void add(EventBean[] eventsPerStream, Map parent, Collection<EventBean> result) {
        strategy.lookup(eventsPerStream, parent, result, next);
    }

    public Set<EventBean> get(EventBean event, Map parent) {
        return strategy.lookup(event, parent, null, next);
    }

    public Collection<EventBean> get(EventBean[] eventsPerStream, Map parent) {
        return strategy.lookup(eventsPerStream, parent, null, next);
    }

    protected static Set<EventBean> handle(EventBean event, SortedMap sortedMapOne, SortedMap sortedMapTwo, Set<EventBean> result, CompositeIndexQuery next) {
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

    protected static Collection<EventBean> handle(EventBean[] eventsPerStream, SortedMap sortedMapOne, SortedMap sortedMapTwo, Collection<EventBean> result, CompositeIndexQuery next) {
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
                next.add(eventsPerStream, entry.getValue(), result);
            }
            if (sortedMapTwo != null) {
                map = (Map<Object, Map>) sortedMapTwo;
                for (Map.Entry<Object, Map> entry : map.entrySet()) {
                    next.add(eventsPerStream, entry.getValue(), result);
                }
            }
            return result;
        }
    }

    private static void addResults(SortedMap sortedMapOne, SortedMap sortedMapTwo, Collection<EventBean> result) {
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

    public void setNext(CompositeIndexQuery next) {
        this.next = next;
    }
}
