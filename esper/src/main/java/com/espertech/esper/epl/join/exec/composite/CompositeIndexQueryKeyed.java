package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBeanUtility;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class CompositeIndexQueryKeyed implements CompositeIndexQuery {

    private final EventPropertyGetter[] propertyGetters;
    private final Class[] keyCoercionTypes;
    private final int[] keyStreamNum;
    private CompositeIndexQuery next;

    public CompositeIndexQueryKeyed(EventType[] typePerStream, String[] keysProps, int[] keyStreamNum, Class[] keyCoercionTypes) {
        this.keyCoercionTypes  = keyCoercionTypes;
        this.keyStreamNum = keyStreamNum;
        propertyGetters = new EventPropertyGetter[keysProps.length];
        for (int i = 0; i < keysProps.length; i++)
        {
            int keyStream = keyStreamNum[i];
            propertyGetters[i] = EventBeanUtility.getAssertPropertyGetter(typePerStream[keyStream], keysProps[i]);
        }
    }

    public void setNext(CompositeIndexQuery next) {
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

    public Collection<EventBean> get(EventBean[] eventsPerStream, Map parent) {
        MultiKeyUntyped mk = EventBeanUtility.getMultiKey(eventsPerStream, propertyGetters, keyStreamNum, keyCoercionTypes);
        Map innerIndex = (Map) parent.get(mk);
        if (innerIndex == null) {
            return null;
        }
        return next.get(eventsPerStream, innerIndex);
    }

    public void add(EventBean event, Map value, Set<EventBean> result) {
        throw new UnsupportedOperationException();
    }

    public void add(EventBean[] eventsPerStream, Map value, Collection<EventBean> result) {
        throw new UnsupportedOperationException();
    }
}
