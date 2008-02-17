package com.espertech.esper.example.marketdatafeed;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RateReportingListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents.length > 0)
        {
            logRate(newEvents[0]);
        }
        if (newEvents.length > 1)
        {
            logRate(newEvents[1]);
        }
    }

    private void logRate(EventBean event)
    {
        log.info("Current rate for feed " + event.get("feed").toString() +
                  " is " + event.get("cnt"));
    }

    private static final Log log = LogFactory.getLog(RateReportingListener.class);
}
