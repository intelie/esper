package com.espertech.esper.epl.join.table.comp;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.EventPropertyGetter;
import com.espertech.esper.client.EventType;
import com.espertech.esper.collection.MultiKeyUntyped;
import com.espertech.esper.event.EventBeanUtility;

import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;

public class InnerIndexEnterRemoveKeyed implements InnerIndexEnterRemove {

    private final EventPropertyGetter[] propertyGetters;
    private final Class[] keyCoercionTypes;
    private InnerIndexEnterRemove next;

    public InnerIndexEnterRemoveKeyed(EventType eventType, String[] keysProps, Class[] keyCoercionTypes) {
        this.keyCoercionTypes = keyCoercionTypes;
        propertyGetters = new EventPropertyGetter[keysProps.length];
        for (int i = 0; i < keysProps.length; i++)
        {
            propertyGetters[i] = EventBeanUtility.getSafePropertyGetter(eventType, keysProps[i]);
        }
    }

    public void setNext(InnerIndexEnterRemove next) {
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
