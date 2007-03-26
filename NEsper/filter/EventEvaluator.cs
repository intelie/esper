using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary> Interface for matching an event instance based on the event's property values to
	/// filters, specifically filter parameter constants or ranges.
	/// </summary>

    public interface EventEvaluator
    {
        /// <summary> Perform the matching of an event based on the event property values,
        /// adding any callbacks for matches found to the matches list.
        /// </summary>
        /// <param name="_event">is the event object wrapper to obtain event property values from
        /// </param>
        /// <param name="matches">accumulates the matching filter callbacks
        /// </param>

        void MatchEvent(EventBean _event, IList<FilterCallback> matches);
    }
}
