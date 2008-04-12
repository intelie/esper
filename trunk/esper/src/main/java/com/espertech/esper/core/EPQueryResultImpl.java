package com.espertech.esper.core;

import com.espertech.esper.client.SafeIterator;
import com.espertech.esper.collection.ArrayEventIterator;
import com.espertech.esper.event.EventBean;
import com.espertech.esper.event.EventType;

import java.util.Iterator;

public class EPQueryResultImpl implements EPQueryResult
{
    private EPPreparedQueryResult queryResult;

    public EPQueryResultImpl(EPPreparedQueryResult queryResult)
    {
        this.queryResult = queryResult;
    }

    public int getRowCount()
    {
        return queryResult.getResult().length;
    }

    public Iterator<EventBean> iterator()
    {
        return new ArrayEventIterator(queryResult.getResult());
    }

    public SafeIterator<EventBean> safeIterator()
    {
        return null;  // TODO
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
