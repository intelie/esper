using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

namespace net.esper.filter
{

    /// <summary> Implementation of the filter service interface.
    /// Does not allow the same filter callback to be added more then once.
    /// </summary>
    public class FilterServiceImpl : FilterService
    {
        /// <summary>
        /// Return a count of the number of events evaluated by this service.
        /// </summary>
        /// <value></value>
        /// <returns> count of invocations of evaluate method
        /// </returns>
        public long NumEventsEvaluated
        {
            get
            {
                return Interlocked.Read( ref numEventsEvaluated ) ;
            }
        }

        private readonly EventTypeIndexBuilder indexBuilder;
        private readonly EventTypeIndex eventTypeIndex;
        private long numEventsEvaluated ;

        /// <summary>
        /// Constructor.
        /// </summary>
        protected internal FilterServiceImpl()
        {
            eventTypeIndex = new EventTypeIndex();
            indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);
        }

        /// <summary>
        /// Adds the specified filter value set.
        /// </summary>
        /// <param name="filterValueSet">The filter value set.</param>
        /// <param name="filterCallback">The filter callback.</param>
        public void Add(FilterValueSet filterValueSet, FilterCallback filterCallback)
        {
            indexBuilder.Add(filterValueSet, filterCallback);
        }

        /// <summary>
        /// Removes the specified filter callback.
        /// </summary>
        /// <param name="filterCallback">The filter callback.</param>
        public void Remove(FilterCallback filterCallback)
        {
            indexBuilder.Remove(filterCallback);
        }

        /// <summary>
        /// Evaluates the specified event bean.
        /// </summary>
        /// <param name="eventBean">The event bean.</param>
        public void Evaluate(EventBean eventBean)
        {
            Interlocked.Increment(ref numEventsEvaluated);

            // Finds all matching filters and return their callbacks
            IList<FilterCallback> matches = new List<FilterCallback>();
            eventTypeIndex.MatchEvent(eventBean, matches);

            if (matches.Count == 0)
            {
                return;
            }

            foreach (FilterCallback actionable in matches)
            {
                actionable.matchFound(eventBean);
            }
        }
    }
}
