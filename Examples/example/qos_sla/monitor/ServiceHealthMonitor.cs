using System;

using net.esper.client;
using net.esper.events;
using net.esper.example.qos_sla.eventbean;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
    public class ServiceHealthMonitor
    {
        public ServiceHealthMonitor()
        {
            EPAdministrator admin = EPServiceProviderManager.GetDefaultProvider().EPAdministrator;

            String eventBean = typeof(OperationMeasurement).FullName;

            EPStatement statView = admin.CreatePattern("every (" +
                    eventBean + "(success=false)->" +
                    eventBean + "(success=false)->" +
                    eventBean + "(success=false))");

            statView.AddListener(new UpdateListener(
                delegate(EventBean[] newEvents, EventBean[] oldEvents)
                {
                    log.Debug(".update Alert, detected 3 erros in a row");
                }));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
