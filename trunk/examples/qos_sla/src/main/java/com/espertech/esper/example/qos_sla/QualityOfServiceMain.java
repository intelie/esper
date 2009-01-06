package com.espertech.esper.example.qos_sla;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.example.qos_sla.monitor.*;
import com.espertech.esper.example.qos_sla.eventbean.LatencyLimit;
import com.espertech.esper.example.qos_sla.eventbean.OperationMeasurement;

import java.util.Random;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QualityOfServiceMain
{
    private static final Log log = LogFactory.getLog(QualityOfServiceMain.class);

    public static void main(String[] args)
    {
        log.info("Setting up EPL");
        DynaLatencySpikeMonitor.start();
        AverageLatencyMonitor.start();
        ErrorRateMonitor.start();
        LatencySpikeMonitor.start();
        ServiceHealthMonitor.start();
        SpikeAndErrorMonitor.start();

        EPRuntime runtime = EPServiceProviderManager.getDefaultProvider().getEPRuntime();

        log.info("Sending new limits");
        String services[] = {"s0", "s1", "s2"};
        String customers[] = {"c0", "c1", "c2"};
        long limitSpike[] = {15000, 10000, 10040};

        // Set up limits for 3 services/customer combinations
        for (int i = 0; i < services.length; i++)
        {
            LatencyLimit limit = new LatencyLimit(services[i], customers[i], limitSpike[i]);
            runtime.sendEvent(limit);
        }

        // Send events
        log.info("Sending 100k of random events");
        Random random = new Random();
        for (int i = 0; i < 100000; i++)
        {
            for (int index = 0; index < services.length; index++)
            {
                int delta = random.nextInt(1000000) - 999700;
                OperationMeasurement measurement = new OperationMeasurement(services[index], customers[index],
                        9950 + delta, true);
                runtime.sendEvent(measurement);
            }
        }

        log.info("Finally, sending a new limit and a artificial spike");
        // Send a new limit
        LatencyLimit limit = new LatencyLimit(services[1], customers[1], 8000);
        runtime.sendEvent(limit);

        // Send a new spike
        OperationMeasurement measurement = new OperationMeasurement(services[1], customers[1], 8001, true);
        runtime.sendEvent(measurement);
    }
}

