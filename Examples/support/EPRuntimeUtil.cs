using System;
using System.Threading;

using net.esper.client;
using net.esper.compat;

using org.apache.commons.logging;

namespace net.esper.support
{
	/// <summary>
	/// Utility methods for monitoring a EPRuntime instance.
	/// </summary>

	public class EPRuntimeUtil
	{
	    public static bool AwaitCompletion(
		EPRuntime epRuntime,
	        int numEventsExpected,
	        int numSecAwait,
	        int numSecThreadSleep,
	        int numSecThreadReport)
	    {
	        log.Info(".awaitCompletion Waiting for completion, expecting " + numEventsExpected +
	                 " events within " + numSecAwait + " sec");
	        
	        int secondsWaitTotal = numSecAwait;
	        long lastNumEventsProcessed = 0;
	        int secondsUntilReport = 0;
	
	        long startTimeMSec = DateTimeHelper.CurrentTimeMillis;
	        long endTimeMSec = 0;
	        long numPerSec = 0;
	
	        while (secondsWaitTotal > 0)
	        {
                Thread.Sleep(numSecThreadSleep * 1000);
	
	            secondsWaitTotal -= numSecThreadSleep;
	            secondsUntilReport += numSecThreadSleep;
	            long currNumEventsProcessed = epRuntime.NumEventsReceived;
	
	            if (secondsUntilReport > numSecThreadReport)
	            {
	                numPerSec = (currNumEventsProcessed - lastNumEventsProcessed) / numSecThreadReport;
	                log.Info(".awaitCompletion received=" + epRuntime.NumEventsReceived +
	                         "  emitted=" + epRuntime.NumEventsEmitted +
	                         "  processed=" + currNumEventsProcessed +
	                         "  perSec=" + numPerSec);
	                lastNumEventsProcessed = currNumEventsProcessed;
	                secondsUntilReport = 0;
	            }
	
	            // Completed loop if the total event count has been reached
	            if (epRuntime.NumEventsReceived == numEventsExpected)
	            {
	                endTimeMSec = DateTimeHelper.CurrentTimeMillis;
	                break;
	            }
	        }
	
	        if (endTimeMSec == 0)
	        {
	            log.Info(".awaitCompletion Not completed within " + numSecAwait + " seconds");
	            return false;
	        }
	
	        long totalUnitsProcessed = epRuntime.NumEventsReceived;
	        long deltaTimeSec = (endTimeMSec - startTimeMSec) / 1000;
	
	        numPerSec = 0;
	        if (deltaTimeSec > 0)
	        {
	            numPerSec = (totalUnitsProcessed) / deltaTimeSec;
	        }
	        else
	        {
	            numPerSec = -1;
	        }
	
	        log.Info(".awaitCompletion Completed, sec=" + deltaTimeSec + "  avgPerSec=" + numPerSec);
	
	        long numReceived = epRuntime.NumEventsReceived;
	        long numReceivedPerSec = 0;
	        if (deltaTimeSec > 0)
	        {
	            numReceivedPerSec = (numReceived) / deltaTimeSec;
	        }
	        else
	        {
	            numReceivedPerSec = -1;
	        }
	
	        log.Info(".awaitCompletion Runtime reports, numReceived=" + numReceived +
	                 "  numProcessed=" + epRuntime.NumEventsReceived +
	                 "  perSec=" +  numReceivedPerSec +
	                 "  numEmitted=" + epRuntime.NumEventsEmitted
	                 );
	
	        return true;
	    }
	    
        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
