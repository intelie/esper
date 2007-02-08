using System;

using net.esper.events;

namespace net.esper.view
{
	/// <summary>
    /// A streams is a conduct for incoming events. Incoming data is placed into streams for consumption by queries.
    /// </summary>

    public interface EventStream : Viewable
    {
        /// <summary> Insert a new event onto the stream.</summary>
        /// <param name="event">to insert
        /// </param>

        void Insert(EventBean _event);
    }
}