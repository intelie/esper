package net.esper.view.internal;

import java.util.Iterator;

import net.esper.view.ViewSupport;
import net.esper.event.EventType;
import net.esper.event.EventBean;
import net.esper.collection.FlushedEventBuffer;

/**
 * A view that acts as an adapter between views and update listeners.
 * The view can be added to a parent view. When the parent view publishes data, the view will forward the
 * data to the UpdateListener implementation that has been supplied. If no UpdateListener has been supplied,
 * then the view will cache the last data published by the parent view.
 */
public final class BufferView extends ViewSupport
{
    private final int streamId;

    private BufferObserver observer;
    private FlushedEventBuffer newDataBuffer = new FlushedEventBuffer();
    private FlushedEventBuffer oldDataBuffer = new FlushedEventBuffer();

    /**
     * Ctor.
     * @param streamId - number of the stream for which the view buffers the generated events.
     */
    public BufferView(int streamId)
    {
        this.streamId = streamId;
    }

    /**
     * Set the observer for indicating new and old data.
     * @param observer to indicate new and old events
     */
    public void setObserver(BufferObserver observer)
    {
        this.observer = observer;
    }

    public final EventType getEventType()
    {
        return parent.getEventType();
    }

    public final Iterator<EventBean> iterator()
    {
        return parent.iterator();
    }

    public final void update(EventBean[] newData, EventBean[] oldData)
    {
        newDataBuffer.add(newData);
        oldDataBuffer.add(oldData);
        observer.newData(streamId, newDataBuffer, oldDataBuffer);
    }
}
