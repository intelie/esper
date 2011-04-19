package com.espertech.esper.example.virtualdw;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowContext;

public class SampleVirtualDataWindowFactory implements VirtualDataWindowFactory {

    public VirtualDataWindow create(VirtualDataWindowContext context) {
        return new SampleVirtualDataWindow(context);
    }
}
