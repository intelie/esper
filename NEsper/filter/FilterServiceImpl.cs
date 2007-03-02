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

        /// <summary> Constructor.</summary>
        protected internal FilterServiceImpl()
        {
            eventTypeIndex = new EventTypeIndex();
            indexBuilder = new EventTypeIndexBuilder(eventTypeIndex);
        }

        public void Add(FilterValueSet filterValueSet, FilterCallback filterCallback)
        {
            indexBuilder.Add(filterValueSet, filterCallback);
        }

        public void Remove(FilterCallback filterCallback)
        {
            indexBuilder.Remove(filterCallback);
        }

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
