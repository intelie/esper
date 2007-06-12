using System;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.pattern;
using net.esper.util;

namespace net.esper.filter
{
	/// <summary> Interface for range-type filter parameters for type checking and to obtain the filter values for endpoints based
	/// on prior results.
	/// </summary>

    public interface FilterSpecParamRangeValue : MetaDefItem
    {
	    /// <summary>Returns the filter value representing the endpoint.</summary>
	    /// <param name="matchedEvents">is the prior results</param>
	    /// <returns>filter value</returns>
	    double? GetFilterValue(MatchedEventMap matchedEvents);
    }
}
