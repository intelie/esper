package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;

public class MultimatchState {
    private int count;
    private EventBean[] events;

    public MultimatchState(EventBean event) {
        events = new EventBean[3];
        add(event);
    }

    public MultimatchState(MultimatchState state)
    {
        EventBean[] copyArray = new EventBean[state.getBuffer().length];
        System.arraycopy(state.getBuffer(), 0, copyArray, 0, state.getCount());

        count = state.getCount();
        events = copyArray;
    }

    public void add(EventBean event)
    {
        if (count == events.length)
        {
            EventBean[] buf = new EventBean[events.length * 2];
            System.arraycopy(events, 0, buf, 0, events.length);
            events = buf;
        }
        events[count++] = event;
    }

    public int getCount() {
        return count;
    }

    public EventBean[] getBuffer() {
        return events;
    }

    public boolean containsEvent(EventBean event)
    {
        for (int i = 0; i < count; i++)
        {
            if (events[i] == event)
            {
                return true;
            }
        }
        return false;
    }

    public EventBean[] getEventArray()
    {
        if (count == events.length)
        {
            return events;
        }
        EventBean[] array = new EventBean[count];
        System.arraycopy(events, 0, array, 0, count);
        return array;
    }
}
