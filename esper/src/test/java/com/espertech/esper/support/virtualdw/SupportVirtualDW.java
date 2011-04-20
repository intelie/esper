package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;

import java.util.Set;

public class SupportVirtualDW implements VirtualDataWindow {

    private final VirtualDataWindowContext context;
    private Set<Object> data;
    private boolean destroyed;
    private VirtualDataWindowLookupContext lastRequestedIndex;
    private Object[] lastAccessKeys;
    private EventBean[] lastUpdateNew;
    private EventBean[] lastUpdateOld;

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
}
