using System;

using EventBuffer = net.esper.collection.EventBuffer;

namespace net.esper.view.internals
{
	/// <summary>
    /// Observer interface to a stream publishing new and old events.
	/// Receive new and old events from a stream.</summary>
	/// <param name="streamId">the stream number sending the events
	/// </param>
	/// <param name="newEventBuffer">buffer for new events
	/// </param>
	/// <param name="oldEventBuffer">buffer for old events
	/// </param>
    /// </summary>
	
    public delegate void BufferObserver(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer);
}