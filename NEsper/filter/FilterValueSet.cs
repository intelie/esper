using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.filter
{
	
	/// <summary> Contains the filter criteria to sift through events. The filter criteria are the event class to look for and
	/// a set of parameters (property names, operators and constant/range values).
	/// </summary>
	public interface FilterValueSet
	{
		/// <summary> Returns type of event to filter for.</summary>
		/// <returns> event type
		/// </returns>

        EventType EventType { get; }
			
		/// <summary> Returns list of filter parameters.</summary>
		/// <returns> list of filter params
		/// </returns>

        IList<FilterValueSetParam> Parameters { get; }
	}
}