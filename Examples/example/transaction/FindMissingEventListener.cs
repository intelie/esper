using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.transaction
{
    public class FindMissingEventListener
    {
        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (oldEvents == null)
            {
                // we don't care about events entering the window (new events)
                // this is because we must wait for C to arri
                return;
            }

            // Missing C events can be reported either through A or through B
            // We assume that duplicates are ok, if not, then streams A and B could be joined and then fed,
            // or duplicates could be removed via another statement as well.
            TxnEventA eventA = (TxnEventA)oldEvents[0]["A"];
            TxnEventB eventB = (TxnEventB)oldEvents[0]["B"];

            if (eventA != null)
            {
                log.Debug("Missing TxnEventC event detected for TxnEventA " + eventA.ToString());
            }
            else
            {
                log.Debug("Missing TxnEventC event detected for TxnEventB " + eventB.ToString());
            }
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}