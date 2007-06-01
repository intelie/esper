using System;
using System.Collections.Generic;

using net.esper.client;
using net.esper.schedule;

namespace net.esper.adapter
{
	/// <summary>
	/// An implementation of SendableEvent that wraps a Map event for
	/// sending into the runtime.
	/// </summary>
	public class SendableMapEvent : SendableEvent
	{
		private readonly IDictionary<String, Object> mapToSend;
		private readonly string eventTypeAlias;
		private readonly long timestamp;
		private readonly ScheduleSlot scheduleSlot;
		
		/// <summary>
		/// Ctor.
		/// <param name="mapToSend">the map to send into the runtime</param>
		/// <param name="eventTypeAlias">the event type alias for the map event</param>
		/// <param name="timestamp">the timestamp for this event</param>
		/// <param name="scheduleSlot">the schedule slot for the entity that created this event</param>
		/// </summary>
		public SendableMapEvent(IDictionary<String, Object> mapToSend, String eventTypeAlias, long timestamp, ScheduleSlot scheduleSlot)
		{
			if(scheduleSlot == null)
			{
				throw new NullReferenceException("ScheduleSlot cannot be null");
			}
			this.mapToSend = new EHashDictionary<String, Object>(mapToSend);
			this.eventTypeAlias = eventTypeAlias;
			this.timestamp = timestamp;
			this.scheduleSlot = scheduleSlot;
		}

		/// <summary>
		/// @see net.esper.adapter.SendableEvent#send(net.esper.client.EPRuntime)
		/// </summary>
		public void Send(EPRuntime runtime)
		{
			runtime.SendEvent(mapToSend, eventTypeAlias);
		}
		
		/// <summary>
		/// @see net.esper.adapter.SendableEvent#getScheduleSlot()
		/// </summary>
		
		public ScheduleSlot ScheduleSlot
		{
			get { return scheduleSlot; }
		}

		/// <summary>
		/// @see net.esper.adapter.SendableEvent#getSendTime()
		/// </summary>
		public long SendTime
		{
			get { return timestamp; }
		}
		
		public String ToString()
		{
			return mapToSend.ToString();
		}
	}
}
