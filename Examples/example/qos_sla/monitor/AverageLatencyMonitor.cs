using System;

using net.esper.client;
using net.esper.example.qos_sla.eventbean;

namespace net.esper.example.qos_sla.monitor
{
    public class AverageLatencyMonitor
    {
        public AverageLatencyMonitor()
        {
            EPAdministrator admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            EPStatement statView = admin.CreateEQL(
                    "select * from " + typeof(OperationMeasurement).FullName +
                    ".std:groupby('customerId').std:groupby('operationName')" +
                    ".win:length(100).stat:uni('latency')");

            statView.AddListener(new AverageLatencyListener(10000).Update);
        }
    }
}