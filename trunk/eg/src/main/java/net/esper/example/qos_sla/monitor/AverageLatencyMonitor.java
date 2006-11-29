package net.esper.example.qos_sla.monitor;

import net.esper.client.*;
import net.esper.example.qos_sla.eventbean.OperationMeasurement;

public class AverageLatencyMonitor
{
    public AverageLatencyMonitor()
    {
        EPAdministrator admin = EPServiceProviderManager.getDefaultProvider().getEPAdministrator();

        EPStatement statView = admin.createEQL(
                "select * from " + OperationMeasurement.class.getName() +
                ".std:groupby('customerId').std:groupby('operationName')" +
                ".win:length(100).stat:uni('latency')");

        statView.addListener(new AverageLatencyListener(10000));
    }
}
