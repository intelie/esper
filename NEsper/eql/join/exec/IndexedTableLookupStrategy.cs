using System;
using System.Collections;
using System.Collections.Generic;

using net.esper.compat;
using net.esper.events;
using net.esper.eql.join.table;

namespace net.esper.eql.join.exec
{
	/// <summary>
    /// Lookup on an index using a set of properties as key values.
    /// </summary>
	
    public class IndexedTableLookupStrategy : TableLookupStrategy
	{
        /// <summary> Returns event type of the lookup event.</summary>
		/// <returns> event type of the lookup event
		/// </returns>
		
        virtual public EventType EventType
		{
			get { return eventType; }
		}
		
        /// <summary> Returns properties to use from lookup event to look up in index.</summary>
		/// <returns> properties to use from lookup event
		/// </returns>
		
        virtual public IList<String> Properties
		{
			get { return properties; }
		}
		/// <summary> Returns index to look up in.</summary>
		/// <returns> index to use
		/// </returns>
		virtual public PropertyIndexedEventTable Index
		{
			get { return index; }
		}

        private readonly EventType eventType;
		private readonly List<String> properties;
		private readonly PropertyIndexedEventTable index;
		private readonly EventPropertyGetter[] propertyGetters;
		
		/// <summary> Ctor.</summary>
		/// <param name="eventType">- event type to expect for lookup
		/// </param>
		/// <param name="properties">- key properties
		/// </param>
		/// <param name="index">- index to look up in
		/// </param>
		
        public IndexedTableLookupStrategy(EventType eventType, String[] properties, PropertyIndexedEventTable index)
		{
			this.eventType = eventType;
            this.properties = new List<string>();
            this.properties.AddRange(properties);
			this.index = index;
			
			propertyGetters = new EventPropertyGetter[properties.Length];
			for (int i = 0; i < this.properties.Count ; i++)
			{
				propertyGetters[i] = eventType.GetGetter(this.properties[i]);
				
				if (propertyGetters[i] == null)
				{
					throw new ArgumentException("Property named '" + this.properties[i] + "' is invalid for type " + eventType);
				}
			}
		}
		
        public ISet<EventBean> lookup(EventBean ev)
        {
            Object[] keys = getKeys(ev);
            return index.lookup(keys);
        }
		
		private Object[] getKeys(EventBean _event)
		{
			return EventBeanUtility.GetPropertyArray(_event, propertyGetters);
		}
		
		public override String ToString()
		{
			return "IndexedTableLookupStrategy indexProps=" + properties + " index=(" + index + ")";
		}
	}
}
