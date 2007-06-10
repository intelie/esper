using System;
using System.Collections.Generic;

using net.esper.events;
using net.esper.collection;
using net.esper.compat;

namespace net.esper.eql.join
{
	/// <summary>
    /// Encapsulates the strategy use to resolve the events for a stream into a tuples of events in a join.
    /// </summary>

    public interface QueryStrategy
    {
        /// <summary> Look up events returning tuples of joined events.</summary>
        /// <param name="lookupEvents">events to use to perform the join
        /// </param>
        /// <param name="joinSet">result join tuples of events 
        /// </param>

        void Lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet);
    }
}
