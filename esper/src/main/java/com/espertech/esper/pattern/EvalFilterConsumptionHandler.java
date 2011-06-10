package com.espertech.esper.pattern;

import com.espertech.esper.client.EventBean;

public class EvalFilterConsumptionHandler {

    private EventBean lastEvent;

    public void setLastEvent(EventBean lastEvent) {
        this.lastEvent = lastEvent;
    }

    public EventBean getLastEvent() {
        return lastEvent;
    }
}
