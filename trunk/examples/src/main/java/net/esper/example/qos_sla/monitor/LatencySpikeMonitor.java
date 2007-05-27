package net.esper.example.qos_sla.monitor;

import net.esper.client.*;
import net.esper.example.qos_sla.eventbean.OperationMeasurement;

public class LatencySpikeMonitor
{
    private LatencySpikeMonitor() {};
    
    public static void start()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        EPStatement latencyAlert = admin.createPattern(
                "every alert=" + OperationMeasurement.class.getName() + "(latency > 20000)");

        latencyAlert.addListener(new LatencySpikeListener());
    }
}
