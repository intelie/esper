using System;
using System.Threading;

using net.esper.timer;

using org.apache.commons.logging;

namespace net.esper.support.timer
{
	public class SupportTimerCallback : net.esper.timer.TimerCallback
	{
		virtual public int Count
		{
			get
            {
				return (int) Interlocked.Read( ref numInvoked ) ;
			}
		}

		virtual public int GetAndResetCount()
		{
            int count = (int) Interlocked.Exchange(ref numInvoked, 0);
            return count;
		}

        private long numInvoked;
		
		public virtual void TimerCallback()
		{
            int current = (int) Interlocked.Increment(ref numInvoked);
            log.Debug(".timerCallback numInvoked=" + current + " thread=" + Thread.CurrentThread);
		}
		
		private static readonly Log log = LogFactory.GetLog(typeof(SupportTimerCallback));
	}
}
