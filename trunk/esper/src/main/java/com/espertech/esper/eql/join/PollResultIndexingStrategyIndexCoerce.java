package com.espertech.esper.eql.join;

import com.espertech.esper.eql.join.table.EventTable;
import com.espertech.esper.eql.join.table.PropertyIndTableCoerceAll;
import com.espertech.esper.eql.join.table.UnindexedEventTableList;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.List;

/**
 * Strategy for building an index out of poll-results knowing the properties to base the index on, and their
 * coercion types.
 */
public class PollResultIndexingStrategyIndexCoerce implements PollResultIndexingStrategy
{
    private final int streamNum;
    private final EventType eventType;
    private final String[] propertyNames;
    private final Class[] coercionTypes;

    /**
     * Ctor.
     * @param streamNum is the stream number of the indexed stream
     * @param eventType is the event type of the indexed stream
     * @param propertyNames is the property names to be indexed
     * @param coercionTypes is the types to coerce to for keys and values
     */
    public PollResultIndexingStrategyIndexCoerce(int streamNum, EventType eventType, String[] propertyNames, Class[] coercionTypes)
    {
        this.streamNum = streamNum;
        this.eventType = eventType;
        this.propertyNames = propertyNames;
        this.coercionTypes = coercionTypes;
    }

    public EventTable index(List<EventBean> pollResult, boolean isActiveCache)
    {
        if (!isActiveCache)
        {
            return new UnindexedEventTableList(pollResult);
        }
        PropertyIndTableCoerceAll table = new PropertyIndTableCoerceAll(streamNum, eventType, propertyNames, coercionTypes);
        table.add(pollResult.toArray(new EventBean[0]));
        return table;
    }
}
