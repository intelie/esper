using System;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.exec
{
	
	/// <summary> Strategy for looking up, in some sort of table or index, an event, potentially based on the
	/// events properties, and returning a set of matched events.
	/// </summary>
	public interface TableLookupStrategy
	{
		/// <summary> Returns matched events for a event to look up for. Never returns an empty result set,
		/// always returns null to indicate no results.
		/// </summary>
		/// <param name="event">to look up
		/// </param>
		/// <returns> set of matching events, or null if none matching
		/// </returns>
    
        ISet<EventBean> lookup(EventBean ev);
	}
}