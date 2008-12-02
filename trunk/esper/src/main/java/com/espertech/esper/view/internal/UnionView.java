package com.espertech.esper.view.internal;

import com.espertech.esper.collection.FlushedEventBuffer;
import com.espertech.esper.collection.RefCountedSet;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;
import com.espertech.esper.event.EventBeanUtility;
import com.espertech.esper.view.View;
import com.espertech.esper.view.ViewSupport;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class UnionView extends ViewSupport implements BufferObserver
{
    private final EventType eventType;
    private final View[] views;
    private final BufferView[] buffers;
    private final FlushedEventBuffer[] oldEventBuffers;
    private final RefCountedSet<EventBean> unionWindow;
    private final List<EventBean> removalEvents = new ArrayList<EventBean>();
    private boolean isHasRemovestreamData;

    public UnionView(EventType eventType, List<View> viewList)
    {
        this.eventType = eventType;
        this.views = viewList.toArray(new View[viewList.size()]);
        this.unionWindow = new RefCountedSet<EventBean>();

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
        // add new event to union
        if (newData != null)
        {
            for (EventBean newEvent : newData)
            {
                unionWindow.add(newEvent, views.length);
            }
        }

        // new events must go to all views
        // old events, such as when removing from a named window, get removed from all views
        isHasRemovestreamData = false;  // changed by observer
        for (View view : views)
        {
            view.update(newData, oldData);
        }

        // see if any child view has removed any events
        List<EventBean> removedEvents = null;

        if (isHasRemovestreamData)
        {            
            // process each buffer
            for (int i = 0; i < oldEventBuffers.length; i++)
            {
                FlushedEventBuffer removed = oldEventBuffers[i];
                if (removed == null)
                {
                    continue;
                }
                oldEventBuffers[i] = null;  // clear entry

                EventBean[] viewOldData = removed.getAndFlush();

                // remove events for union, if the last event was removed then add it
                for (EventBean old : viewOldData)
                {
                    boolean isNoMoreRef = unionWindow.remove(old);
                    if (isNoMoreRef)
                    {
                        if (removedEvents == null)
                        {
                            removalEvents.clear();
                            removedEvents = removalEvents;
                        }
                        removedEvents.add(old);
                    }
                }
            }
        }

        if (oldData != null)
        {
            for (EventBean oldEvent : oldData)
            {
                unionWindow.removeAll(oldEvent);
            }
        }

        if (removedEvents != null)
        {
            if (oldData == null)
            {
                oldData = removedEvents.toArray(new EventBean[removedEvents.size()]);
            }
            else
            {
                oldData = EventBeanUtility.addToArray(oldData, removedEvents);
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
        return unionWindow.keyIterator();
    }

    public void newData(int streamId, FlushedEventBuffer newEventBuffer, FlushedEventBuffer oldEventBuffer)
    {
        if (!oldEventBuffer.isEmpty())
        {
            oldEventBuffers[streamId] = oldEventBuffer;
            isHasRemovestreamData = true;
        }
    }
}
