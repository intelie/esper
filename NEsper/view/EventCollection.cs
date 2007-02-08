using System;
using System.Collections.Generic;

using net.esper.events;

namespace net.esper.view
{
    /// <summary> Interface that marks an event collection.
    /// Every event in the event collection must be of the same event type, as defined by the getEventType() call.
    /// </summary>
    public interface EventCollection : IEnumerable<EventBean>
    {
        /// <summary> Provides metadata information about the type of object the event collection contains.</summary>
        /// <returns> metadata for the objects in the collection
        /// </returns>

        EventType EventType { get; }
    }
}