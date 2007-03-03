using System;

using net.esper.client;
using net.esper.events;

using org.apache.commons.logging;

namespace net.esper.example.marketdatafeed
{
    public class RateFalloffAlertListener
    {
        public void Update(EventBean[] newEvents, EventBean[] oldEvents)
        {
            if (newEvents == null)
            {
                return; // ignore old events for events leaving the window
            }

            EventBean eventBean = newEvents[0];

            log.Info("Rate fall-off detected for feed=" + eventBean["Feed"].ToString() +
                      " and rate=" + eventBean["FeedCnt"] +
                      " and average=" + eventBean["AvgCnt"]);
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}