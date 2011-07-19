/*
 * *************************************************************************************
 *  Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 *  http://esper.codehaus.org                                                          *
 *  http://www.espertech.com                                                           *
 *  ---------------------------------------------------------------------------------- *
 *  The software in this package is published under the terms of the GPL license       *
 *  a copy of which has been included with this distribution in the license.txt file.  *
 * *************************************************************************************
 */

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
        this.propertyGetter = EventBeanUtility.getAssertPropertyGetter(eventType, rangeProp);
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

            if (eventMap == null) {
                return;
            }
            
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
