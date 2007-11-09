package net.esper.eql.named;

import net.esper.event.EventBean;

import java.util.ArrayList;
import java.util.Iterator;

public class DeletionStrategyDeleteAll implements DeletionStrategy
{
    private Iterable<EventBean> source;

    public DeletionStrategyDeleteAll(Iterable<EventBean> source)
    {
        this.source = source;
    }

    public EventBean[] determineRemoveStream(EventBean[] newData)
    {
        ArrayList<EventBean> events = new ArrayList<EventBean>();
        for (Iterator<EventBean> it = source.iterator(); it.hasNext();)
        {
            events.add(it.next());
        }
        return events.toArray(new EventBean[0]);
    }
}
