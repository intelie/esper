package net.esper.example.marketdatafeed;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RateReportingListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        logRate(newEvents[0]);
        logRate(newEvents[1]);
    }

    private void logRate(EventBean event)
    {
        log.info("Current rate for feed " + event.get("feed").toString() +
                  " is " + event.get("cnt"));
    }

    private static final Log log = LogFactory.getLog(RateReportingListener.class);
}
