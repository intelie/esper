using System;

using net.esper.events;
using net.esper.filter;

namespace net.esper.support.filter
{
	
	public class SupportFilterCallback : FilterCallback
	{
		virtual public int CountInvoked
		{
			get
			{
				return countInvoked;
			}
			
			set
			{
				this.countInvoked = value;
			}
			
		}
		virtual public EventBean LastEvent
		{
			get
			{
				return lastEvent;
			}
			
			set
			{
				this.lastEvent = value;
			}
			
		}
		virtual public int AndResetCountInvoked
		{
			get
			{
				int count = countInvoked;
				countInvoked = 0;
				return count;
			}
			
		}
		private int countInvoked;
		private EventBean lastEvent;
		
		public virtual void  matchFound(EventBean _event)
		{
			countInvoked++;
			lastEvent = _event;
		}
	}
}
