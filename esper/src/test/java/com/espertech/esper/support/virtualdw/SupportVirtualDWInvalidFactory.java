package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;
import com.espertech.esper.client.hook.VirtualDataWindowContext;

public class SupportVirtualDWInvalidFactory implements VirtualDataWindowFactory {

    public VirtualDataWindow create(VirtualDataWindowContext context) {
        return new SupportVirtualDWInvalid();
    }
}
