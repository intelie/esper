using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;

namespace net.esper.filter
{
	/// <summary> Interface for range-type filter parameters for type checking and to obtain the filter values for endpoints based
	/// on prior results.
	/// </summary>
    
    public interface FilterSpecParamRangeValue
    {
        /// <summary> Check the type against the map of event tag and type.</summary>
        /// <param name="taggedEventTypes">map of event tags and types
        /// </param>
        void checkType(EDictionary<String, EventType> taggedEventTypes);

        /// <summary> Returns the filter value representing the endpoint.</summary>
        /// <param name="matchedEvents">is the prior results
        /// </param>
        /// <returns> filter value
        /// </returns>
        double getFilterValue(MatchedEventMap matchedEvents);
    }
}