package net.esper.view.internal;

import net.esper.event.EventBean;
import net.esper.collection.ViewUpdatedCollection;
import net.esper.view.window.RandomAccessByIndex;
import net.esper.collection.RollingEventBuffer;

/**
 * Buffer class for insert stream events only for use with unbound streams that inserts data only, to serve
 * up one or more prior events in the insert stream based on an index.
 * <p>
 * Does not expect or care about the remove stream and simple keeps a rolling buffer of new data events
 * up to the maximum prior event we are asking for. 
 */
public class PriorEventBufferUnbound implements ViewUpdatedCollection, RandomAccessByIndex
{
    private final int maxSize;
    private final RollingEventBuffer newEvents;

    /**
     * Ctor.
     * @param maxPriorIndex is the highest prior-event index required by any expression
     */
    public PriorEventBufferUnbound(int maxPriorIndex)
    {
        this.maxSize = maxPriorIndex + 1;
        newEvents = new RollingEventBuffer(maxSize);
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        // Post new data to rolling buffer starting with the oldest
        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                EventBean newEvent = newData[i];

                // Add new event
                newEvents.add(newEvent);
            }
        }
    }

    public EventBean getNewData(int index)
    {
        if (index >= maxSize)
        {
            throw new IllegalArgumentException("Index " + index + " not allowed, max size is " + maxSize);
        }
        return newEvents.get(index);
    }

    public EventBean getOldData(int index)
    {
        return null;
    }

    public void destroy()
    {
        // No action required
    }    
}
