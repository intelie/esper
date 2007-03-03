using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.qos_sla.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
public class LatencySpikeListener
{
    public void Update(EventBean[] newEvents, EventBean[] oldEvents)
    {
        OperationMeasurement eventBean = (OperationMeasurement) newEvents[0]["Alert"];
        
        log.Info("Alert, for operation '" + eventBean.OperationName +
                "' and customer '" + eventBean.CustomerId + "'" +
                " latency was " + eventBean.Latency);
    }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
}
}