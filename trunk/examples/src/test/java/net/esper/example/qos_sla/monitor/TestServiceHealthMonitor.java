package net.esper.example.qos_sla.monitor;

import net.esper.example.qos_sla.eventbean.*;
import net.esper.client.*;
import junit.framework.TestCase;

public class TestServiceHealthMonitor extends TestCase
{
    private EPRuntime runtime;

    public void setUp()
    {
        new ServiceHealthMonitor();
        runtime = EPServiceProviderManager.getDefaultProvider().getEPRuntime();
    }

    public void testLatencyAlert()
    {
        sendEvent(false);
        sendEvent(false);
        sendEvent(false);
    }

    private void sendEvent(boolean success)
    {
        OperationMeasurement measurement = new OperationMeasurement("myService", "myCustomer", 10000, success);
        runtime.sendEvent(measurement);
    }
}
