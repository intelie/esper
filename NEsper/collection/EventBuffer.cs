using System;
using System.Collections.Generic;

using EventBean = net.esper.events.EventBean;
using EventBeanUtility = net.esper.events.EventBeanUtility;

namespace net.esper.collection
{
	/// <summary>
	/// Buffer for events - accumulates events until flushed.
	/// </summary>

	public class EventBuffer
	{
		/// <summary> Get the events currently buffered. Returns null if the buffer is empty. Flushes the buffer.</summary>
		/// <returns> array of events in buffer or null if empty
		/// </returns>

		virtual public EventBean[] GetAndFlush()
		{
			EventBean[] flattened = EventBeanUtility.Flatten( remainEvents );
			remainEvents.Clear();
			return flattened;
		}

		private IList<EventBean[]> remainEvents = new List<EventBean[]>();

		/// <summary> Add an event array to buffer.</summary>
		/// <param name="events">to add
		/// </param>
		public virtual void Add( EventBean[] events )
		{
			if ( events != null )
			{
				remainEvents.Add( events );
			}
		}

		/// <summary> Empty buffer.</summary>
		public virtual void Flush()
		{
			remainEvents.Clear();
		}
	}
}
