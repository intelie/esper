package net.esper.client;

/**
 * Listener interface for events emitted from an {@link EPRuntime}.
 */
public interface EmittedListener
{
    /**
     * Called to indicate an event emitted.
     * @param event is the event emitted
     */
    public void emitted(Object event);
}
