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

        public virtual EventStream createStream(FilterSpec filterSpec, FilterService filterService)
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
            FilterValueSet filterValues = filterSpec.getValueSet(null);
            filterService.Add(filterValues, filterCallback);

            return eventStream;
        }

        public virtual void dropStream(FilterSpec filterSpec, FilterService filterService)
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
