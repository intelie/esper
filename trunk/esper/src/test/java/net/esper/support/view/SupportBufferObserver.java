package net.esper.support.view;

import net.esper.view.internal.BufferObserver;
import net.esper.collection.EventBuffer;

public class SupportBufferObserver implements BufferObserver
{
    private boolean hasNewData;
    private int streamId;
    private EventBuffer newEventBuffer;
    private EventBuffer oldEventBuffer;

    public boolean getAndResetHasNewData()
    {
        boolean result = hasNewData;
        hasNewData = false;
        return result;
    }

    public void newData(int streamId, EventBuffer newEventBuffer, EventBuffer oldEventBuffer)
    {
        if (hasNewData == true)
        {
            throw new IllegalStateException("Observer already has new data");
        }

        hasNewData = true;
        this.streamId = streamId;
        this.newEventBuffer = newEventBuffer;
        this.oldEventBuffer = oldEventBuffer;
    }

    public int getAndResetStreamId()
    {
        int id = streamId;
        streamId = 0;
        return id;
    }

    public EventBuffer getAndResetNewEventBuffer()
    {
        EventBuffer buf = newEventBuffer;
        newEventBuffer = null;
        return buf;
    }

    public EventBuffer getAndResetOldEventBuffer()
    {
        EventBuffer buf = oldEventBuffer;
        oldEventBuffer = null;
        return buf;
    }
}
