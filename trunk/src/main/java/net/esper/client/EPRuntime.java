package net.esper.client;

import java.util.Map;

/**
 * Interface to event stream processing runtime services.
 */
public interface EPRuntime
{
    /**
     * Send an event represented by a plain Java object to the event stream processing runtime.
     * <p>
     * Use the route method for sending events into the runtime from within UpdateListener code.
     *
     * @param object is the event to sent to the runtime
     * @throws EPException is thrown when the processing of the event lead to an error
     */
    public void sendEvent(Object object) throws EPException;
    
    /**
     * Send a map containing event property values to the event stream processing runtime.
     * <p>
     * Use the route method for sending events into the runtime from within UpdateListener code.
     *
     * @param map - map that contains event property values. Keys are expected to be of type String while values
     * can be of any type. Keys and values should match those declared via Configuration for the given eventTypeAlias. 
     * @param eventTypeAlias - the alias for the (property name, property type) information for this map
     * @throws EPException - when the processing of the event leads to an error
     */
    public void sendEvent(Map map, String eventTypeAlias) throws EPException;

    /**
     * Send an event represented by a DOM node to the event stream processing runtime.
     * <p>
     * Use the route method for sending events into the runtime from within UpdateListener code.
     *
     * @param node is the DOM node as an event
     * @throws EPException is thrown when the processing of the event lead to an error
     */
    public void sendEvent(org.w3c.dom.Node node) throws EPException;

    /**
     * Number of events received over the lifetime of the event stream processing runtime.
     * @return number of events received
     */
    public int getNumEventsReceived();

    /**
     * Number of events emitted over the lifetime of the event stream processing runtime.
     * @return number of events emitted
     */
    public int getNumEventsEmitted();

    /**
     * Emit an event object to any registered EmittedListener instances listening to the default channel.
     * @param object to be emitted to the default channel
     */
    public void emit(final Object object);

    /**
     * Emit an event object to any registered EmittedListener instances on the specified channel.
     * Event listeners listening to all channels as well as those listening to the specific channel
     * are called. Supplying a null value in the channel has the same result as the emit(Object object) method.
     * @param object to be emitted
     * @param channel channel to emit the object to, or null if emitting to the default channel
     */
    public void emit(final Object object, final String channel);

    /**
     * Register an object that listens for events emitted from the event stream processing runtime on the
     * specified channel. A null value can be supplied for the channel in which case the
     * emit listener will be invoked for events emitted an any channel.
     * @param listener called when an event is emitted by the runtime.
     * @param channel is the channel to add the listener to, a null value can be used to listen to events emitted
     * on all channels
     */
    public void addEmittedListener(EmittedListener listener, String channel);

    /**
     * Deregister all emitted event listeners.
     */
    public void clearEmittedListeners();

    /**
     * Route the event object back to the event stream processing runtime for internal dispatching.
     * The route event is processed just like it was sent to the runtime, that is any
     * active expressions seeking that event receive it. The routed event has priority over other
     * events sent to the runtime. In a single-threaded application the routed event is
     * processed before the next event is sent to the runtime through the
     * EPRuntime.sendEvent method.
     * @param event to route internally for processing by the event stream processing runtime
     */
    public void route(final Object event);
}
