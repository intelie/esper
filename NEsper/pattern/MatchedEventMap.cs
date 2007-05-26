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

	public interface MatchedEventMap
	{
		/// <summary>
		/// Add an event to the collection identified by the given tag.
		/// </summary>
		/// <param name="tag">is an identifier to retrieve the event from</param>
		/// <param name="eventBean">is the event object to be added</param>
		
		void Add(String tag, EventBean eventBean);
		
		/// <summary> Returns a Hashtable containing the events where the key is the event tag string and the value is the event
		/// instance.
		/// </summary>
		/// <returns> Hashtable containing event instances
		/// </returns>

		EDictionary<String, EventBean> GetMatchingEvents();
		
		/// <summary> Returns a single event instance given the tag identifier, or null if the tag could not be located.</summary>
		/// <param name="tag">is the identifier to look for
		/// </param>
		/// <returns> event instances for the tag
		/// </returns>
		
		EventBean GetMatchingEvent(String tag);
		
		/// <summary> Make a shallow copy of this collection.</summary>
		/// <returns> shallow copy
		/// </returns>

        MatchedEventMap ShallowCopy();
		
		/// <summary> Merge the state of an other match event structure into this one by adding all entries
		/// within the MatchedEventMap to this match event.
		/// </summary>
		/// <param name="other">is the other instance to merge in.
		/// </param>
		
		void Merge(MatchedEventMap other);
	}
}
