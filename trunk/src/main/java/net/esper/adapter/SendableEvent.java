package net.esper.adapter;

import net.esper.client.EPRuntime;

/**
 * An event packaged up so that it can immediately be sent into 
 * the caller-specified runtime.
 */
public interface SendableEvent
{
	/**
	 * Send the event into the runtime.
	 * @param runtime - the runtime to send the event into
	 */
	public void send(EPRuntime runtime);
}
