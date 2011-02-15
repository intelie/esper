package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Map;
import java.util.Set;

public class InnerIndexQueryKeyed implements InnerIndexQuery {

    private final EventPropertyGetter[] propertyGetters;
    private final Class[] keyCoercionTypes;
    private InnerIndexQuery next;

    public InnerIndexQueryKeyed(EventType eventType, String[] keysProps, Class[] keyCoercionTypes) {
        this.keyCoercionTypes  = keyCoercionTypes;
        propertyGetters = new EventPropertyGetter[keysProps.length];
        for (int i = 0; i < keysProps.length; i++)
        {
            propertyGetters[i] = EventBeanUtility.getSafePropertyGetter(eventType, keysProps[i]);
        }
    }

    public void setNext(InnerIndexQuery next) {
        this.next = next;
    }

    public Set<EventBean> get(EventBean event, Map parent) {
        MultiKeyUntyped mk = EventBeanUtility.getMultiKey(event, propertyGetters, keyCoercionTypes);
        Map innerIndex = (Map) parent.get(mk);
        if (innerIndex == null) {
            return null;
        }
        return next.get(event, innerIndex);
    }

    public void add(EventBean event, Map value, Set<EventBean> result) {
        throw new UnsupportedOperationException();
    }
}
