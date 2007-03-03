using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.qos_sla.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
    public class SpikeAndErrorMonitor
    {
        public SpikeAndErrorMonitor()
        {
            EPAdministrator admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            String eventName = typeof(OperationMeasurement).FullName;

            EPStatement myPattern = admin.CreatePattern(
                    "every (spike=" + eventName + "(latency>20000) or error=" + eventName + "(success=false))");

            myPattern.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    OperationMeasurement spike = (OperationMeasurement)newEvents[0]["Spike"];
                    OperationMeasurement error = (OperationMeasurement)newEvents[0]["Error"];

                    if (spike != null)
                    {
                        log.Debug(".update spike=" + spike.ToString());
                    }
                    if (error != null)
                    {
                        log.Debug(".update error=" + error.ToString());
                    }
                }));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}