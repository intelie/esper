package com.espertech.esper.eql.join.table;

import com.espertech.esper.event.EventBean;
import com.espertech.esper.collection.NullIterator;

import java.util.Iterator;
import java.util.List;

/**
 * Simple table of events without an index, based on a List implementation rather then a set
 * since we know there cannot be duplicates (such as a poll returning individual rows).
 */
public class UnindexedEventTableList implements EventTable
{
    private static NullIterator<EventBean> emptyIterator = new NullIterator<EventBean>();
    private List<EventBean> eventSet;

    /**
     * Ctor.
     * @param eventSet is a list initializing the table
     */
    public UnindexedEventTableList(List<EventBean> eventSet)
    {
        this.eventSet = eventSet;
    }

    public void add(EventBean[] addEvents)
    {
        if (addEvents == null)
        {
            return;
        }

        for (EventBean addEvent : addEvents)
        {
            eventSet.add(addEvent);
        }
    }

    public void remove(EventBean[] removeEvents)
    {
        if (removeEvents == null)
        {
            return;
        }

        for (EventBean removeEvent : removeEvents)
        {
            eventSet.remove(removeEvent);
        }
    }

    public Iterator<EventBean> iterator()
    {
        if (eventSet == null)
        {
            return emptyIterator;
        }
        return eventSet.iterator();
    }

    public boolean isEmpty()
    {
        return eventSet.isEmpty();
    }

    public String toString()
    {
        return "UnindexedEventTableList";
    }

    public void clear()
    {
        eventSet.clear();
    }
}
