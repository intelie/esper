/**************************************************************************************
 * Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
 * http://esper.codehaus.org                                                          *
 * ---------------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the GPL license       *
 * a copy of which has been included with this distribution in the license.txt file.  *
 **************************************************************************************/
package com.espertech.esper.client;

import java.util.Map;
import java.util.Set;

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

    /**
     * Sets a listener to receive events that are unmatched by any statement.
     * <p>
     * Events that can be unmatched are all events that are send into a runtime via one
     * of the sendEvent methods, or that have been generated via insert-into clause.
     * <p>
     * For an event to be unmatched by any statement, the event must not match any
     * statement's event stream filter criteria (a where-clause is NOT a filter criteria for a stream, as below).
     * <p>
     * Note: In the following statement a MyEvent event does always match
     * this statement's event stream filter criteria, regardless of the value of the 'quantity' property.
     * <pre>select * from MyEvent where quantity > 5</pre>
     * <br>
     * In the following statement only a MyEvent event with a 'quantity' property value of 5 or less does not match
     * this statement's event stream filter criteria:
     * <pre>select * from MyEvent(quantity > 5)</pre>
     * <p>
     * For patterns, if no pattern sub-expression is active for such event, the event is also unmatched.
     * @param listener is the listener to receive notification of unmatched events, or null to unregister a
     * previously registered listener
     */
    public void setUnmatchedListener(UnmatchedListener listener);

    /**
     * Returns the current variable value. A null value is a valid value for a variable.
     * @param variableName is the name of the variable to return the value for
     * @return current variable value
     * @throws VariableNotFoundException if a variable by that name has not been declared
     */
    public Object getVariableValue(String variableName) throws VariableNotFoundException;

    /**
     * Returns current variable values for each of the variable names passed in,
     * guaranteeing consistency in the face of concurrent updates to the variables.
     * @param variableNames is a set of variable names for which to return values
     * @return map of variable name and variable value
     * @throws VariableNotFoundException if any of the variable names has not been declared
     */
    public Map<String, Object> getVariableValue(Set<String> variableNames) throws VariableNotFoundException;

    /**
     * Returns current variable values for all variables,
     * guaranteeing consistency in the face of concurrent updates to the variables.
     * @return map of variable name and variable value
     */
    public Map<String, Object> getVariableValueAll();

    /**
     * Sets the value of a single variable.
     * @param variableName is the name of the variable to change the value of
     * @param variableValue is the new value of the variable, with null an allowed value
     * @throws VariableValueException if the value does not match variable type or cannot be safely coerced
     * to the variable type
     * @throws VariableNotFoundException if the variable name has not been declared
     */
    public void setVariableValue(String variableName, Object variableValue) throws VariableValueException, VariableNotFoundException;

    /**
     * Sets the value of multiple variables in one update, applying all or none of the changes
     * to variable values in one atomic transaction.
     * @param variableValues is the map of variable name and variable value, with null an allowed value
     * @throws VariableValueException if any value does not match variable type or cannot be safely coerced
     * to the variable type
     * @throws VariableNotFoundException if any of the variable names has not been declared
     */
    public void setVariableValue(Map<String, Object> variableValues) throws VariableValueException, VariableNotFoundException;
}
