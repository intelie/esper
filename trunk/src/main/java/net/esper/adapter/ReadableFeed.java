package net.esper.adapter;

import net.esper.client.EPException;

/** 
 * A Feed that allows the most current events to be pulled by the user.
 */
public interface ReadableFeed extends Feed
{
	/**
	 * Get the next event in line to be sent into the runtime , or null if there is no available event.
	 * @return an instance of SendableEvent that wraps the next event to send, or null if none
	 * @throws EPException in case of errors creating the event
	 */
	public SendableEvent read() throws EPException;
}
