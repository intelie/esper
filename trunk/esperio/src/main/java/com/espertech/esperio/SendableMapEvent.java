/**************************************************************************************
 * Copyright (C) 2008 EsperTech, Inc. All rights reserved.                            *
 * http://esper.codehaus.org                                                          *
 * http://www.espertech.com                                                           *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esperio;

import java.util.HashMap;
import java.util.Map;

import com.espertech.esper.schedule.ScheduleSlot;

/**
 * An implementation of SendableEvent that wraps a Map event for
 * sending into the runtime.
 */
public class SendableMapEvent extends AbstractSendableEvent
{
	private final Map<String, Object> mapToSend;
	private final String eventTypeName;

	/**
	 * Ctor.
	 * @param mapToSend - the map to send into the runtime
	 * @param eventTypeName - the event type name for the map event
	 * @param timestamp - the timestamp for this event
	 * @param scheduleSlot - the schedule slot for the entity that created this event
	 */
	public SendableMapEvent(Map<String, Object> mapToSend, String eventTypeName, long timestamp, ScheduleSlot scheduleSlot)
	{
		super(timestamp, scheduleSlot);
		this.mapToSend = new HashMap<String, Object>(mapToSend);
		this.eventTypeName = eventTypeName;
	}

	/* (non-Javadoc)
	 * @see com.espertech.esperio.SendableEvent#send(com.espertech.esper.client.EPRuntime)
	 */
	public void send(AbstractSender sender)
	{
		sender.sendEvent(this, mapToSend, eventTypeName);
	}

	public String toString()
	{
		return mapToSend.toString();
	}
}
