using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.filter
{
	/// <summary>
    /// Container for filter values for use by the {@link FilterService} to filter and distribute incoming events.
    /// </summary>

    public class FilterValueSetImpl : FilterValueSet
    {
        private readonly EventType eventType;
        private readonly IList<FilterValueSetParam> parameters;

        /// <summary> Returns event type to filter for.</summary>
        /// <returns> event type to filter for
        /// </returns>
        virtual public EventType EventType
        {
            get { return eventType; }
        }

        /// <summary> Ctor.</summary>
        /// <param name="eventType">type of event to filter for</param>
        /// <param name="parameters">list of filter parameters</param>

        public FilterValueSetImpl(EventType eventType, IList<FilterValueSetParam> parameters)
        {
            this.eventType = eventType;
            this.parameters = parameters;
        }

        /// <summary> Returns list of filter parameters.</summary>
        /// <returns> list of filter parameters
        /// </returns>
        
        public IList<FilterValueSetParam> Parameters
        {
            get
            {
                return parameters;
            }
        }
    }
}