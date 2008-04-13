package com.espertech.esper.core;

import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.Iterator;

/**
 * Query result.
 */
public class EPQueryResultImpl implements EPQueryResult
{
    private EPPreparedQueryResult queryResult;

    /**
     * Ctor.
     * @param queryResult is the prepared query
     */
    public EPQueryResultImpl(EPPreparedQueryResult queryResult)
    {
        this.queryResult = queryResult;
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(queryResult.getResult());
    }

    public EventBean[] getArray()
    {
        return queryResult.getResult();
    }

    public EventType getEventType()
    {
        return queryResult.getEventType();
    }
}
