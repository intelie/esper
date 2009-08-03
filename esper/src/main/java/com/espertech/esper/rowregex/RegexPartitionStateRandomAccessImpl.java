package com.espertech.esper.rowregex;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.collection.RollingEventBuffer;

import java.util.HashMap;
import java.util.Map;

public class RegexPartitionStateRandomAccessImpl implements RegexPartitionStateRandomAccess
{
    private final RegexPartitionStateRandomAccessGetter getter;
    private final Map<EventBean, EventBean[]> priorEventMap;
    private final RollingEventBuffer newEvents;
    private EventBean[] lastNew;

    public RegexPartitionStateRandomAccessImpl(RegexPartitionStateRandomAccessGetter getter)
    {
        this.getter = getter;

        // Construct a rolling buffer of new data for holding max index + 1 (position 1 requires 2 events to keep)
        newEvents = new RollingEventBuffer(getter.getMaxPriorIndex() + 1);
        if (!getter.isUnbound())
        {
            priorEventMap = new HashMap<EventBean, EventBean[]>();
        }
        else
        {
            priorEventMap = null;
        }
    }

    public void newEventPrepare(EventBean newEvent)
    {
        // Add new event
        newEvents.add(newEvent);

        // Save prior index events in array
        EventBean[] priorEvents = new EventBean[getter.getIndexesRequestedLen()];
        for (int j = 0; j < priorEvents.length; j++)
        {
            int priorIndex = getter.getIndexesRequested()[j];
            priorEvents[j] = newEvents.get(priorIndex);
        }

        if (priorEventMap != null)
        {
            priorEventMap.put(newEvent, priorEvents);
        }

        lastNew = priorEvents;        
        getter.setRandomAccess(this);
    }

    public void existingEventPrepare(EventBean newEvent)
    {
        if (priorEventMap != null)
        {
            lastNew = priorEventMap.get(newEvent);
        }
        getter.setRandomAccess(this);
    }

    // Always immediatly preceded by #newEventPrepare
    public EventBean getPreviousEvent(int assignedRelativeIndex)
    {
        if (lastNew == null)
        {
            return null;
        }
        return lastNew[assignedRelativeIndex];
    }

    public void remove(EventBean[] oldEvents)
    {
        if (oldEvents == null)
        {
            return;
        }
        for (int i = 0; i < oldEvents.length; i++)
        {
            remove(oldEvents[i]);
        }
    }

    public void remove(EventBean oldEvent)
    {
        if (priorEventMap != null)
        {
            priorEventMap.remove(oldEvent);
        }
    }

    public boolean isEmpty()
    {
        if (priorEventMap != null)
        {
            priorEventMap.isEmpty();
        }
        return true;
    }
}