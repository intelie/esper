package com.espertech.esperio;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.schedule.ScheduleSlot;

public abstract class AbstractSendableEvent implements SendableEvent {

	private final long timestamp;
	private final ScheduleSlot scheduleSlot;
	
	public AbstractSendableEvent(long timestamp, ScheduleSlot scheduleSlot) {
		if(scheduleSlot == null)
		{
			throw new NullPointerException("ScheduleSlot cannot be null");
		}
		
		this.timestamp = timestamp;
		this.scheduleSlot = scheduleSlot;
	}

	public abstract void send(EPRuntime runtime);
	
	/* (non-Javadoc)
	 * @see com.espertech.esperio.SendableEvent#getScheduleSlot()
	 */
	public ScheduleSlot getScheduleSlot()
	{
		return scheduleSlot;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.SendableEvent#getSendTime()
	 */
	public long getSendTime()
	{
		return timestamp;
	}
}
