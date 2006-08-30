package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.SendableEvent;
import net.esper.client.EPRuntime;
import net.esper.schedule.ScheduleSlot;

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
		this.mapToSend = new HashMap<String, Object>(mapToSend);
		this.eventTypeAlias = eventTypeAlias;
		this.timestamp = timestamp;
		this.scheduleSlot = scheduleSlot;
	}
	
	public void send(EPRuntime runtime)
	{
		runtime.sendEvent(mapToSend, eventTypeAlias);
	}
	
	public ScheduleSlot getScheduleSlot()
	{
		return scheduleSlot;
	}

	public long getSendTime()
	{
		return timestamp;
	}
	
	public String toString()
	{
		return mapToSend.toString();
	}
}