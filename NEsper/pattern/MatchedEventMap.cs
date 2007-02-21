using System;
using System.Collections.Generic;
using System.Text;

using net.esper.compat;
using net.esper.events;

namespace net.esper.pattern
{
	/// <summary> Collection for internal use similar to the MatchedEventMap class in the client package
	/// that holds the one or more events that could match any defined event expressions.
	/// The optional tag value supplied when an event expression is created is used as a key for placing
	/// matching event objects into this collection.
	/// </summary>

	public sealed class MatchedEventMap
	{
		private EDictionary<String, EventBean> events = new EHashDictionary<String, EventBean>();
		
		/// <summary>
		/// Constructor creates an empty collection of events.
		/// </summary>
		
		public MatchedEventMap()
		{
		}

		/// <summary>
		/// Add an event to the collection identified by the given tag.
		/// </summary>
		/// <param name="tag">is an identifier to retrieve the event from</param>
		/// <param name="eventBean">is the event object to be added</param>
		
		public void Add(String tag, EventBean eventBean)
		{
			events.Put(tag, eventBean);
		}
		
		/// <summary> Returns a Hashtable containing the events where the key is the event tag string and the value is the event
		/// instance.
		/// </summary>
		/// <returns> Hashtable containing event instances
		/// </returns>

		public EDictionary<String, EventBean> getMatchingEvents()
		{
			return events;
		}
		
		/// <summary> Returns a single event instance given the tag identifier, or null if the tag could not be located.</summary>
		/// <param name="tag">is the identifier to look for
		/// </param>
		/// <returns> event instances for the tag
		/// </returns>
		
		public EventBean getMatchingEvent(String tag)
		{
			return events.Fetch(tag);
		}
		
		public override bool Equals(Object otherObject)
		{
			if (otherObject == this)
			{
				return true;
			}
			
			if (otherObject == null)
			{
				return false;
			}
			
			if (GetType() != otherObject.GetType())
			{
				return false;
			}
			
			MatchedEventMap other = (MatchedEventMap) otherObject;
			
			if (events.Count != other.events.Count)
			{
				return false;
			}
			
			// Compare entry by entry

			foreach( KeyValuePair<String, EventBean> entry in events )
			{
				String tag = entry.Key;
				EventBean eventBean = entry.Value;
				
				if (other.getMatchingEvent(tag) != eventBean)
				{
					return false;
				}
			}
			
			return true;
		}
		
		public override String ToString()
		{
			StringBuilder buffer = new StringBuilder();
			int count = 0;

			foreach ( KeyValuePair<String, EventBean> entry in events )
			{
				buffer.Append(" (" + (count++) + ") ");
				buffer.Append("tag=" + entry.Key);
				buffer.Append("  event=" + entry.Value);
			}
			
			return buffer.ToString();
		}
		
		public override int GetHashCode()
		{
			return events.GetHashCode();
		}
		
		/// <summary> Make a shallow copy of this collection.</summary>
		/// <returns> shallow copy
		/// </returns>

        public MatchedEventMap shallowCopy()
		{
			MatchedEventMap copy = new MatchedEventMap();
			copy.events = new EHashDictionary<String, EventBean>() ;
            copy.events.PutAll( events );
			return copy;
		}
		
		/// <summary> Merge the state of an other match event structure into this one by adding all entries
		/// within the MatchedEventMap to this match event.
		/// </summary>
		/// <param name="other">is the other instance to merge in.
		/// </param>
		
		public void merge(MatchedEventMap other)
		{
			foreach ( KeyValuePair<String, EventBean> entry in other.events )
			{
				events.Put( entry.Key, entry.Value ) ;
			}
		}
	}
}
