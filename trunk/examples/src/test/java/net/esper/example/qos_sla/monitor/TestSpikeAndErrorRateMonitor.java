package net.esper.example.qos_sla.monitor;

import net.esper.example.qos_sla.eventbean.*;
import net.esper.client.*;
import junit.framework.TestCase;

public class TestSpikeAndErrorRateMonitor extends TestCase
{
    private EPRuntime runtime;

    public void setUp()
    {
        new SpikeAndErrorMonitor();
        runtime = EPServiceProviderManager.getDefaultProvider().getEPRuntime();
    }

    public void testAlert() throws Exception
    {
        sendEvent("s1", 30000, false);
    }

    private void sendEvent(String service, long latency, boolean success)
    {
        OperationMeasurement measurement = new OperationMeasurement(service, "myCustomer", latency, success);
        runtime.sendEvent(measurement);
    }
}
