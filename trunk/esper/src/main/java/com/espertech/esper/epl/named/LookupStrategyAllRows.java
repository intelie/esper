package com.espertech.esper.epl.named;

import com.espertech.esper.event.EventBean;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Deletes from a named window all events simply using the named window's data window iterator.
 */
public class LookupStrategyAllRows implements LookupStrategy
{
    private Iterable<EventBean> source;

    /**
     * Ctor.
     * @param source iterator of the data window under the named window
     */
    public LookupStrategyAllRows(Iterable<EventBean> source)
    {
        this.source = source;
    }

    public EventBean[] lookup(EventBean[] newData)
    {
        ArrayList<EventBean> events = new ArrayList<EventBean>();
        for (Iterator<EventBean> it = source.iterator(); it.hasNext();)
        {
            events.add(it.next());
        }
        return events.toArray(new EventBean[events.size()]);
    }
}
