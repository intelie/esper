package net.esper.eql.named;

import net.esper.event.EventBean;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Deletes from a named window all events simply using the named window's data window iterator.
 */
public class DeletionStrategyDeleteAll implements DeletionStrategy
{
    private Iterable<EventBean> source;

    /**
     * Ctor.
     * @param source iterator of the data window under the named window
     */
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
