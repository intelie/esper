using System;

using net.esper.schedule;

using org.apache.commons.logging;

namespace net.esper.support.schedule
{
	
	public class SupportScheduleCallback : ScheduleCallback
	{
		public static int CallbackOrderNum
		{
			set
			{
				SupportScheduleCallback.orderAllCallbacks = value;
			}
			
		}
		private static int orderAllCallbacks;
		
		private int orderTriggered = 0;
		
		public virtual void  scheduledTrigger()
		{
			log.Debug(".scheduledTrigger");
			orderAllCallbacks++;
			orderTriggered = orderAllCallbacks;
		}
		
		public virtual int clearAndGetOrderTriggered()
		{
			int result = orderTriggered;
			orderTriggered = 0;
			return result;
		}

        private static readonly Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
	}
}
