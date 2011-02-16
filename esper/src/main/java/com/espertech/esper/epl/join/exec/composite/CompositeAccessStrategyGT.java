package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class CompositeAccessStrategyGT extends CompositeAccessStrategyRelOpBase implements CompositeAccessStrategy {

    public CompositeAccessStrategyGT(EventPropertyGetter key, Class coercionType, int keyStreaNum) {
        super(key, coercionType, keyStreaNum);
    }

    public Set<EventBean> lookup(EventBean event, Map parent, Set<EventBean> result, CompositeIndexQuery next) {
        TreeMap index = (TreeMap) parent;
        Object comparable = key.get(event);
        if (comparable == null) {
            return null;
        }
        comparable = EventBeanUtility.coerce(comparable, coercionType);
        return CompositeIndexQueryRange.handle(event, index.tailMap(comparable, false), null, result, next);
    }

    public Collection<EventBean> lookup(EventBean[] eventPerStream, Map parent, Collection<EventBean> result, CompositeIndexQuery next) {
        TreeMap index = (TreeMap) parent;
        Object comparable = key.get(eventPerStream[keyStreamNum]);
        if (comparable == null) {
            return null;
        }
        comparable = EventBeanUtility.coerce(comparable, coercionType);
        return CompositeIndexQueryRange.handle(eventPerStream, index.tailMap(comparable, false), null, result, next);
    }
}
