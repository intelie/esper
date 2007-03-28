using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.filter
{
    /// <summary>
    /// Mapping of event type to a tree-like structure
    /// containing filter parameter constants in indexes <seealso cref="FilterParamIndex" /> and filter callbacks in <seealso cref="FilterCallbackSetNode"/>.
    /// <para>
    /// This class evaluates events for the purpose of filtering by (1) looking up the event's <seealso cref="EventType" />
    /// and (2) asking the subtree for this event type to evaluate the event.
    /// </para>
    /// <para>
    /// The class performs all the locking required for multithreaded access.
    /// </para>
    /// </summary>
    
    public class EventTypeIndex : EventEvaluator
    {
        private IDictionary<EventType, FilterCallbackSetNode> eventTypes;
        private ReaderWriterLock eventTypesRWLock;

        /// <summary>
        /// Constructor
        /// </summary>

        public EventTypeIndex()
        {
            eventTypes = new Dictionary<EventType, FilterCallbackSetNode>();
            eventTypesRWLock = new ReaderWriterLock();
        }
        
        /// <summary>
        /// Add a new event type to the index and use the specified node for the root node of its subtree.
        /// If the event type already existed, the method will throw an IllegalStateException.
        /// </summary>
        /// <param name="eventType">the event type to be added to the index</param>
        /// <param name="rootNode">the root node of the subtree for filter constant indizes and callbacks</param>
        
        public void Add(EventType eventType, FilterCallbackSetNode rootNode)
        {
            eventTypesRWLock.AcquireWriterLock( LockConstants.WriterTimeout );
            if (eventTypes.ContainsKey(eventType))
            {
                eventTypesRWLock.ReleaseWriterLock();
                throw new IllegalStateException("Event type already in index, add not performed, type=" + eventType);
            }
            eventTypes[eventType] = rootNode;
            eventTypesRWLock.ReleaseWriterLock();
        }

        /// <summary>Returns the root node for the given event type, or null if this event type has not been seen before.</summary>
        /// <param name="eventType">is an event type</param>
        /// <returns>the subtree's root node</returns>

        public FilterCallbackSetNode this[EventType eventType]
        {
        	get
        	{
	            eventTypesRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );
	            FilterCallbackSetNode result = null ;
	            eventTypes.TryGetValue( eventType, out result ) ;
	            eventTypesRWLock.ReleaseReaderLock();
	
	            return result;
        	}
        }

        /// <summary>
        /// Matches the event.
        /// </summary>
        /// <param name="ev">The ev.</param>
        /// <param name="matches">The matches.</param>
        public void MatchEvent(EventBean ev, IList<FilterCallback> matches)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".MatchEvent Event received for matching, event=" + ev);
            }

            EventType eventType = ev.EventType;

            // Attempt to match exact type
            MatchType(eventType, ev, matches);

            // No supertype means we are done
            if (eventType.SuperTypes == null)
            {
                return;
            }

			foreach( EventType superType in eventType.DeepSuperTypes )
            {
                MatchType(superType, ev, matches);
            }
        }

        private void MatchType(EventType eventType, EventBean eventBean, IList<FilterCallback> matches)
        {
            eventTypesRWLock.AcquireReaderLock( LockConstants.ReaderTimeout );
            FilterCallbackSetNode rootNode = null ;
            eventTypes.TryGetValue(eventType, out rootNode) ;
            eventTypesRWLock.ReleaseReaderLock();

            // If the top class node is null, no filters have yet been registered for this event type.
            // In this case, log a message and done.
            if (rootNode == null)
            {
                if (log.IsDebugEnabled)
                {
                    String message = "Event type is not known to the filter service, eventType=" + eventType;
                    log.Debug(".MatchEvent " + message);
                }
                return;
            }

            rootNode.MatchEvent(eventBean, matches);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EventTypeIndex));
    }
}
