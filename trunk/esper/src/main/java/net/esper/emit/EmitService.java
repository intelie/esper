package net.esper.emit;

import net.esper.client.EmittedListener;

/**
 * The emit service is a simple publish-subscribe mechanism for sending events out of the runtime to
 * emitted event listeners that registered interest in the same or default channel that an event was
 * emitted to.
 */
public interface EmitService
{
    /**
     * Emit an event to the specified channel. All listeners listening to the exact same channel and
     * all listeners listening to the default channel are handed the event emitted.
     * @param object is the event to emit
     * @param channel is the channel to emit to
     */
    public void emitEvent(Object object, String channel);

    /**
     * Add emitted event listener for the specified channel, or the default channel if the channel value is null.
     * The listener will be invoked when an event is emitted on the subscribed channel. Listeners subscribed to the
     * default channel are invoked for all emitted events regardless of what channel the event is emitted onto.
     * @param listener is the callback to receive when events are emitted
     * @param channel is the channel to listen to, with null values allowed to indicate the default channel
     */
    public void addListener(EmittedListener listener, String channel);

    /**
     * Removes all listeners for emitted events.
     */
    public void clearListeners();

    /**
     * Number of events emitted.
     * @return total of events emitted
     */
    public int getNumEventsEmitted();
}
