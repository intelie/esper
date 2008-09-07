package com.espertech.esper.regression.support;

import com.espertech.esper.support.bean.SupportMarketDataBean;

public class EventSendDesc
{
    private SupportMarketDataBean event;
    private String eventDesc;

    public EventSendDesc(SupportMarketDataBean event, String eventDesc) {
        this.event = event;
        this.eventDesc = eventDesc;
    }

    public SupportMarketDataBean getEvent() {
        return event;
    }

    public String getEventDesc() {
        return eventDesc;
    }
}
