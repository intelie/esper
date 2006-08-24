package net.esper.adapter.csv;

import java.util.HashMap;
import java.util.Map;

import net.esper.adapter.SendableEvent;
import net.esper.client.EPRuntime;

/**
 * An instance of SendableEvent that wraps a Map event for
 * sending into the runtime.
 */
public class SendableMapEvent implements SendableEvent
{
	private final Map<String, Object> mapToSend;
	private final String eventTypeAlias;
	
	/**
	 * Ctor.
	 * @param mapToSend - the map to send into the runtime
	 * @param eventTypeAlias - the event type alias for the map event
	 */
	public SendableMapEvent(Map<String, Object> mapToSend, String eventTypeAlias)
	{
		this.mapToSend = new HashMap<String, Object>(mapToSend);
		this.eventTypeAlias = eventTypeAlias;
	}
	
	public void send(EPRuntime runtime)
	{
		runtime.sendEvent(mapToSend, eventTypeAlias);
	}

}
