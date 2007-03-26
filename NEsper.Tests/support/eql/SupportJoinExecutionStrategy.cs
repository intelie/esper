using System;

using net.esper.eql.join;
using net.esper.events;

namespace net.esper.support.eql
{
	
	public class SupportJoinExecutionStrategy : JoinExecutionStrategy
	{
		virtual public EventBean[][] LastNewDataPerStream
		{
			get
			{
				return lastNewDataPerStream;
			}
			
		}
		virtual public EventBean[][] LastOldDataPerStream
		{
			get
			{
				return lastOldDataPerStream;
			}
			
		}
		private EventBean[][] lastNewDataPerStream;
		private EventBean[][] lastOldDataPerStream;
		
		public virtual void  Join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream)
		{
			lastNewDataPerStream = newDataPerStream;
			lastOldDataPerStream = oldDataPerStream;
		}
	}
}
