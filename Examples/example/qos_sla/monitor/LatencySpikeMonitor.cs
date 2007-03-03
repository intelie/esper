using System;

using net.esper.client;
using net.esper.example.qos_sla.eventbean;

namespace net.esper.example.qos_sla.monitor
{
    public class LatencySpikeMonitor
    {
        private LatencySpikeMonitor()
        {
        }

        public static void start()
        {
            EPAdministrator admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            EPStatement latencyAlert = admin.CreatePattern(
                    "every alert=" + typeof(OperationMeasurement).FullName + "(latency > 20000)");

            latencyAlert.AddListener(new LatencySpikeListener().Update);
        }
    }
}