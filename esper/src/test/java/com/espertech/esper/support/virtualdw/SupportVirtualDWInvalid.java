package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowEvent;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;

import java.util.Iterator;

public class SupportVirtualDWInvalid implements VirtualDataWindow {

    public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {
        return null;
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
    }

    public void destroy() {
    }

    public Iterator<EventBean> iterator() {
        return null;
    }

    public void handleEvent(VirtualDataWindowEvent event) {
    }
}
