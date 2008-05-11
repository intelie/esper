package com.espertech.esperio;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.schedule.ScheduleSlot;

/**
 * An implementation of SendableEvent that wraps a Map event for
 * sending into the runtime.
 */
public class SendableMapEvent extends AbstractSendableEvent
{
	private final Map<String, Object> mapToSend;
	private final String eventTypeAlias;

	/**
	 * Ctor.
	 * @param mapToSend - the map to send into the runtime
	 * @param eventTypeAlias - the event type alias for the map event
	 * @param timestamp - the timestamp for this event
	 * @param scheduleSlot - the schedule slot for the entity that created this event
	 */
	public SendableMapEvent(Map<String, Object> mapToSend, String eventTypeAlias, long timestamp, ScheduleSlot scheduleSlot)
	{
		super(timestamp, scheduleSlot);
		this.mapToSend = new HashMap<String, Object>(mapToSend);
		this.eventTypeAlias = eventTypeAlias;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.SendableEvent#send(com.espertech.esper.client.EPRuntime)
	 */
	public void send(EPRuntime runtime)
	{
		runtime.sendEvent(mapToSend, eventTypeAlias);
	}

	public String toString()
	{
		return mapToSend.toString();
	}
}
