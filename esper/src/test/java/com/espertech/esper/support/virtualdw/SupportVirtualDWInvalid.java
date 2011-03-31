package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowLookup;
import com.espertech.esper.client.hook.VirtualDataWindowLookupContext;

public class SupportVirtualDWInvalid implements VirtualDataWindow {

    public VirtualDataWindowLookup getLookup(VirtualDataWindowLookupContext desc) {
        return null;
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
    }

    public void destroy() {
    }
}
