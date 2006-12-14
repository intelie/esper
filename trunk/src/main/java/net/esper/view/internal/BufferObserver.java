package net.esper.view.internal;

import net.esper.collection.FlushedEventBuffer;

/**
 * Observer interface to a stream publishing new and old events.
 */
public interface BufferObserver
{
    /**
     * Receive new and old events from a stream.
     * @param streamId - the stream number sending the events
     * @param newEventBuffer - buffer for new events
     * @param oldEventBuffer - buffer for old events
     */
    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer);
}
