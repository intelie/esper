package net.esper.view.window;

import net.esper.event.EventBean;
import net.esper.collection.ViewUpdatedCollection;

import java.util.Map;
import java.util.HashMap;

/**
 * Provides relative access to insert stream events for certain window.
 */
public class IStreamRelativeAccess implements RelativeAccessByEventNIndex, ViewUpdatedCollection
{
    private final Map<EventBean, Integer> indexPerEvent;
    private EventBean[] lastNewData;
    private final UpdateObserver updateObserver;

    /**
     * Ctor.
     * @param updateObserver is invoked when updates are received
     */
    public IStreamRelativeAccess(UpdateObserver updateObserver)
    {
        this.updateObserver = updateObserver;
        indexPerEvent = new HashMap<EventBean, Integer>();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
        updateObserver.updated(this, newData);
        indexPerEvent.clear();
        lastNewData = newData;

        if (newData != null)
        {
            for (int i = 0; i < newData.length; i++)
            {
                indexPerEvent.put(newData[i], i);
            }
        }
    }

    public EventBean getRelativeToEvent(EventBean event, int prevIndex)
    {
        if (lastNewData == null)
        {
            return null;
        }

        if (prevIndex == 0)
        {
            return event;
        }

        Integer indexIncoming = indexPerEvent.get(event);
        if (indexIncoming == null)
        {
            return null;
        }

        if (prevIndex > indexIncoming)
        {
            return null;
        }

        int relativeIndex = indexIncoming - prevIndex;
        if ((relativeIndex < lastNewData.length) && (relativeIndex >= 0))
        {
            return lastNewData[relativeIndex];
        }
        return null;
    }

    public interface UpdateObserver
    {
        public void updated(IStreamRelativeAccess iStreamRelativeAccess, EventBean[] newData);
    }
}
