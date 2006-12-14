package net.esper.collection;

import net.esper.event.EventBean;

/**
 * Event buffer that rolls and provides a random access API
 *
 * Implemented as a forwards-filling array:
 *      |   |   |   |   |   |
 *
 */
public class RollingEventBuffer
{
    private int size;
    private EventBean[] buffer;
    private int nextFreeIndex;

    public RollingEventBuffer(int size)
    {
        if (size <= 0)
        {
            throw new IllegalArgumentException("Minimum buffer size is 1");
        }

        this.size = size;
        nextFreeIndex = 0;
        buffer = new EventBean[size];
    }

    public void add(EventBean[] events)
    {
        if (events == null)
        {
            return;
        }

        for (int i = 0; i < events.length; i++)
        {
            buffer[nextFreeIndex] = events[i];
            nextFreeIndex++;

            if (nextFreeIndex == size)
            {
                nextFreeIndex = 0;
            }
        }
    }

    public EventBean get(int index)
    {
        if (index >= size)
        {
            throw new IllegalArgumentException("Invalid index " + index + " for size " + size);
        }

        // The newest event is at (nextFreeIndex + 1)
        int newest = nextFreeIndex - 1;
        int relative = newest - index;
        if (relative < 0)
        {
            relative = relative + size;
        }
        return buffer[relative];
    }
}
