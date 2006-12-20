package net.esper.example.transaction;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FindMissingEventListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        if (oldEvents == null)
        {
            // we don't care about events entering the window (new events)
            // this is because we must wait for C to arri
            return;
        }

        // Missing C events can be reported either through A or through B
        // We assume that duplicates are ok, if not, then streams A and B could be joined and then fed,
        // or duplicates could be removed via another statement as well.
        TxnEventA eventA = (TxnEventA) oldEvents[0].get("A");
        TxnEventB eventB = (TxnEventB) oldEvents[0].get("B");

        if (eventA != null)
        {
            log.debug("Missing TxnEventC event detected for TxnEventA " + eventA.toString());
        }
        else
        {
            log.debug("Missing TxnEventC event detected for TxnEventB " + eventB.toString());
        }
    }

    private static final Log log = LogFactory.getLog(FindMissingEventListener.class);
}
