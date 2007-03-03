using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.transaction
{
    public class RealtimeSummaryGroupListener
    {
        private String groupIdentifier;

        public RealtimeSummaryGroupListener(String groupIdentifier)
        {
            this.groupIdentifier = groupIdentifier;
        }

        public void update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents == null)
            {
                // we don't care about events leaving the window (old events)
                return;
            }

            EventBean eventBean = newEvents[0];
            log.Debug(
                    groupIdentifier + "=" + eventBean[groupIdentifier] +
                    " minAC=" + eventBean["minLatency"] +
                    " maxAC=" + eventBean["maxLatency"] +
                    " avgAC=" + eventBean["avgLatency"]
                    );
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}