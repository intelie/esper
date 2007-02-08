using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.collection;
using net.esper.util;

namespace net.esper.view
{
	/// <summary>
    /// Interface for views that poll data based on information from other streams.
    /// </summary>
	
    public interface HistoricalEventViewable : Viewable, ValidatedView, StopCallback
	{
		/// <summary> Poll for stored historical or reference data using events per stream and
		/// returing for each event-per-stream row a separate list with events
		/// representing the poll result.
		/// </summary>
		/// <param name="lookupEventsPerStream">is the events per stream where the
		/// first dimension is a number of rows (often 1 depending on windows used) and
		/// the second dimension is the number of streams participating in a join.
		/// </param>
		/// <returns> array of lists with one list for each event-per-stream row  
		/// </returns>
        IList<EventBean>[] poll(EventBean[][] lookupEventsPerStream);
	}
}