package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;

public class SupportVirtualDWFactory implements VirtualDataWindowFactory {

    public VirtualDataWindow create(VirtualDataWindowContext context) {
        return new SupportVirtualDW(context);
    }
}
