package net.esper.example.transaction;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RealtimeSummaryGroupListener implements UpdateListener
{
    private String groupIdentifier;

    public RealtimeSummaryGroupListener(String groupIdentifier)
    {
        this.groupIdentifier = groupIdentifier;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (newEvents == null)
        {
            // we don't care about events leaving the window (old events)
            return;
        }

        EventBean event = newEvents[0];
        log.debug(
                groupIdentifier + "=" + event.get(groupIdentifier) +
                " minAC=" + event.get("minLatency") +
                " maxAC=" + event.get("maxLatency") +
                " avgAC=" + event.get("avgLatency")
                );
    }

    private static final Log log = LogFactory.getLog(RealtimeSummaryGroupListener.class);
}
