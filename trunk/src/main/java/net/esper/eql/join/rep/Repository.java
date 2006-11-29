package net.esper.eql.join.rep;

import net.esper.event.EventBean;

import java.util.Iterator;
import java.util.Set;

/**
 * An interface for a repository of events in a lookup/join scheme
 * that supplies events for event stream table lookups and receives results of lookups.
 */
public interface Repository
{
    /**
     * Supply events for performing look ups for a given stream.
     * @param lookupStream is the stream to perform lookup for
     * @return an iterator over events with additional positioning information
     */
    public Iterator<Cursor> getCursors(int lookupStream);

    /**
     * Add a lookup result.
     * @param cursor provides result position and parent event and node information
     * @param lookupResults is the events found
     * @param resultStream is the stream number of the stream providing the results
     */
    public void addResult(Cursor cursor, Set<EventBean> lookupResults, int resultStream);
}
