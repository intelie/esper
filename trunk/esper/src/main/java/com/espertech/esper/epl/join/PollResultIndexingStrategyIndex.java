package com.espertech.esper.epl.join;

import com.espertech.esper.epl.join.table.EventTable;
import com.espertech.esper.epl.join.table.UnindexedEventTableList;
import com.espertech.esper.epl.join.table.PropertyIndexedEventTable;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.List;

/**
 * Strategy for building an index out of poll-results knowing the properties to base the index on.
 */
public class PollResultIndexingStrategyIndex implements PollResultIndexingStrategy
{
    private final int streamNum;
    private final EventType eventType;
    private final String[] propertyNames;

    /**
     * Ctor.
     * @param streamNum is the stream number of the indexed stream
     * @param eventType is the event type of the indexed stream
     * @param propertyNames is the property names to be indexed
     */
    public PollResultIndexingStrategyIndex(int streamNum, EventType eventType, String[] propertyNames)
    {
        this.streamNum = streamNum;
        this.eventType = eventType;
        this.propertyNames = propertyNames;
    }

    public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
    {
        if (!isActiveCache)
        {
            return new UnindexedEventTableList(pollResult);
        }
        PropertyIndexedEventTable table = new PropertyIndexedEventTable(streamNum, eventType, propertyNames);
        table.add(pollResult.toArray(new EventBean[pollResult.size()]));
        return table;
    }
}
