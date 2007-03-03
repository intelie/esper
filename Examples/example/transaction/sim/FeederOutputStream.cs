using System;
using System.Collections.Generic;
using System.IO;

using net.esper.client;
using net.esper.client.time;
using net.esper.compat;
using net.esper.example.transaction;

using org.apache.commons.logging;

namespace net.esper.example.transaction.sim
{
    public class FeederOutputStream : OutputStream
    {
        private readonly EPRuntime runtime;
        private readonly long startTimeMSec;

        // We keep increasing the current time to simulate a 30 minute window
        private long currentTimeMSec;

        public FeederOutputStream(EPRuntime runtime)
        {
            this.runtime = runtime;
            startTimeMSec = DateTimeHelper.CurrentTimeMillis;
            currentTimeMSec = startTimeMSec;
        }

        public void Output(IList<TxnEventBase> bucket)
        {
            log.Info(".output Feeding " + bucket.Count + " events");

            long startTimeMSec = currentTimeMSec;
            long timePeriodLength = FindMissingEventStmt.TIME_WINDOW_TXNC_IN_SEC * 1000;
            long endTimeMSec = startTimeMSec + timePeriodLength;
            SendTimerEvent(startTimeMSec);

            int count = 0, total = 0;
            foreach (TxnEventBase eventBean in bucket)
            {
                runtime.SendEvent(eventBean);
                count++;
                total++;

                if (count % 1000 == 0)
                {
                    SendTimerEvent(startTimeMSec + timePeriodLength * total / bucket.Count);
                    count = 0;
                }

                if (count == 10000)
                {
                    log.Info(".output Completed " + total + " events");
                    count = 0;
                }
            }

            SendTimerEvent(endTimeMSec);
            currentTimeMSec = endTimeMSec;

            log.Info(".output Completed bucket");
        }

        private void SendTimerEvent(long msec)
        {
            log.Info(".sendTimerEvent Setting time to now + " + (msec - startTimeMSec));
        }

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
