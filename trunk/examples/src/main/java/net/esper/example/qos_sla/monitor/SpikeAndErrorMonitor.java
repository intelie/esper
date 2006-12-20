package net.esper.example.qos_sla.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.esper.client.*;
import net.esper.example.qos_sla.eventbean.OperationMeasurement;
import net.esper.event.EventBean;

public class SpikeAndErrorMonitor
{
    public SpikeAndErrorMonitor()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        String eventName = OperationMeasurement.class.getName();

        EPStatement myPattern = admin.createPattern(
                "every (spike=" + eventName + "(latency>20000) or error=" + eventName + "(success=false))");

        myPattern.addListener(new UpdateListener()
        {
            public void update(EventBean[] newEvents, EventBean[] oldEvents)
            {
                OperationMeasurement spike = (OperationMeasurement) newEvents[0].get("spike");
                OperationMeasurement error = (OperationMeasurement) newEvents[0].get("error");

                if (spike != null)
                {
                    log.debug(".update spike=" + spike.toString());
                }
                if (error != null)
                {
                    log.debug(".update error=" + error.toString());
                }
            }
        });
    }
    
    private static final Log log = LogFactory.getLog(SpikeAndErrorMonitor.class);
}
