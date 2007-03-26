using System;

using net.esper.collection;
using net.esper.events;
using net.esper.filter;
using net.esper.view;

namespace net.esper.view.stream
{
    /// <summary> Service implementation to reuse event streams and existing filters using reference counting to remove filters
    /// when not used.
    /// </summary>
    
    public class StreamReuseServiceImpl : StreamReuseService
    {
        private RefCountedMap<FilterSpec, Pair<EventStream, FilterCallback>> eventStreams;

        /// <summary> Ctor.</summary>
        public StreamReuseServiceImpl()
        {
            this.eventStreams = new RefCountedMap<FilterSpec, Pair<EventStream, FilterCallback>>();
        }

        /// <summary>
        /// Create or reuse existing EventStream instance representing that event filter.
        /// When called for some filters, should return same stream.
        /// </summary>
        /// <param name="filterSpec">event filter definition</param>
        /// <param name="filterService">filter service to activate filter if not already active</param>
        /// <returns>event stream representing active filter</returns>
        public virtual EventStream CreateStream(FilterSpec filterSpec, FilterService filterService)
        {
            // Check if a stream for this filter already exists
            Pair<EventStream, FilterCallback> pair = eventStreams[filterSpec];

            if (pair != null)
            {
                eventStreams.Reference(filterSpec);
                return pair.First;
            }

            // New event stream
            EventStream eventStream = new ZeroDepthStream(filterSpec.EventType);

            FilterCallback filterCallback = new FilterCallbackImpl(
                delegate(EventBean _event)
                {
                    eventStream.Insert(_event);
                });
                

            // Store stream for reuse
            pair = new Pair<EventStream, FilterCallback>(eventStream, filterCallback);
            eventStreams[filterSpec] = pair;

            // Activate filter
            FilterValueSet filterValues = filterSpec.GetValueSet(null);
            filterService.Add(filterValues, filterCallback);

            return eventStream;
        }

        /// <summary>
        /// Drop the event stream associated with the filter passed in.
        /// Throws an exception if already dropped.
        /// </summary>
        /// <param name="filterSpec">is the event filter definition associated with the event stream to be dropped</param>
        /// <param name="filterService">to be used to deactivate filter when the last event stream is dropped</param>
        public virtual void DropStream(FilterSpec filterSpec, FilterService filterService)
        {
        	Pair<EventStream, FilterCallback> pair = eventStreams[filterSpec];
            bool isLast = eventStreams.Dereference(filterSpec);
            if (isLast)
            {
                filterService.Remove(pair.Second);
            }
        }
    }
}
