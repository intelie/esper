using System;
using System.Threading;

using net.esper.timer;

using org.apache.commons.logging;

namespace net.esper.support.timer
{
	public sealed class SupportTimerCallback
	{
		public int Count
		{
			get
            {
				return (int) Interlocked.Read( ref numInvoked ) ;
			}
		}

		public int GetAndResetCount()
		{
            int count = (int) Interlocked.Exchange(ref numInvoked, 0);
            return count;
		}

        private long numInvoked;

		public void TimerCallback()
		{
            int current = (int) Interlocked.Increment(ref numInvoked);

            if (log.IsDebugEnabled)
            {
                log.Debug(".timerCallback numInvoked=" + current + " thread=" + Thread.CurrentThread);
            }
		}
		
		private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
