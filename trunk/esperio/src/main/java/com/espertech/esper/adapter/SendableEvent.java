package com.espertech.esper.adapter;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.schedule.ScheduleSlot;

/**
 * A wrapper that packages an event up so that it can be 
 * sent into the caller-specified runtime. It also provides 
 * the scheduling information for this event (send time and 
 * schedule slot), so the user can send this event on schedule.
 */
public interface SendableEvent
{
	/**
	 * Send the event into the runtime.
	 * @param runtime - the runtime to send the event into
	 */
	public void send(EPRuntime runtime);
	
	/**
	 * Get the send time of this event, relative to all the other events sent or read by the same entity
	 * @return timestamp
	 */
	public long getSendTime();
	
	/**
	 * Get the schedule slot for the entity that created this event
	 * @return schedule slot
	 */
	public ScheduleSlot getScheduleSlot();
}
