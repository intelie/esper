package com.espertech.esper.client.hook;

public class VirtualDataWindowEventStopWindow extends VirtualDataWindowEvent {

    private final String namedWindowName;

    public VirtualDataWindowEventStopWindow(String namedWindowName) {
        this.namedWindowName = namedWindowName;
    }

    public String getNamedWindowName() {
        return namedWindowName;
    }
}
