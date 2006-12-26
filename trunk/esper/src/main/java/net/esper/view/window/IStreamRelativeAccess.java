package net.esper.view.window;

import net.esper.event.EventBean;
import net.esper.collection.ViewUpdatedCollection;

import java.util.Map;
import java.util.HashMap;

public class IStreamRelativeAccess implements RelativeAccessByEventNIndex, ViewUpdatedCollection
{
    private Map<EventBean, Integer> indexPerEvent;
    private EventBean[] lastNewData;

    public IStreamRelativeAccess()
    {
        indexPerEvent = new HashMap<EventBean, Integer>();
    }

    public void update(EventBean[] newData, EventBean[] oldData)
    {
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
}
