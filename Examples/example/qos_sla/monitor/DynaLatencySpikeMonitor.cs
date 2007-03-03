using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.qos_sla.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
    public class DynaLatencySpikeMonitor
    {
        private static EPAdministrator admin;

        private EPStatement spikeLatencyAlert;

        public static void Start()
        {
            admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            String eventName = typeof(LatencyLimit).FullName;
            EPStatement latencyAlert = admin.CreatePattern("every limit=" + eventName);

            latencyAlert.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    LatencyLimit limitEvent = (LatencyLimit)newEvents[0]["Limit"];
                    new DynaLatencySpikeMonitor(limitEvent);
                }));
        }

        public DynaLatencySpikeMonitor(LatencyLimit limit)
        {
            log.Debug("New limit, for operation '" + limit.OperationName +
                    "' and customer '" + limit.CustomerId + "'" +
                    " setting threshold " + limit.LatencyThreshold);

            String filter = "operationName='" + limit.OperationName +
                            "',customerId='" + limit.CustomerId + "'";

            // Alert specific to operation and customer
            spikeLatencyAlert = admin.CreatePattern("every alert=" +
                    typeof(OperationMeasurement).FullName +
                    "(" + filter + ", latency>" + limit.LatencyThreshold + ")");
            spikeLatencyAlert.AddListener(new LatencySpikeListener().Update);

            // Stop pattern when the threshold changes
            String eventName = typeof(LatencyLimit).FullName;
            EPStatement stopPattern = admin.CreatePattern(eventName + "(" + filter + ")");

            stopPattern.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    spikeLatencyAlert.Stop();
                }));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
