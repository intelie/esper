package net.esper.eql.join.table;

import net.esper.event.EventBean;

import java.util.Set;
import java.util.HashSet;

/**
 * Simple table of events without an index.
 */
public class UnindexedEventTable implements EventTable
{
    private final int streamNum;
    private Set<EventBean> eventSet = new HashSet<EventBean>();

    /**
     * Ctor.
     * @param streamNum is the indexed stream's number
     */
    public UnindexedEventTable(int streamNum)
    {
        this.streamNum = streamNum;
    }

    public void add(EventBean[] addEvents)
    {
        if (addEvents == null)
        {
            return;
        }

        for (int i = 0; i < addEvents.length; i++)
        {
            eventSet.add(addEvents[i]);
        }
    }

    public void remove(EventBean[] removeEvents)
    {
        if (removeEvents == null)
        {
            return;
        }

        for (int i = 0; i < removeEvents.length; i++)
        {
            eventSet.remove(removeEvents[i]);
        }
    }

    /**
     * Returns events in table.
     * @return all events
     */
    public Set<EventBean> getEventSet()
    {
        return eventSet;
    }

    public String toString()
    {
        return "UnindexedEventTable streamNum=" + streamNum;
    }
}