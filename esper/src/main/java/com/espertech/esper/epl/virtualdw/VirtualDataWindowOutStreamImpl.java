package com.espertech.esper.epl.virtualdw;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.hook.VirtualDataWindowOutStream;
import com.espertech.esper.view.ViewSupport;

public class VirtualDataWindowOutStreamImpl implements VirtualDataWindowOutStream {
    private ViewSupport view;

    public void setView(ViewSupport view) {
        this.view = view;
    }

    public void update(EventBean[] newData, EventBean[] oldData) {
        view.updateChildren(newData, oldData);
    }
}
