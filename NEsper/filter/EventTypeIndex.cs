using System;
using System.Collections.Generic;
using System.Threading;

using net.esper.compat;
using net.esper.events;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.filter
{
    /// <summary> Mapping of event type to a tree-like structure
    /// containing filter parameter constants in indexes {@link FilterParamIndex} and filter callbacks in {@link FilterCallbackSetNode}.
    /// <p>
    /// This class evaluates events for the purpose of filtering by (1) looking up the event's {@link EventType}
    /// and (2) asking the subtree for this event type to evaluate the event.
    /// <p>
    /// The class performs all the locking required for multithreaded access.
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

        /**
         * Returns the root node for the given event type, or null if this event type has not been seen before.
         * @param eventType is an event type
         * @return the subtree's root node
         */
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

        public void matchEvent(EventBean ev, IList<FilterCallback> matches)
        {
            if (log.IsDebugEnabled)
            {
                log.Debug(".matchEvent Event received for matching, event=" + ev);
            }

            EventType eventType = ev.EventType;

            // Attempt to match exact type
            matchType(eventType, ev, matches);

            // No supertype means we are done
            if (eventType.SuperTypes == null)
            {
                return;
            }

			foreach( EventType superType in eventType.DeepSuperTypes )
            {
                matchType(superType, ev, matches);
            }
        }

        private void matchType(EventType eventType, EventBean eventBean, IList<FilterCallback> matches)
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
                    log.Debug(".matchEvent " + message);
                }
                return;
            }

            rootNode.matchEvent(eventBean, matches);
        }

        private static readonly Log log = LogFactory.GetLog(typeof(EventTypeIndex));
    }
}
