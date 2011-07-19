package com.espertech.esper.example.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;

public class SampleVirtualDataWindow implements VirtualDataWindow {

    private final VirtualDataWindowContext context;

    public SampleVirtualDataWindow(VirtualDataWindowContext context) {
        this.context = context;
    }

    public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {

        // Place any code that interrogates the hash-index and btree-index fields here.

        // Return the index representation.
        return new SampleVirtualDataWindowLookup(context);
    }

    public void update(EventBean[] newData, EventBean[] oldData) {

        // This sample simply posts into the insert and remove stream what is received.
        context.getOutputStream().update(newData, oldData);
    }

    public void destroy() {
        // Called when the named window is stopped or destroyed.
        // This sample does not need to clean up resources.
    }
}
