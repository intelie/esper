package com.espertech.esper.example.qos_sla.monitor;

import com.espertech.esper.client.UpdateListener;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.example.qos_sla.eventbean.OperationMeasurement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LatencySpikeListener implements UpdateListener
{
    public void update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        OperationMeasurement event = (OperationMeasurement) newEvents[0].get("alert");
        
        log.info("Alert, for operation '" + event.getOperationName() +
                "' and customer '" + event.getCustomerId() + "'" +
                " latency was " + event.getLatency());
    }

    private static final Log log = LogFactory.getLog(LatencySpikeListener.class);
}