using System;

using net.esper.events;
using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.join
{
	/// <summary> Interface for populating a join tuple result set from new data and old data for each stream.</summary>
	public interface JoinSetComposer
	{
		/// <summary> Return join tuple result set from new data and old data for each stream.</summary>
		/// <param name="newDataPerStream">for each stream the event array (can be null).
		/// </param>
		/// <param name="oldDataPerStream">for each stream the event array (can be null).
		/// </param>
		/// <returns> join tuples
		/// </returns>

		UniformPair<ISet<MultiKey<EventBean>>> Join( EventBean[][] newDataPerStream, EventBean[][] oldDataPerStream );
	}
}