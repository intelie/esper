using System;
using EventBean = net.esper.events.EventBean;
namespace net.esper.eql.join.table
{
	
	/// <summary> Table of events allowing add and remove. Lookup in table is coordinated
	/// through the underlying implementation.
	/// </summary>
	public interface EventTable
	{
		/// <summary> Add events to table.</summary>
		/// <param name="events">to add
		/// </param>
		void  Add(EventBean[] events);
		
		/// <summary> Remove events from table.</summary>
		/// <param name="events">to remove
		/// </param>
		void  Remove(EventBean[] events);
	}
}
