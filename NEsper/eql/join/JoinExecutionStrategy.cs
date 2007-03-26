using System;
using EventBean = net.esper.events.EventBean;
namespace net.esper.eql.join
{
	
	/// <summary> Strategy for executing a join.</summary>
	public interface JoinExecutionStrategy
	{
		/// <summary> Execute join. The first dimension in the 2-dim arrays is the stream that generated the events,
		/// and the second dimension is the actual events generated.
		/// </summary>
		/// <param name="newDataPerStream">new events for each stream
		/// </param>
		/// <param name="oldDataPerStream">old events for each stream
		/// </param>
		void  Join(EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream);
	}
}