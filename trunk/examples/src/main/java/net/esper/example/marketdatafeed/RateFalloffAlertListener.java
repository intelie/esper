package net.esper.example.marketdatafeed;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RateFalloffAlertListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            return; // ignore old events for events leaving the window
        }

        EventBean event = newEvents[0];

        log.info("Rate fall-off detected for feed=" + event.get("feed").toString() +
                  " and rate=" + event.get("feedCnt") +
                  " and average=" + event.get("avgCnt"));
    }

    private static final Log log = LogFactory.getLog(RateFalloffAlertListener.class);
}
