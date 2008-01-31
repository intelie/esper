package com.espertech.esper.adapter;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.schedule.ScheduleSlot;

/**
 * An implementation of SendableEvent that wraps a Map event for
 * sending into the runtime.
 */
public class SendableMapEvent implements SendableEvent
{
	private final Map<String, Object> mapToSend;
	private final String eventTypeAlias;
	private final long timestamp;
	private final ScheduleSlot scheduleSlot;
	
	/**
	 * Ctor.
	 * @param mapToSend - the map to send into the runtime
	 * @param eventTypeAlias - the event type alias for the map event
	 * @param timestamp - the timestamp for this event
	 * @param scheduleSlot - the schedule slot for the entity that created this event
	 */
	public SendableMapEvent(Map<String, Object> mapToSend, String eventTypeAlias, long timestamp, ScheduleSlot scheduleSlot)
	{
		if(scheduleSlot == null)
		{
			throw new NullPointerException("ScheduleSlot cannot be null");
		}
		this.mapToSend = new HashMap<String, Object>(mapToSend);
		this.eventTypeAlias = eventTypeAlias;
		this.timestamp = timestamp;
		this.scheduleSlot = scheduleSlot;
	}
	
	/* (non-Javadoc)
	 * @see com.espertech.esper.adapter.SendableEvent#send(com.espertech.esper.client.EPRuntime)
	 */
	public void send(EPRuntime runtime)
	{
		runtime.sendEvent(mapToSend, eventTypeAlias);
	}
	
	/* (non-Javadoc)
	 * @see com.espertech.esper.adapter.SendableEvent#getScheduleSlot()
	 */
	public ScheduleSlot getScheduleSlot()
	{
		return scheduleSlot;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esper.adapter.SendableEvent#getSendTime()
	 */
	public long getSendTime()
	{
		return timestamp;
	}
	
	public String toString()
	{
		return mapToSend.toString();
	}
}
