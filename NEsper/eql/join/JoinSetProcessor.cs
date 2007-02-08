using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

namespace net.esper.eql.join
{
	/// <summary>
    /// Processes a join result set constisting of sets of tuples of events.
    /// </summary>
	
    public interface JoinSetProcessor
	{
		/// <summary> Process join result set.</summary>
		/// <param name="newEvents">- set of event tuples representing new data
		/// </param>
		/// <param name="oldEvents">- set of event tuples representing old data
		/// </param>
		void  Process( ISet<MultiKey<EventBean>> newEvents, ISet<MultiKey<EventBean>> oldEvents);
	}
}