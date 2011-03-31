package com.espertech.esper.example.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SampleVirtualDataWindowLookup implements VirtualDataWindowLookup {

    private final VirtualDataWindowContext context;

    public SampleVirtualDataWindowLookup(VirtualDataWindowContext context) {
        this.context = context;
    }

    public Set<EventBean> lookup(Object[] keys) {
        // Add code to interogate lookup-keys here.

        // Create sample event.
        Map<String, Object> eventData = new HashMap<String, Object>();
        eventData.put("key1", "sample1");
        eventData.put("key2", "sample2");
        eventData.put("value1", 100);
        eventData.put("value2", 1.5d);
        EventBean event = context.getEventFactory().wrap(eventData);
        return Collections.singleton(event);
    }
}
