using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.transaction
{
    public class CombinedEventListener
    {
        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents == null)
            {
                // we don't care about events leaving the window (old events)
                return;
            }

            EventBean eventBean = newEvents[0];
            log.Debug("Combined event detected " +
                    " transactionId=" + eventBean["transactionId"] +
                    " customerId=" + eventBean["customerId"] +
                    " supplierId=" + eventBean["supplierId"] +
                    " latencyAC=" + eventBean["latencyAC"] +
                    " latencyAB=" + eventBean["latencyAB"] +
                    " latencyBC=" + eventBean["latencyBC"]
                    );
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
