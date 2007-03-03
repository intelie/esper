using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.qos_sla.monitor
{
    public class AverageLatencyListener
    {
        private long alertThreshold;

        public AverageLatencyListener(long alertThreshold)
        {
            this.alertThreshold = alertThreshold;
        }

        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            long count = (long)newEvents[0]["Count"];
            double avg = (double)newEvents[0]["Average"];

            if ((count < 100) || (avg < alertThreshold))
            {
                return;
            }

            String operation = (String)newEvents[0]["OperationName"];
            String customer = (String)newEvents[0]["CustomerId"];

            log.Debug("Alert, for operation '" + operation +
                    "' and customer '" + customer + "'" +
                    " average latency was " + avg);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}