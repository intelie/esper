package net.esper.example.transaction;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RealtimeSummaryTotalsListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)            
            return;
        }

        EventBean event = newEvents[0];
        log.debug(
                " Totals minAC=" + event.get("minLatencyAC") +
                " maxAC=" + event.get("maxLatencyAC") +
                " avgAC=" + event.get("avgLatencyAC") +
                " minAB=" + event.get("minLatencyAB") +
                " maxAB=" + event.get("maxLatencyAB") +
                " avgAB=" + event.get("avgLatencyAB") +
                " minBC=" + event.get("minLatencyBC") +
                " maxBC=" + event.get("maxLatencyBC") +
                " avgBC=" + event.get("avgLatencyBC")
                );
    }

    private static final Log log = LogFactory.getLog(RealtimeSummaryTotalsListener.class);
}
