// ************************************************************************************
// Copyright (C) 2006 Thomas Bernhardt. All rights reserved.                          *
// http://esper.codehaus.org                                                          *
// ---------------------------------------------------------------------------------- *
// The software in this package is published under the terms of the GPL license       *
// a copy of which has been included with this distribution in the license.txt file.  *
// ************************************************************************************

using System;
using System.Xml;

using net.esper.compat;

namespace net.esper.client
{
    /// <summary>
    ///  Interface to event stream processing runtime services.
    /// </summary>

    public interface EPRuntime
    {
        /// <summary> Number of events received over the lifetime of the event stream processing runtime.</summary>
        /// <returns> number of events received
        /// </returns>

        long NumEventsReceived { get; }

        /// <summary> Number of events emitted over the lifetime of the event stream processing runtime.</summary>
        /// <returns> number of events emitted
        /// </returns>

        long NumEventsEmitted { get; }

        /// <summary>
        /// Send an event represented by a plain Java object to the event stream processing runtime.
        /// Use the route method for sending events into the runtime from within UpdateListener code.
        /// </summary>
        /// <param name="_object">is the event to sent to the runtime
        /// </param>
        /// <throws>  EPException is thrown when the processing of the event lead to an error </throws>

        void SendEvent(Object _object);

        /// <summary>
        /// Send a map containing event property values to the event stream processing runtime.
        /// Use the route method for sending events into the runtime from within UpdateListener code.
        /// </summary>
        /// <param name="map">map that contains event property values. Keys are expected to be of type String while values
        /// can be of any type. Keys and values should match those declared via Configuration for the given eventTypeAlias. 
        /// </param>
        /// <param name="eventTypeAlias">the alias for the (property name, property type) information for this map
        /// </param>
        /// <throws>  EPException - when the processing of the event leads to an error </throws>
        
        void SendEvent(IDataDictionary map, String eventTypeAlias);

        /// <summary>
        /// Send an event represented by a DOM node to the event stream processing runtime.
        /// Use the route method for sending events into the runtime from within UpdateListener code.
        /// </summary>
        /// <param name="node">is the DOM node as an event
        /// </param>
        /// <throws>  EPException is thrown when the processing of the event lead to an error </throws>
        
        void SendEvent(XmlNode node);

        /// <summary>
        /// Emit an event object to any registered EmittedListener instances listening to the default channel.</summary>
        /// <param name="_object">to be emitted to the default channel
        /// </param>
        
        void Emit(Object _object);

        /// <summary>
        /// Emit an event object to any registered EmittedListener instances on the specified channel.
        /// Event listeners listening to all channels as well as those listening to the specific channel
        /// are called. Supplying a null value in the channel has the same result as the Emit(Object object) method.
        /// </summary>
        /// <param name="_object">to be emitted
        /// </param>
        /// <param name="channel">channel to emit the object to, or null if emitting to the default channel
        /// </param>
        
        void Emit(Object _object, String channel);

        /// <summary> Register an object that listens for events emitted from the event stream processing runtime on the
        /// specified channel. A null value can be supplied for the channel in which case the
        /// emit listener will be invoked for events emitted an any channel.
        /// </summary>
        /// <param name="listener">called when an event is emitted by the runtime.
        /// </param>
        /// <param name="channel">is the channel to add the listener to, a null value can be used to listen to events emitted
        /// on all channels
        /// </param>
        
        void AddEmittedListener(EmittedListener listener, String channel);

        /// <summary>
        /// Deregister all emitted event listeners.
        /// </summary>
        
        void ClearEmittedListeners();

        /// <summary>
        /// Route the event object back to the event stream processing runtime for internal dispatching.
        /// The route event is processed just like it was sent to the runtime, that is any
        /// active expressions seeking that event receive it. The routed event has priority over other
        /// events sent to the runtime. In a single-threaded application the routed event is
        /// processed before the next event is sent to the runtime through the
        /// EPRuntime.sendEvent method.
        /// </summary>
        /// <param name="_event">to route internally for processing by the event stream processing runtime
        /// </param>
        
        void Route(Object _event);
    }
}
