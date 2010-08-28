package com.espertech.esper.epl.agg;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.epl.expression.ExprEvaluator;

import java.util.Iterator;

public class AggregationAccessorAll implements AggregationAccessor
{
    private final int streamNum;
    private final ExprEvaluator childNode;
    private final EventBean[] eventsPerStream;

    public AggregationAccessorAll(int streamNum, ExprEvaluator childNode)
    {
        this.streamNum = streamNum;
        this.childNode = childNode;
        this.eventsPerStream = new EventBean[streamNum + 1];
    }

    public Object getValue(AggregationAccess access) {
        if (access.size() == 0) {
            return null;
        }
        Object[] result = new Object[access.size()];
        Iterator<EventBean> it = access.iterator();
        int count = 0;
        for (;it.hasNext();) {
            EventBean bean = it.next();
            eventsPerStream[streamNum] = bean;
            Object value = childNode.evaluate(eventsPerStream, true, null);
            result[count++] = value;
        }

        return result;
    }
}