package com.espertech.esper.support.virtualdw;

import com.espertech.esper.client.hook.VirtualDataWindow;
import com.espertech.esper.client.hook.VirtualDataWindowContext;
import com.espertech.esper.client.hook.VirtualDataWindowFactory;

import java.util.ArrayList;
import java.util.List;

public class SupportVirtualDWFactory implements VirtualDataWindowFactory {

    private static List<SupportVirtualDW> windows = new ArrayList<SupportVirtualDW>();

    public static List<SupportVirtualDW> getWindows() {
        return windows;
    }

    public VirtualDataWindow create(VirtualDataWindowContext context) {
        SupportVirtualDW vdw = new SupportVirtualDW(context);
        windows.add(vdw);
        return vdw;
    }
}
