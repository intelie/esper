package net.esper.eql.join;

import net.esper.event.EventBean;
import net.esper.collection.MultiKey;
import net.esper.view.HistoricalEventViewable;

import java.util.Set;
import java.util.List;

/**
 * Query strategy for use with {@link HistoricalEventViewable}
 * to perform lookup for a given stream using the poll method on a viewable.
 */
public class HistoricalDataQueryStrategy implements QueryStrategy
{
    private final int myStreamNumber;
    private final int historicalStreamNumber;
    private final HistoricalEventViewable historicalEventViewable;
    private final EventBean[][] lookupRows1Event;

    /**
     * Ctor.
     * @param myStreamNumber is the strategy's stream number
     * @param historicalStreamNumber is the stream number of the view to be polled
     * @param historicalEventViewable is the view to be polled from
     */
    public HistoricalDataQueryStrategy(int myStreamNumber, int historicalStreamNumber, HistoricalEventViewable historicalEventViewable)
    {
        this.myStreamNumber = myStreamNumber;
        this.historicalStreamNumber = historicalStreamNumber;
        this.historicalEventViewable = historicalEventViewable;

        lookupRows1Event = new EventBean[1][];
        lookupRows1Event[0] = new EventBean[2];
    }

    public void lookup(EventBean[] lookupEvents, Set<MultiKey<EventBean>> joinSet)
    {
        EventBean[][] lookupRows;

        // If looking up a single event, reuse the buffered array
        if (lookupEvents.length == 1)
        {
            lookupRows = lookupRows1Event;
            lookupRows[0][myStreamNumber] = lookupEvents[0];
        }
        else
        {
            // Prepare rows with each row N events where N is the number of streams
            lookupRows = new EventBean[lookupEvents.length][];
            for (int i = 0; i < lookupEvents.length; i++)
            {
                lookupRows[i] = new EventBean[2];
                lookupRows[i][myStreamNumber] = lookupEvents[i];
            }
        }

        List<EventBean>[] result = historicalEventViewable.poll(lookupRows);

        int count = 0;
        for (List<EventBean> rowsPerLookup : result)
        {
            for (EventBean resultEvent : rowsPerLookup)
            {
                EventBean[] resultRow = new EventBean[2];
                resultRow[myStreamNumber] = lookupEvents[count];
                resultRow[historicalStreamNumber] = resultEvent;
                joinSet.add(new MultiKey<EventBean>(resultRow));
            }
            count++;
        }
    }
}
