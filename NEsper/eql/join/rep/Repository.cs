using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join.rep
{
	/// <summary>
	/// An interface for a repository of events in a lookup/join scheme that
	/// supplies events for event stream table lookups and receives results
	/// of lookups.
	/// </summary>

	public interface Repository
	{
		/// <summary>
		/// Supply events for performing look ups for a given stream.
		/// </summary>
		/// <param name="lookupStream">is the stream to perform lookup for</param>
		/// <returns>
		/// an iterator over events with additional positioning information
		/// </returns>

		IEnumerator<Cursor> GetCursors(int lookupStream);

		/// <summary>
		/// Add a lookup result.
		/// </summary>
		/// <param name="cursor">provides result position and parent event and node information</param>
		/// <param name="lookupResults">is the events found</param>
		/// <param name="resultStream">is the stream number of the stream providing the results</param>

		void AddResult(Cursor cursor, Set<EventBean> lookupResults, int resultStream);
	}
}
