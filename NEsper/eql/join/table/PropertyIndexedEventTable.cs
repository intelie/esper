using System;
using System.Collections.Generic;

using net.esper.collection;
using net.esper.compat;
using net.esper.events;

using Log = org.apache.commons.logging.Log;
using LogFactory = org.apache.commons.logging.LogFactory;

namespace net.esper.eql.join.table
{
    /// <summary> Index that organizes events by the event property values into hash buckets. Based on a HashMap
    /// with <seealso cref="net.esper.collection.MultiKeyUntyped"/> keys that store the property values.
    /// 
    /// Takes a list of property names as parameter. Doesn't care which event type the events have as long as the properties
    /// exist. If the same event is added twice, the class throws an exception on add.
    /// </summary>
    
    public class PropertyIndexedEventTable : EventTable
    {
        private readonly int streamNum;
        private readonly String[] propertyNames;
		
		/// <summary>
		/// Getters for properties.
		/// </summary>
		
        private readonly EventPropertyGetter[] propertyGetters;

		/// <summary>
		/// Index table.
		/// </summary>
		
        private readonly EDictionary<MultiKeyUntyped, Set<EventBean>> propertyIndex;

        /// <summary> Ctor.</summary>
        /// <param name="streamNum">the stream number that is indexed
        /// </param>
        /// <param name="eventType">types of events indexed
        /// </param>
        /// <param name="propertyNames">property names to use for indexing
        /// </param>

        public PropertyIndexedEventTable(int streamNum, EventType eventType, String[] propertyNames)
        {
            this.streamNum = streamNum;
            this.propertyNames = propertyNames;

            // Init getters
            propertyGetters = new EventPropertyGetter[propertyNames.Length];
            for (int i = 0; i < propertyNames.Length; i++)
            {
                propertyGetters[i] = eventType.GetGetter(propertyNames[i]);
            }

            propertyIndex = new EHashDictionary<MultiKeyUntyped, Set<EventBean>>();
        }
		
	    /**
	     * Determine multikey for index access.
	     * @param event to get properties from for key
	     * @return multi key
	     */
	    protected virtual MultiKeyUntyped GetMultiKey(EventBean _event)
	    {
	        return EventBeanUtility.getMultiKey(_event, propertyGetters);
	    }

        /// <summary> Add an array of events. Same event instance is not added twice. Event properties should be immutable.
        /// Allow null passed instead of an empty array.
        /// </summary>
        /// <param name="events">to add
        /// </param>
        /// <throws>  ArgumentException if the event was already existed in the index </throws>

        public virtual void Add(EventBean[] events)
        {
            if (events == null)
            {
                return;
            }

            foreach (EventBean ev in events)
            {
                Add(ev);
            }
        }

        /// <summary> Remove events.</summary>
        /// <param name="events">to be removed, can be null instead of an empty array.
        /// </param>
        /// <throws>  ArgumentException when the event could not be removed as its not in the index </throws>
        public virtual void Remove(EventBean[] events)
        {
            if (events == null)
            {
                return;
            }

            foreach (EventBean ev in events)
            {
                Remove(ev);
            }
        }

        /// <summary> Returns the set of events that have the same property value as the given event.</summary>
        /// <param name="keys">to compare against
        /// </param>
        /// <returns> set of events with property value, or null if none found (never returns zero-sized set)
        /// </returns>

        public virtual Set<EventBean> Lookup(Object[] keys)
        {
            MultiKeyUntyped key = new MultiKeyUntyped(keys);
            Set<EventBean> events = propertyIndex.Fetch(key);
            return events;
        }

		private void Add( EventBean _event )
        {
			MultiKeyUntyped key = GetMultiKey(_event);
            Set<EventBean> events = propertyIndex.Fetch(key, null);

            if (events == null)
            {
                events = new EHashSet<EventBean>();
                propertyIndex[key] = events;
            }

            if (events.Contains(_event))
            {
                throw new ArgumentException("Event already in index, event=" + _event);
            }

            events.Add(_event);
        }

        private void Remove(EventBean _event)
        {
			MultiKeyUntyped key = GetMultiKey(_event);

            Set<EventBean> events = propertyIndex.Fetch(key, null);
            if (events == null)
            {
                log.Debug(".remove Event could not be located in index, event " + _event);
                return;
            }

            if (!events.Remove(_event))
            {
                // Not an error, its possible that an old-data event is artificial (such as for statistics) and
                // thus did not correspond to a new-data event raised earlier.
                log.Debug( String.Format( ".remove Event could not be located in index, event {0}", _event) ) ;
                return;
            }

            if (events.IsEmpty)
            {
                propertyIndex.Remove(key);
            }
         }

         /// <summary>
         /// Returns a <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
         /// </summary>
         /// <returns>
         /// A <see cref="T:System.String"></see> that represents the current <see cref="T:System.Object"></see>.
         /// </returns>
        public override String ToString()
        {
            return
                "PropertyIndexedEventTable" +
                " streamNum=" + streamNum + 
                " propertyNames=" + CollectionHelper.Render(propertyNames);
        }

        private static Log log = LogFactory.GetLog(System.Reflection.MethodBase.GetCurrentMethod().DeclaringType);
    }
}
