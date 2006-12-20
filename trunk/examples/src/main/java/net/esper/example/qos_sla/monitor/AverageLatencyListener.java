package net.esper.example.qos_sla.monitor;

import net.esper.client.UpdateListener;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AverageLatencyListener implements UpdateListener
{
    private long alertThreshold;

    public AverageLatencyListener(long alertThreshold)
    {
        this.alertThreshold = alertThreshold;
    }

    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        long count = (Long) newEvents[0].get("count");
        double avg = (Double) newEvents[0].get("average");

        if ((count < 100) || (avg < alertThreshold))
        {
            return;
        }

        String operation = (String) newEvents[0].get("operationName");
        String customer = (String) newEvents[0].get("customerId");

        log.debug("Alert, for operation '" + operation +
                "' and customer '" + customer + "'" +
                " average latency was " + avg);
    }

    private static final Log log = LogFactory.getLog(AverageLatencyListener.class);
}