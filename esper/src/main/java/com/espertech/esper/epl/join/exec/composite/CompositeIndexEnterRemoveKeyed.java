package com.espertech.esper.epl.join.exec.composite;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBeanUtility;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class CompositeIndexEnterRemoveKeyed implements CompositeIndexEnterRemove {

    private final EventPropertyGetter[] propertyGetters;
    private final Class[] keyCoercionTypes;
    private CompositeIndexEnterRemove next;

    public CompositeIndexEnterRemoveKeyed(EventType eventType, String[] keysProps, Class[] keyCoercionTypes) {
        this.keyCoercionTypes = keyCoercionTypes;
        propertyGetters = new EventPropertyGetter[keysProps.length];
        for (int i = 0; i < keysProps.length; i++)
        {
            propertyGetters[i] = EventBeanUtility.getAssertPropertyGetter(eventType, keysProps[i]);
        }
    }

    public void setNext(CompositeIndexEnterRemove next) {
        this.next = next;
    }

    public void enter(EventBean event, Map parent) {
        MultiKeyUntyped mk = EventBeanUtility.getMultiKey(event, propertyGetters, keyCoercionTypes);
        Map innerIndex = (Map) parent.get(mk);
        if (innerIndex == null) {
            innerIndex = new TreeMap<Object, Object>();
            parent.put(mk, innerIndex);
        }
        next.enter(event, innerIndex);
    }

    public void remove(EventBean event, Map parent) {
        MultiKeyUntyped mk = EventBeanUtility.getMultiKey(event, propertyGetters, keyCoercionTypes);
        Map innerIndex = (Map) parent.get(mk);
        if (innerIndex == null) {
            return;
        }
        next.remove(event, innerIndex);
        if (innerIndex.isEmpty()) {
            parent.remove(mk);
        }
    }

    public void getAll(HashSet<EventBean> result, Map parent) {
        Map<MultiKeyUntyped, Map> map = parent;
        for (Map.Entry<MultiKeyUntyped, Map> entry : map.entrySet()) {
            next.getAll(result, entry.getValue());
        }
    }
}
