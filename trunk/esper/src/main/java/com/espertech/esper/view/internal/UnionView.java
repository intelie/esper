package com.espertech.esper.view.internal;

import com.espertech.esper.collection.FlushedEventBuffer;
import com.espertech.esper.collection.RefCountedSet;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewSupport;

import java.util.Iterator;
import java.util.List;

public class UnionView extends ViewSupport implements BufferObserver
{
    private final EventType eventType;
    private final View[] views;
    private final BufferView[] buffers;
    private final FlushedEventBuffer[] oldEventBuffers;
    private final RefCountedSet<EventBean> window;

    public UnionView(EventType eventType, List<View> viewList)
    {
        this.eventType = eventType;
        this.views = viewList.toArray(new View[viewList.size()]);
        this.window = new RefCountedSet<EventBean>();

        buffers = new BufferView[viewList.size()];
        oldEventBuffers = new FlushedEventBuffer[viewList.size()];
        
        for (int i = 0; i < viewList.size(); i++)
        {
            buffers[i] = new BufferView(i);
            buffers[i].setObserver(this);
            views[i].addView(buffers[i]);
        }
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        if (newData != null)
        {
            for (EventBean newEvent : newData)
            {
                window.add(newEvent, views.length);
            }
        }

        // new events must go to all views
        // old events, such as when removing from a named window, get removed from all views
        for (View view : views)
        {
            view.update(newData, oldData);
        }

        for (FlushedEventBuffer removed : oldEventBuffers)
        {
            if (removed == null)
            {
                continue;
            }

            EventBean[] viewOldData = removed.getAndFlush();
            for (EventBean old : viewOldData)
            {
                boolean isNoMoreRef = window.remove(old);
                if (isNoMoreRef)
                {
                    
                }
            }
        }

        if (oldData != null)
        {
            for (EventBean oldEvent : oldData)
            {
                window.removeAll(oldEvent);
            }
        }

        updateChildren(newData, oldData);
    }

    public EventType getEventType()
    {
        return eventType;
    }

    public Iterator<EventBean> iterator()
    {
        return window.keyIterator();
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {

    }
}
