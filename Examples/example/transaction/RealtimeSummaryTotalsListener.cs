using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.transaction
{
    public class RealtimeSummaryTotalsListener
    {
        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents == null)
            {
                // we don't care about events leaving the window (old events)            
                return;
            }

            EventBean eventBean = newEvents[0];
            log.Debug(
                    " Totals minAC=" + eventBean["minLatencyAC"] +
                    " maxAC=" + eventBean["maxLatencyAC"] +
                    " avgAC=" + eventBean["avgLatencyAC"] +
                    " minAB=" + eventBean["minLatencyAB"] +
                    " maxAB=" + eventBean["maxLatencyAB"] +
                    " avgAB=" + eventBean["avgLatencyAB"] +
                    " minBC=" + eventBean["minLatencyBC"] +
                    " maxBC=" + eventBean["maxLatencyBC"] +
                    " avgBC=" + eventBean["avgLatencyBC"]
                    );
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}