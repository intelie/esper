package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;

import java.util.HashSet;
import java.util.Set;

public class SupportVirtualDWIndex implements VirtualDataWindowLookup {

    private final SupportVirtualDW supportVirtualDW;
    private final VirtualDataWindowContext context;

    public SupportVirtualDWIndex(SupportVirtualDW supportVirtualDW, VirtualDataWindowContext context) {
        this.supportVirtualDW = supportVirtualDW;
        this.context = context;
    }

    public Set<EventBean> lookup(Object[] keys, EventBean[] eventsPerStream) {
        supportVirtualDW.setLastKeys(keys);
        supportVirtualDW.setLastAccessEvents(eventsPerStream);
        Set<EventBean> events = new HashSet<EventBean>();
        for (Object item : supportVirtualDW.getData()) {
            events.add(context.getEventFactory().wrap(item));
        }
        return events;
    }
}
