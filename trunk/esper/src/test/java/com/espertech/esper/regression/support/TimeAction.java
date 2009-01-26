package com.espertech.esper.regression.support;

import com.espertech.esper.support.bean.SupportMarketDataBean;

import java.util.List;
import java.util.ArrayList;

public class TimeAction
{
    private List<EventSendDesc> events;
    private String actionDesc;

    public TimeAction() {
        events = new ArrayList<EventSendDesc>();
    }

    public void add(SupportMarketDataBean event, String eventDesc)
    {
        events.add(new EventSendDesc(event, eventDesc));
    }

    public void setActionDesc(String actionDesc) {
        this.actionDesc = actionDesc;
    }

    public List<EventSendDesc> getEvents() {
        return events;
    }

    public String getActionDesc() {
        return actionDesc;
    }
}

