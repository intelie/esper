package net.esper.example.qos_sla.monitor;

import net.esper.client.*;
import net.esper.example.qos_sla.eventbean.*;
import net.esper.event.EventBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DynaLatencySpikeMonitor
{
    private static EPAdministrator admin;

    private EPStatement spikeLatencyAlert;

    public static void start()
    {
        admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        String event = LatencyLimit.class.getName();
        EPStatement latencyAlert = admin.createPattern("every limit=" + event);

        latencyAlert.addListener(new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                LatencyLimit limitEvent = (LatencyLimit) newEvents[0].get("limit");
                new DynaLatencySpikeMonitor(limitEvent);
            }
        });
    }

    public DynaLatencySpikeMonitor(LatencyLimit limit)
    {
        log.debug("New limit, for operation '" + limit.getOperationName() +
                "' and customer '" + limit.getCustomerId() + "'" +
                " setting threshold " + limit.getLatencyThreshold());

        String filter = "operationName='" + limit.getOperationName() +
                        "',customerId='" + limit.getCustomerId() + "'";

        // Alert specific to operation and customer
        spikeLatencyAlert = admin.createPattern("every alert=" +
                OperationMeasurement.class.getName() +
                "(" + filter + ", latency>" + limit.getLatencyThreshold() + ")");
        spikeLatencyAlert.addListener(new LatencySpikeListener());

        // Stop pattern when the threshold changes
        String event = LatencyLimit.class.getName();
        EPStatement stopPattern = admin.createPattern(event + "(" + filter + ")");

        stopPattern.addListener(new UpdateListener() {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                spikeLatencyAlert.stop();
            }
        });
    }

    private static final Log log = LogFactory.getLog(DynaLatencySpikeMonitor.class);
}
