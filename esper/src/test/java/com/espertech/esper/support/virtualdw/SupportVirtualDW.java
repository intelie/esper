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

package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.*;

import java.util.*;

public class SupportVirtualDW implements VirtualDataWindow {

    public static final String ITERATE = "iterate";

    private final VirtualDataWindowContext context;
    private Set<Object> data;
    private boolean destroyed;
    private VirtualDataWindowLookupContext lastRequestedIndex;
    private Object[] lastAccessKeys;
    private EventBean[] lastAccessEvents;
    private EventBean[] lastUpdateNew;
    private EventBean[] lastUpdateOld;
    private List<VirtualDataWindowEvent> events = new ArrayList<VirtualDataWindowEvent>();

    public VirtualDataWindowContext getContext() {
        return context;
    }

    public void setData(Set data) {
        this.data = data;
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public Set<Object> getData() {
        return data;
    }

    public VirtualDataWindowLookupContext getLastRequestedIndex() {
        return lastRequestedIndex;
    }

    public SupportVirtualDW(VirtualDataWindowContext context) {
        this.context = context;
    }

    public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {
        lastRequestedIndex = desc;
        return new SupportVirtualDWIndex(this, context);
    }

    public void destroy() {
        destroyed = true;
    }

    public void setLastKeys(Object[] keys) {
        lastAccessKeys = keys;
    }

    public Object[] getLastAccessKeys() {
        return lastAccessKeys;
    }

    public void setLastAccessEvents(EventBean[] lastAccessEvents) {
        this.lastAccessEvents = lastAccessEvents;
    }

    public EventBean[] getLastAccessEvents() {
        return lastAccessEvents;
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
        lastUpdateNew = newData;
        lastUpdateOld = oldData;
        context.getOutputStream().update(newData, oldData);
    }

    public EventBean[] getLastUpdateNew() {
        return lastUpdateNew;
    }

    public EventBean[] getLastUpdateOld() {
        return lastUpdateOld;
    }

    public Iterator<EventBean> iterator() {
        if (context.getCustomConfiguration() != null && context.getCustomConfiguration().equals(ITERATE)) {
            List<EventBean> events = new ArrayList<EventBean>();
            for (Object item : data) {
                events.add(context.getEventFactory().wrap(item));
            }
            return events.iterator();
        }
        return Collections.<EventBean>emptyList().iterator();
    }

    public void handleEvent(VirtualDataWindowEvent event) {
        events.add(event);
    }

    public List<VirtualDataWindowEvent> getEvents() {
        return events;
    }
}
