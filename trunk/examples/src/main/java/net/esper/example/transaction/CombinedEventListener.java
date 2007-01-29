package net.esper.example.transaction;

import net.esper.client.*;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CombinedEventListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)
            return;
        }

        EventBean event = newEvents[0];
        log.debug("Combined event detected " +
                " transactionId=" + event.get("transactionId") +
                " customerId=" + event.get("customerId") +
                " supplierId=" + event.get("supplierId") +
                " latencyAC=" + event.get("latencyAC") +
                " latencyAB=" + event.get("latencyAB") +
                " latencyBC=" + event.get("latencyBC")
                );
    }

    private static final Log log = LogFactory.getLog(CombinedEventListener.class);
}
